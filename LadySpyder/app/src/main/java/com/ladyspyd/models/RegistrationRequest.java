package com.ladyspyd.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationRequest {

    @SerializedName("user_name")
    @Expose
    String userName;
    @SerializedName("user_email")
    @Expose
    String userEmail;
    @SerializedName("user_phone")
    @Expose
    String userPhone;
    @SerializedName("password")
    @Expose
    String userPassword;
    @SerializedName("language")
    @Expose
    String language;

    public RegistrationRequest(String userName, String userEmail, String userPhone, String userPassword, String language) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userPassword = userPassword;
        this.language = language;
    }

}