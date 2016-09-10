package com.pucho.service.impl;

import java.util.List;

import org.activejpa.entity.Condition;
import org.activejpa.entity.Filter;

import com.pucho.domain.GCMUser;
import com.pucho.domain.User;
import com.pucho.helper.ErrorResponse;
import com.pucho.helper.GenericResponse;
import com.pucho.helper.NotificationRegisterRequest;
import com.pucho.helper.SuccessResponse;
import com.pucho.service.NotificationRegisterService;

public class NotificationRegisterServiceImpl implements NotificationRegisterService {

    @Override
    public GenericResponse register(NotificationRegisterRequest request) {
        GenericResponse response = new GenericResponse();
        Filter filter = new Filter();
        filter.addCondition("id", Condition.Operator.eq, request.getUserId());
        List<User> userList = User.where(filter);
        if (userList == null || userList.size() == 0) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setReason(ErrorResponse.Reason.USER_DOES_NOT_EXIST);
            response.setFailure(errorResponse);
            return response;
        }
        User user = userList.get(0);
        GCMUser gcmUser = user.getGcmUser();
        if (gcmUser == null) {
            gcmUser = new GCMUser();
            gcmUser.setRegistrationId(request.getRegistrationId());
            gcmUser.persist();
            user.setGcmUser(gcmUser);
            user.persist();
        }
        else {
            gcmUser.setRegistrationId(request.getRegistrationId());
            gcmUser.persist();
        }
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setSuccess(new SuccessResponse(true));
        response.setSuccessResponse(successResponse);
        return response;
    }
}
