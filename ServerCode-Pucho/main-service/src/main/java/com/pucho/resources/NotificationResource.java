package com.pucho.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pucho.helper.GenericResponse;
import com.pucho.helper.NotificationRegisterRequest;
import com.pucho.service.NotificationRegisterService;
import com.pucho.utils.JsonUtil;

import io.dropwizard.jackson.JsonSnakeCase;

@Path("/notify")
@JsonSnakeCase
@Produces({ "application/json" })
@Singleton
public class NotificationResource {

    private static final Logger logger = LoggerFactory.getLogger(SMSResource.class);
    private NotificationRegisterService notificationService;
    
    @Inject
    public NotificationResource(NotificationRegisterService notificationService) {
        this.notificationService = notificationService;
    }
    
    @POST
    public String register(String body) {
        NotificationRegisterRequest request;
        try {
            request = JsonUtil.deserializeJson(body, NotificationRegisterRequest.class);
        }
        catch (Exception e) {
            logger.error("Error while parsing the request " + body, e);
            throw new WebApplicationException(e, javax.ws.rs.core.Response.Status.BAD_REQUEST);
        }
        GenericResponse response = this.notificationService.register(request);
        try {
            String responseBody = JsonUtil.serializeJson(response);
            return responseBody;
        }
        catch (JsonProcessingException e) {
            logger.error("Error while parsing the response ", e);
            throw new WebApplicationException(e, javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
        }
        
    }
}
