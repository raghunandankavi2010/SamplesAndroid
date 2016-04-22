package com.indiainnovates.pucho.models;

/**
 * Created by Raghunandan on 18-12-2015.
 */
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

