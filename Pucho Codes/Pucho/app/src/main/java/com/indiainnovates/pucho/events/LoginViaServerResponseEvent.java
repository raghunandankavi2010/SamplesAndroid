package com.indiainnovates.pucho.events;

import com.indiainnovates.pucho.models.SignupResponse;

/**
 * Created by Raghunandan on 01-01-2016.
 */
public class LoginViaServerResponseEvent {

    private SignupResponse signupResponse;

    public LoginViaServerResponseEvent(SignupResponse signupResponse)
    {
        this.signupResponse = signupResponse;
    }

    public SignupResponse getLoginViaServerResponse() {
        return signupResponse;
    }
}
