package com.pucho.resources;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pucho.configuration.OAuthConfiguration;
import com.pucho.configuration.PuchoConfiguration;
import com.pucho.helper.ErrorResponse;
import com.pucho.helper.GenericResponse;
import com.pucho.helper.GoogleOAuthLoginRequest;
import com.pucho.service.LoginService;
import com.pucho.utils.JsonUtil;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import io.dropwizard.jackson.JsonSnakeCase;

/**
 * @author harsh
 *
 */
@Path("/oauth")
@JsonSnakeCase
@Produces({ "application/json" })
@Singleton
public class OauthResource {

    private static final Logger logger = LoggerFactory.getLogger(OauthResource.class);
    private List<String> CLIENT_ID_LIST;
    private List<String> APPS_DOMAIN_NAME_LIST;
    private LoginService loginService;

    @Inject
    public OauthResource(PuchoConfiguration configuration, LoginService loginService) {
        OAuthConfiguration oAuthconfig = configuration.getOAuthconfiguration();
        this.CLIENT_ID_LIST = oAuthconfig.getClientIdList();
        this.APPS_DOMAIN_NAME_LIST = oAuthconfig.getAppsDomainList();
        this.loginService = loginService;
    }

    @POST
    @Path("/google")
    @Timed
    public String login(String body) {
        GoogleOAuthLoginRequest request;
        try {
            request = JsonUtil.deserializeJson(body, GoogleOAuthLoginRequest.class);

        } catch (Exception e) {
            logger.error("Error while parsing the request " + body, e);
            throw new WebApplicationException(e, javax.ws.rs.core.Response.Status.BAD_REQUEST);
        }
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new ApacheHttpTransport(), new JacksonFactory())
                .setAudience(this.CLIENT_ID_LIST).build();
        String idTokenString = request.getIdToken();
        GenericResponse response = new GenericResponse();
        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                response = this.loginService.login(request, idToken.getPayload().getEmail());
                String responseStr = JsonUtil.serializeJson(response);
                if (response.isSuccess()) {
                    return responseStr;
                }
            }
            else {
                System.out.println("idToken: null, client_id_list: " + StringUtils.join(this.CLIENT_ID_LIST, ","));
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setReason(ErrorResponse.Reason.INVALID_ID_TOKEN);
                response.setFailure(errorResponse);
            }
        } catch (GeneralSecurityException e) {
            logger.error("GeneralSecurityException", e);
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            logger.error("IOException", e);
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
        catch (IllegalArgumentException e) {
            logger.error("IllegalArgumentException", e);
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
        Response res = Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(response)
                .build();
        throw new WebApplicationException(res);
    }

}
