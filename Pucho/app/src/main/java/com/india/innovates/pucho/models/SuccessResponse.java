package com.india.innovates.pucho.models;

/**
 * Created by Raghunandan on 18-12-2015.
 */
import com.google.gson.annotations.Expose;

public class SuccessResponse {

    @Expose
    private Success success;

    /**
     *
     * @return
     * The success
     */
    public Success getSuccess() {
        return success;
    }

    /**
     *
     * @param success
     * The success
     */
    public void setSuccess(Success success) {
        this.success = success;
    }



}
