package com.indiainnovates.pucho.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Raghunandan on 31-01-2016.
 */
public class QuestionAskedBy implements Parcelable{

    private boolean active;
    private int id,active_int;
    private String fullName,externalUserId,username,phone,email,linkedin,personalUrl;
    //private Long gcmUser;

    public QuestionAskedBy()
    {

    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    /*public Long getGcmUser() {
        return gcmUser;
    }

    public void setGcmUser(Long gcmUser) {
        this.gcmUser = gcmUser;
    }*/

    // Parcelling part

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeByte((byte) (isActive() ? 1 : 0));

        dest.writeInt(this.id);

        dest.writeString(this.fullName);
        dest.writeString(this.externalUserId);
        dest.writeString(this.username);
        dest.writeString(this.phone);
        dest.writeString(this.email);
        dest.writeString(this.linkedin);
        dest.writeString(this.personalUrl);
        //if(gcmUser!=null)
        //dest.writeLong(this.gcmUser);

    }

    protected QuestionAskedBy(Parcel in) {

        this.active =  in.readByte() != 0;

        this.id = in.readInt();

        this.fullName = in.readString();
        this.externalUserId = in.readString();
        this.username = in.readString();
        this.phone = in.readString();
        this.email = in.readString();
        this.linkedin = in.readString();
        this.personalUrl = in.readString();

        //if(gcmUser!=null)
        //this.gcmUser = in.readLong();
    }

    public static final Creator<QuestionAskedBy> CREATOR = new Creator<QuestionAskedBy>() {
        public QuestionAskedBy createFromParcel(Parcel source) {
            return new QuestionAskedBy(source);
        }

        public QuestionAskedBy[] newArray(int size) {
            return new QuestionAskedBy[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
