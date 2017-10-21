package com.peakaeriest.ladyspyder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdatePasswordRequestModel {

    //    user_id,old_password,new_password
    @SerializedName("email")
    @Expose
    String userID;

    @SerializedName("old_password")
    @Expose
    String oldPassword;

    @SerializedName("new_password")
    @Expose
    String newPassword;

    public UpdatePasswordRequestModel(String userID, String oldPassword, String newPassword) {
        this.userID = userID;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
