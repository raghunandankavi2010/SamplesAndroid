package com.ladyspyd.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequestModel {

    @SerializedName("user_email")
    @Expose
    String userEmail;

    @SerializedName("password")
    @Expose
    String userPassword;

    public LoginRequestModel(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }


}
