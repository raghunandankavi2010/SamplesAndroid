package com.ladyspyd.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForogotRequestModel {

    @SerializedName("user_email")
    @Expose
    String userEmail;


    public ForogotRequestModel(String userEmail) {
        this.userEmail = userEmail;
    }


}
