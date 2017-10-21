package com.peakaeriest.ladyspyder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LSForgotpasswordResponseModel {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}