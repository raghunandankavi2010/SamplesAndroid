package com.pucho.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pucho.core.instrumentation.MemoryCache;
import com.pucho.core.instrumentation.QueueNames;
import com.pucho.domain.SMSAction;
import com.pucho.helper.GenericResponse;
import com.pucho.helper.IncomingSMSRequest;
import com.pucho.helper.OTPRequest;
import com.pucho.helper.SMSWorkerRequest;
import com.pucho.helper.SuccessResponse;
import com.pucho.service.MQService;
import com.pucho.service.SMSService;
import com.pucho.utils.JsonUtil;

import io.dropwizard.jackson.JsonSnakeCase;

@Path("/sms")
@JsonSnakeCase
@Produces({ "application/json" })
@Singleton
public class SMSResource {

    private static final Logger logger = LoggerFactory.getLogger(SMSResource.class);
    private final SMSService smsService;
    private final MQService mqService;
    private final MemoryCache cache = MemoryCache.INSTANCE;

    @Inject
    public SMSResource(SMSService smsService, MQService mqService) {
        this.smsService = smsService;
        this.mqService = mqService;
    }

    @GET
    @Path("/otp/{phone}")
    @Timed
    public String sendSMS(@PathParam("phone") String phone) {
        try {
            OTPRequest otp = new OTPRequest(phone);
            SMSWorkerRequest workerRequest = new SMSWorkerRequest();
            workerRequest.setSmsAction(SMSAction.OTP);
            workerRequest.setSerializedEntity(JsonUtil.serializeJson(otp));
            this.mqService.sendMessage(QueueNames.SMSQUEUE, JsonUtil.serializeJson(workerRequest));
            GenericResponse response = new GenericResponse();
            response.setSuccessResponse(new SuccessResponse(true));
            return JsonUtil.serializeJson(response);
        } catch (Exception e) {
            logger.error("Error while parsing the response ", e);
            throw new WebApplicationException(e, javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/validate/{phone}/{code}")
    @Timed
    public String validateSMS(@PathParam("phone") String phone, @PathParam("code") String code) {
        try {
            String responseStr = JsonUtil.serializeJson(this.smsService.validateOTP(phone, code));
            return responseStr;
        } catch (Exception e) {
            logger.error("Error while parsing the response ", e);
            throw new WebApplicationException(e, javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @POST
    @Timed
    public String incomingSMSHandler(String body) {
        IncomingSMSRequest request;
        try {
            request = JsonUtil.deserializeJson(body, IncomingSMSRequest.class);
        }
        catch(Exception e) {
            logger.error("Error while parsing the request " + body, e);
            throw new WebApplicationException(e, javax.ws.rs.core.Response.Status.BAD_REQUEST);
        }
        try {
            // mosto systems appended +0530 to a utc time string, hence the hack
            String time = request.getTime();
            time = time.replace("+0530", "Z");
            request.setTime(time);
            SMSWorkerRequest workerRequest = new SMSWorkerRequest();
            workerRequest.setSmsAction(SMSAction.INCOMING_SMS);
            workerRequest.setSerializedEntity(JsonUtil.serializeJson(request));
            this.mqService.sendMessage(QueueNames.SMSQUEUE, JsonUtil.serializeJson(workerRequest));
            GenericResponse response = new GenericResponse();
            response.setSuccessResponse(new SuccessResponse(true));
            return JsonUtil.serializeJson(response);
        }
        catch(JsonProcessingException e) {
            logger.error("Error while parsing the response ", e);
            throw new WebApplicationException(e, javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/unblock/{phoneNo}")
    public String unblockPhone(@PathParam("phoneNo") String phoneNo) {
        if (phoneNo.trim().length() == 10) {
            phoneNo = "91" + phoneNo.trim();
        }
        this.cache.unBlock(phoneNo);
        return "{}";
    }

}
