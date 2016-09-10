package com.pucho.service.impl;

import com.google.inject.Inject;
import com.ning.http.client.Response;
import com.pucho.configuration.MostoConfiguration;
import com.pucho.configuration.PuchoConfiguration;
import com.pucho.core.instrumentation.MemoryCache;
import com.pucho.domain.Question;
import com.pucho.domain.SMSOTP;
import com.pucho.domain.SMSTypes;
import com.pucho.domain.User;
import com.pucho.helper.ErrorResponse;
import com.pucho.helper.GenericResponse;
import com.pucho.helper.IncomingSMSRequest;
import com.pucho.helper.NewSMSRequest;
import com.pucho.helper.OTPRequest;
import com.pucho.helper.SuccessResponse;
import com.pucho.service.SMSService;
import com.pucho.utils.HttpClient;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activejpa.entity.Condition;
import org.activejpa.entity.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dinesh.rathore on 03/05/15.
 */
public class SMSServiceImpl implements SMSService {

    private static final Logger logger = LoggerFactory.getLogger(SMSServiceImpl.class);
    private HttpClient httpClient = HttpClient.INSTANCE;
    private final MostoConfiguration mostoConfiguration;
    private final MemoryCache cache = MemoryCache.INSTANCE;
    private final String HELP_TEXT_COUNT_KEY = "helptext_";
    public final String template = "Use code %s to verify your phone number on Pucho. May the force be with you.";

    @Inject
    public SMSServiceImpl(PuchoConfiguration configuration) {
        this.mostoConfiguration = configuration.getMostoConfiguration();
    }

    @Override
    public void sendSMS(String mobileNo, String sms) {

        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("user", mostoConfiguration.getUser());
            params.put("password", mostoConfiguration.getPassword());
            params.put("mobileNumber", mobileNo);
            params.put("sender", mostoConfiguration.getSender());
            params.put("SMSText", sms);

            String url = mostoConfiguration.getSmsUrl();
            Response response = httpClient.executeGet(url, params, null);

            if (response.getStatusCode() != javax.ws.rs.core.Response.Status.OK.getStatusCode()) {
                Integer status = response.getStatusCode();
                String responseBody = response.getResponseBody();
                System.out.println(response.getUri().toString());
                System.out.println(status);
                System.out.println(responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public GenericResponse newSMS(NewSMSRequest sms) {
        System.out.println(sms.toString());

        GenericResponse response = new GenericResponse();
        response.setSuccessResponse(new SuccessResponse(true));
        return response;
    }

    @Override
    public GenericResponse sendOTP(OTPRequest otpRequest) {
    	Integer length = otpRequest.getPhone().trim().length();
        if (length == 10) {
            String phone = "91" + otpRequest.getPhone().trim();
            otpRequest.setPhone(phone);
        }
        SecureRandom random = new SecureRandom();
        Integer code = random.nextInt(9999);
        if (code <= 999) {
            code += 1000;
        }
        String sms = String.format(template, Integer.toString(code));
        SMSOTP otp = new SMSOTP();
        otp.setPhone(otpRequest.getPhone());
        otp.setCode(Integer.toString(code));
        otp.setConsumed(false);
        otp.persist();
        this.sendSMS(otpRequest.getPhone(), sms);
        GenericResponse response = new GenericResponse();
        response.setSuccessResponse(new SuccessResponse(true));
        return response;
    }

    @Override
    public GenericResponse validateOTP(String mobileNo, String code) {
    	Integer length = mobileNo.trim().length();
    	if (length == 10) {
            String phone = "91" + mobileNo.trim();
            mobileNo = phone;
        }
        GenericResponse response = new GenericResponse();
        Filter filter = new Filter();
        filter.addCondition("phone", Condition.Operator.eq, mobileNo);
        filter.addCondition("code", Condition.Operator.eq, code);
        filter.addCondition("consumed", Condition.Operator.eq, false);
        List<SMSOTP> smsOTPList = SMSOTP.where(filter);
        if (smsOTPList == null || smsOTPList.size() == 0) {
            // do nothing
        } else {
            SMSOTP smsOTP = smsOTPList.get(0);
            Date now = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.HOUR, -4);
            Date fourHourBack = cal.getTime();
            if (smsOTP.getCreatedAt().compareTo(fourHourBack) >= 0) {
                smsOTP.setConsumed(true);
                smsOTP.persist();
                response.setSuccess(true);
                SuccessResponse successResponse = new SuccessResponse();
                successResponse.setSuccess(new SuccessResponse(true));
                response.setSuccessResponse(successResponse);
                return response;
            }
        }
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setReason(ErrorResponse.Reason.INVALID_OTP);
        response.setFailure(errorResponse);
        return response;
    }

    @Override
    public GenericResponse processSMS(IncomingSMSRequest request) {
        GenericResponse response = new GenericResponse();
        Integer length = request.getPhoneNo().trim().length();
        if (length == 10) {
            String phone = "91" + request.getPhoneNo().trim();
            request.setPhoneNo(phone);
        }
        String message = request.getMessage();
        String keywordMessage[] = message.split(" ", 3);
        if (keywordMessage.length == 3) {
            String keyword = keywordMessage[1].toLowerCase();
            String value = keywordMessage[2];
            SMSTypes smsType = SMSTypes.fromKeyword(keyword);
            switch (smsType) {
                case REGISTER:
                    registerPhoneUser(request.getPhoneNo(), value);
                break;
                case ASK:
                    askQuestionfromPhoneUser(request.getPhoneNo(), value, request.getTime());
                break;
                default:
                    String msg = "To ask a question use PUCHO ASK <Your QUES>." + " To register PUCHO REGISTER <YOUR NAME>. "
                            + "Send it to 8689982282. May the force be with you.";
                    sendHelpTextMessage(request.getPhoneNo(), msg);
                    logger.error("Invalid Keyword from phone: " + request.getPhoneNo() + " with message: " + message);
                break;
            }
        } else {
            // do something
            String msg = "To ask a question use PUCHO ASK <Your QUES>." + " To register PUCHO REGISTER <YOUR NAME>. "
                    + "Send it to 8689982282. May the force be with you.";
            sendHelpTextMessage(request.getPhoneNo(), msg);
            logger.error("Failed to fetch keyword from the incoming message from phone: " + request.getPhoneNo() + " with message: "
                    + message);
        }
        response.setSuccess(true);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setSuccess(new SuccessResponse(true));
        response.setSuccessResponse(successResponse);
        return response;
    }

    private void askQuestionfromPhoneUser(String phoneNumber, String question, String time) {
        Filter filter = new Filter();
        filter.addCondition("phone", Condition.Operator.eq, phoneNumber);
        List<User> usersList = User.where(filter);
        User user = null;
        if (usersList == null || usersList.size() == 0) {
            // User does not exist, send a message to register first
            String message = "Please Register first. Type Pucho register" + " <Your name>. Ex: Pucho Register Harsh Mathur. "
                    + "Send this message to 8689982282. May the force be with you.";
            sendHelpTextMessage(phoneNumber, message);
        } else {
            user = usersList.get(0);
            Question ques = new Question();
            ques.setTitle(question);
            ques.setAskedOn(javax.xml.bind.DatatypeConverter.parseDateTime(time).getTime());
            ques.setUserId(user.getId());
            ques.setUser(user);
            ques.persist();
            String msg = "Hi " + user.getFullName() + "! We have recieved your question."
                    + " We are working on the finding the most relevant answers" + " to the question. We will notify you.";
            sendMessageToUser(user, msg);
        }
    }

    private void registerPhoneUser(String phoneNumber, String name) {
        Filter filter = new Filter();
        filter.addCondition("phone", Condition.Operator.eq, phoneNumber);
        List<User> usersList = User.where(filter);
        User user = null;
        if (usersList == null || usersList.size() == 0) {
            // create new user
            user = new User();
            user.setPhone(phoneNumber);
            user.setFullName(name);
            user.persist();
            String msg = "Hi " + name + "! Thanks for registering with Pucho. You can ask a "
                    + "question by sending PUCHO ASK <Your question> and send" + " the sms to 8689982282. May the force be with you.";
            sendMessageToUser(user, msg);
        }
        else {
            String msg = "Hi " + name + "! You can ask a "
                    + "question by sending PUCHO ASK <Your question> and send" + " the sms to 8689982282. May the force be with you.";
            sendHelpTextMessage(phoneNumber, msg);
        }
    }
    
    private void sendHelpTextMessage(String phone, String msg) {
        String key = HELP_TEXT_COUNT_KEY+phone;
        if (!this.cache.isBlocked(phone)) {
            Integer count = (Integer)this.cache.get(key, 0);
            if (count < 3) {
                count = count + 1;
                sendSMS(phone, msg);
                this.cache.set(key, count);
            }
            else if (count == 3) {
                this.cache.blockPhone(phone);
                this.cache.pop(key);
            }
        }
    }
    
    private void sendMessageToUser(User user, String message) {
        if (user.getActive()) {
            sendSMS(user.getPhone(), message);
        }
    }
}
