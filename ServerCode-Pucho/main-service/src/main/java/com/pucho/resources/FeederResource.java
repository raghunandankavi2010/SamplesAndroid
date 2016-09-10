package com.pucho.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pucho.service.QAService;
import com.pucho.utils.JsonUtil;
import io.dropwizard.jackson.JsonSnakeCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;

/**
 * Created by dinesh.rathore on 01/02/16.
 */
@Path("/wall/")
@JsonSnakeCase
@Produces({ "application/json" })
@Singleton
public class FeederResource {

    private static final Logger logger = LoggerFactory.getLogger(FeederResource.class);
    private QAService qaService;

    @Inject
    public FeederResource(QAService qaService) {
        this.qaService = qaService;
    }

    @GET
    @Path("/{userId}/feed")
    public String getFeed(@PathParam("userId") Long userId) {
        try {
            String responseStr = JsonUtil.serializeJson(this.qaService.getFeedForUser(userId));
            return responseStr;

        } catch (Exception e) {
            logger.error("Error while parsing the response ", e);
            throw new WebApplicationException(e, javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Path("/{followerUserId}/followUser/{followedUserId}")
    public String followUser(@PathParam("followerUserId") Long followerUserId,@PathParam("followedUserId") Long followedUserId) {
        try {
            String responseStr = JsonUtil.serializeJson(this.qaService.followUser(followerUserId, followedUserId));
            return responseStr;

        } catch (Exception e) {
            logger.error("Error while parsing the response ", e);
            throw new WebApplicationException(e, javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Path("/{followerUserId}/followQuestion/{followedQuestionId}")
    public String followQuestion(@PathParam("followerUserId") Long followerUserId,@PathParam("followedQuestionId") Long followedQuestionId) {
        try {
            String responseStr = JsonUtil.serializeJson(this.qaService.followQuestion(followerUserId, followedQuestionId));
            return responseStr;

        } catch (Exception e) {
            logger.error("Error while parsing the response ", e);
            throw new WebApplicationException(e, javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
