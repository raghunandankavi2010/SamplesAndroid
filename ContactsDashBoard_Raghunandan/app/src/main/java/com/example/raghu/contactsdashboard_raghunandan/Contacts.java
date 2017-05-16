package com.example.raghu.contactsdashboard_raghunandan;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by raghu on 10/5/17.
 */

public class Contacts implements Parcelable {

    private  String id,name,number,email,photo,callDate,dateString,dob;

    private int callType,duration;


    protected Contacts(Parcel in) {
        id = in.readString();
        name = in.readString();
        number = in.readString();
        email = in.readString();
        photo = in.readString();
        duration = in.readInt();
        callDate = in.readString();
        dateString = in.readString();
        callType = in.readInt();
        dob = in.readString();
    }

    public Contacts() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(number);
        dest.writeString(email);
        dest.writeString(photo);
        dest.writeInt(duration);
        dest.writeString(callDate);
        dest.writeString(dateString);
        dest.writeInt(callType);
        dest.writeString(dob);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contacts> CREATOR = new Creator<Contacts>() {
        @Override
        public Contacts createFromParcel(Parcel in) {
            return new Contacts(in);
        }

        @Override
        public Contacts[] newArray(int size) {
            return new Contacts[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }

    public int getDuration() {
        return duration;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }
}
