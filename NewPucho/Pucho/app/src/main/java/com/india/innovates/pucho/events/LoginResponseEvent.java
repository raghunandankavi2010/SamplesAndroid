package com.india.innovates.pucho.events;

import com.india.innovates.pucho.models.LoginResponse;

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

