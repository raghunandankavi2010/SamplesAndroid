package com.ladyspyd.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestAddContactsUs {


    @SerializedName("user_email")
    @Expose
    String userEmail;
    @SerializedName("message")
    @Expose
    String message;


    public RequestAddContactsUs(String userEmail, String message) {
        this.userEmail = userEmail;
        this.message = message;
    }

}