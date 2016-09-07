package com.india.innovates.pucho.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Raghunandan on 16-02-2016.
 */
public class AnsweredByUser implements Parcelable {

    /*"user": {
        "active": true,
                "id": 1,
                "fullName": "Harsh Mathur",
                "profession": null,
                "externalUserId": null,
                "username": null,
                "phone": "919599771751",
                "email": null,
                "linkedin": null,
                "personalUrl": null,
                "userEducations": [],
        "gcmUser": null*/

    private boolean active;
    private int id;
    private String fullName,profession,externalUserId,email,linkedin,personalUrl,phone;

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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeByte((byte) (isActive() ? 1 : 0));

        dest.writeInt(this.id);

        dest.writeString(this.fullName);
        dest.writeString(this.externalUserId);
        dest.writeString(this.phone);
        dest.writeString(this.email);
        dest.writeString(this.linkedin);
        dest.writeString(this.personalUrl);
        //if(gcmUser!=null)
        //dest.writeLong(this.gcmUser);

    }

    protected AnsweredByUser(Parcel in) {

        this.active =  in.readByte() != 0;

        this.id = in.readInt();

        this.fullName = in.readString();
        this.externalUserId = in.readString();
        this.phone = in.readString();
        this.email = in.readString();
        this.linkedin = in.readString();
        this.personalUrl = in.readString();

        //if(gcmUser!=null)
        //this.gcmUser = in.readLong();
    }

    public static final Creator<AnsweredByUser> CREATOR = new Creator<AnsweredByUser>() {
        public AnsweredByUser createFromParcel(Parcel source) {
            return new AnsweredByUser(source);
        }

        public AnsweredByUser[] newArray(int size) {
            return new AnsweredByUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
