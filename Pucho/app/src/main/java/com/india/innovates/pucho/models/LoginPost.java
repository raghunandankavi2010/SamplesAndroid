package com.india.innovates.pucho.models;

/**
 * Created by Raghunandan on 19-12-2015.
 */
public class LoginPost {

    private String person_name, person_id, id_token;

    public LoginPost() {

    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getPersonId() {
        return person_id;
    }

    public void setPersonId(String person_id) {
        this.person_id = person_id;
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }
}
