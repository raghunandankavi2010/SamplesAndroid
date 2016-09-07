package com.india.innovates.pucho.models;

/**
 * Created by Raghunandan on 24-12-2015.
 */
public class SignUpPost {

 /*   {
        "fullName": "Harsh Mathur",
            "profession": "software Engineer",
            "externalUserId": "externalUserID",
            "username": "harsh.mathur",
            "email" : "harshmathur.1990@gmail.com",
            "password": "password",
            "linkedin": "linkedinurl",
            "personalUrl" : "personalUrl"
    }*/
    String fullName,profession,username,password,email,linkedin,personalUrl,externalUserId;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getPersonalUrl() {
        return personalUrl;
    }

    public void setPersonalUrl(String personalUrl) {
        this.personalUrl = personalUrl;
    }

    public String getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

}
