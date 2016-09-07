package com.india.innovates.pucho.events;

import com.india.innovates.pucho.models.SignupResponse;

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
