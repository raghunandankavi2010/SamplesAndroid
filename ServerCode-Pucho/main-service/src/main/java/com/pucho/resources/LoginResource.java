package com.pucho.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pucho.helper.FacebookPhoneLoginRequest;
import com.pucho.helper.GenericResponse;
import com.pucho.helper.LoginRequest;
import com.pucho.service.LoginService;
import com.pucho.utils.JsonUtil;
import io.dropwizard.jackson.JsonSnakeCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by dinesh.rathore on 05/04/15.
 */
@Path("/login")
@JsonSnakeCase
@Produces({ "application/json" })
@Singleton
public class LoginResource {

    private static final Logger logger = LoggerFactory.getLogger(LoginResource.class);

    private LoginService loginService;

    @Inject
    public LoginResource(LoginService loginService) {
        this.loginService = loginService;
    }

    @POST
    @Timed
    public String login(String body) {

        LoginRequest request;
        try {
            request = JsonUtil.deserializeJson(body, LoginRequest.class);

        } catch (Exception e) {
            logger.error("Error while parsing the request " + body, e);
            throw new WebApplicationException(e, javax.ws.rs.core.Response.Status.BAD_REQUEST);
        }

        GenericResponse response = loginService.login(request);
        return processResponse(response);

    }

    @POST
    @Timed
    @Path("/facebook")
    public String loginWithFacebookPhone(String body) {

        FacebookPhoneLoginRequest request;
        try {
            request = JsonUtil.deserializeJson(body, FacebookPhoneLoginRequest.class);

        } catch (Exception e) {
            logger.error("Error while parsing the request " + body, e);
            throw new WebApplicationException(e, javax.ws.rs.core.Response.Status.BAD_REQUEST);
        }

        GenericResponse response = loginService.login(request);
        return processResponse(response);

    }

    private String processResponse(GenericResponse response) {
        try {
            String responseStr = JsonUtil.serializeJson(response);
            if (response.isSuccess()) {
                return responseStr;
            }
            Response res = Response.status(Response.Status.UNAUTHORIZED)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(response)
                    .build();
            throw new WebApplicationException(res);

        } catch (JsonProcessingException e) {
            logger.error("Error while parsing the response ", e);
            throw new WebApplicationException(e, javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
