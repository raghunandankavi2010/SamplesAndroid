package com.india.innovates.pucho.models;

/**
 * Created by Raghunandan on 28-02-2016.
 */
public class Gcm_user
{
    private String id;

    private String registration_id;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getRegistration_id ()
    {
        return registration_id;
    }

    public void setRegistration_id (String registration_id)
    {
        this.registration_id = registration_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", registration_id = "+registration_id+"]";
    }
}

