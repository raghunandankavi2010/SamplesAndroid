package com.peakaeriest.ladyspyder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateRequestModel {

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("old_password")
    @Expose
    String old_password;


    @SerializedName("new_password")
    @Expose
    String new_password;


    public UpdateRequestModel(String userEmail,String old_password,String new_password) {
        this.email = userEmail;
        this.old_password = old_password;
        this.new_password = new_password;

    }


}
