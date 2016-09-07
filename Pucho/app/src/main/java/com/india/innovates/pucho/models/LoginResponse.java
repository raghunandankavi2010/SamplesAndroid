package com.india.innovates.pucho.models;

/**
 * Created by Raghunandan on 18-12-2015.
 */

public class LoginResponse {

    
    private boolean success;


    
    private SuccessResponse success_response;


    /**
     * @return The success
     */
    public boolean getSuccess() {
        return success;
    }

    /**
     * @param success The success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return The successResponse
     */
    public SuccessResponse getSuccessResponse() {
        return success_response;
    }

    /**
     * @param success_response The success_response
     */
    public void setSuccessResponse(SuccessResponse success_response) {
        this.success_response = success_response;
    }
    



}

