package com.indiainnovates.pucho.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Raghunandan on 28-02-2016.
 */
public class SearchUser implements Parcelable{


    private int id;

    private String username;

    private String email;

    private Gcm_user gcm_user;

    private boolean active;

    //private List<String> user_educations;

    private String external_user_id;

    private String full_name;

    protected SearchUser(Parcel in) {
        id = in.readInt();
        username = in.readString();
        email = in.readString();
        active = in.readByte() != 0;
        external_user_id = in.readString();
        full_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeString(external_user_id);
        dest.writeString(full_name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchUser> CREATOR = new Creator<SearchUser>() {
        @Override
        public SearchUser createFromParcel(Parcel in) {
            return new SearchUser(in);
        }

        @Override
        public SearchUser[] newArray(int size) {
            return new SearchUser[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gcm_user getGcm_user() {
        return gcm_user;
    }

    public void setGcm_user(Gcm_user gcm_user) {
        this.gcm_user = gcm_user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

 /*   public List<String> getUser_educations() {
        return user_educations;
    }

    public void setUser_educations(List<String> user_educations) {
        this.user_educations = user_educations;
    }*/

    public String getExternal_user_id() {
        return external_user_id;
    }

    public void setExternal_user_id(String external_user_id) {
        this.external_user_id = external_user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }




}
