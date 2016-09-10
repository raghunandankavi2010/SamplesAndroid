package com.pucho.service.impl;

import com.pucho.domain.User;
import com.pucho.helper.*;
import com.pucho.service.LoginService;
import org.activejpa.entity.Condition;
import org.activejpa.entity.Filter;

import java.util.List;

/**
 * Created by dinesh.rathore on 05/04/15.
 */
public class LoginServiceImpl implements LoginService {

    @Override
    public GenericResponse login(LoginRequest request) {

        GenericResponse response = new GenericResponse();
        Filter filter = new Filter();
        filter.addCondition("username", Condition.Operator.eq, request.getUserName());
        filter.addCondition("password", Condition.Operator.eq, request.getPassword());
        List<User> users = User.where(filter);
        if (users == null || users.size() == 0) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setReason(ErrorResponse.Reason.LOGIN_FAILED);
            response.setFailure(errorResponse);
        } else {

            response.setSuccess(true);
            SuccessResponse successResponse = new SuccessResponse();
            successResponse.setSuccess(users.get(0));
            response.setSuccessResponse(successResponse);
        }
        return response;
    }

    @Override
    public GenericResponse login(GoogleOAuthLoginRequest request, String email) {
        GenericResponse response = new GenericResponse();
        Filter filter = new Filter();
        filter.addCondition("email", Condition.Operator.eq, email);
        List<User> users = User.where(filter);
        User user;
        if (users == null || users.size() == 0) {
            user = new User();
            user.setEmail(email);
            user.setFullName(request.getPersonName());
            user.setExternalUserId(request.getPersonId());
            user.setUsername(email);
            user.setProvider("GOOGLE");
            user.persist();
        }
        else {
            user = users.get(0);
        }
        response.setSuccess(true);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setSuccess(user);
        response.setSuccessResponse(successResponse);
        return response;
    }

    @Override
    public GenericResponse login(FacebookPhoneLoginRequest request) {
        if (request.getPhoneNo().trim().length() == 10) {
            String phone = "91" + request.getPhoneNo().trim();
            request.setPhoneNo(phone);
        }
        GenericResponse response = new GenericResponse();
        Filter filter = new Filter();
        filter.addCondition("phone", Condition.Operator.eq, request.getPhoneNo());
        List<User> usersList = User.where(filter);
        User user = null;
        if (usersList == null || usersList.size() == 0) {
            // create new user
            user = new User();
            user.setPhone(request.getPhoneNo());
            user.setFullName(request.getFullName());
            user.setProvider("FACEBOOK");
            user.setExternalUserId(request.getFacebookAccountId());
            user.persist();
        }
        else {
            user = usersList.get(0);
        }
        response.setSuccess(true);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setSuccess(user);
        response.setSuccessResponse(successResponse);
        return response;
    }

}
