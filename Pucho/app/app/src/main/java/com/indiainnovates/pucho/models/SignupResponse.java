package com.indiainnovates.pucho.models;

/**
 * Created by Raghunandan on 24-12-2015.
 */
public class SignupResponse {

    private boolean success;

    private SuccessSignUpResponse success_response;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public SuccessSignUpResponse getSuccess_response() {
        return success_response;
    }

    public void setSuccess_response(SuccessSignUpResponse success_response) {
        this.success_response = success_response;
    }


}
