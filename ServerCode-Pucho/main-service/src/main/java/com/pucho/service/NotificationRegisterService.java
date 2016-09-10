package com.pucho.service;

import com.pucho.helper.GenericResponse;
import com.pucho.helper.NotificationRegisterRequest;

public interface NotificationRegisterService {

    public GenericResponse register(NotificationRegisterRequest request);
}
