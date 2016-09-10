package com.pucho.service;

import com.pucho.helper.FacebookPhoneLoginRequest;
import com.pucho.helper.GenericResponse;
import com.pucho.helper.GoogleOAuthLoginRequest;
import com.pucho.helper.LoginRequest;

/**
 * Created by dinesh.rathore on 05/04/15.
 */
public interface LoginService {

    GenericResponse login(LoginRequest request);
    GenericResponse login(GoogleOAuthLoginRequest request, String email);
    GenericResponse login(FacebookPhoneLoginRequest request);
}
