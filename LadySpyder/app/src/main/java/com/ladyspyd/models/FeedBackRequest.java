package com.ladyspyd.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedBackRequest {


    @SerializedName("user_email")
    @Expose
    String user_email;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("desc")
    @Expose
    String desc;


    public FeedBackRequest(String user_email, String title, String desc) {
        this.user_email = user_email;
        this.title = title;
        this.desc = desc;
    }

}