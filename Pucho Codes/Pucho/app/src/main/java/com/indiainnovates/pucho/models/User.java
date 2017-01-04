package com.indiainnovates.pucho.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tamil on 4/13/2015.
 */

//POJO Object to store USER details
public class User implements Parcelable {




    private boolean active;
    private String userName, sessionKey, profession, email, fullname, profileurl,userId,idToken;

    public User() {

    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }


    public String getUserName() {
        return userName;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }


    public String getUserId() {
        return userId;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public String getProfileurl() {
        return profileurl;
    }

    // Parcelling part

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fullname);
        dest.writeString(this.email);
        dest.writeString(this.profileurl);
        dest.writeString(this.userId);


    }

    public User(Parcel in) {
        this.fullname = in.readString();
        this.email = in.readString();
        this.profileurl = in.readString();
        this.userId = in.readString();

    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}


