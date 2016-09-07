package com.india.innovates.pucho.models;

/**
 * Created by Raghunandan on 29-01-2016.
 */
public class PostGcmToken {

    private int user_id;
    private String registration_id;

    public void setRegistration_id(String registration_id) {
        this.registration_id = registration_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getRegistration_id() {
        return registration_id;
    }
}
