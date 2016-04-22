package com.indiainnovates.pucho.events;

import com.indiainnovates.pucho.models.LoginResponse;

/**
 * Created by Raghunandan on 26-12-2015.
 */
public class LoginResponseEvent {

    private LoginResponse loginResponse;

    public LoginResponseEvent(LoginResponse loginResponse)
    {
        this.loginResponse = loginResponse;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }
}

