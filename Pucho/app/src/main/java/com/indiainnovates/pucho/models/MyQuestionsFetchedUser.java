package com.indiainnovates.pucho.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Raghunandan on 18-03-2016.
 */
public class MyQuestionsFetchedUser implements Parcelable{

    private boolean active;
    private int id;
    private String fullName,profession,externalUserId,username,phone,email,linkedin,personalUrl;

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

    /* "user": {
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
        "gcmUser": null
    },
            "answers": [{
        "active": true,
                "id": 60,
                "questionId": 2,
                "userId": 24,
                "content": "hhb",
                "upvote": 0,
                "downvote": 0,
                "shareCount": null,
                "answerdOn": "2016-03-10 23:12:27",
                "user": {
            "active": true,
                    "id": 24,
                    "fullName": "MeeraSingh",
                    "profession": null,
                    "externalUserId": null,
                    "username": "MeeraSingh",
                    "phone": null,
                    "email": "saimeera.singh@gmail.com",
                    "linkedin": null,
                    "personalUrl": null,
                    "userEducations": [],
            "gcmUser": {
                "id": 11,
                        "registrationId": "d9044AMEmPE:APA91bGAKt2e-ll7BMvw8Wu279IDYUZClxFGSnrN1_O_u47gNO1gtIajL5XmH0FsN1md7MGaqd_54yM4n2VNAlo1ghGRsXVxeYER5gDc3oAxQ6VIGNzY5fy4Q6hShU1zygIysoILbF-R"
            }
        }*/


    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeByte((byte) (isActive() ? 1 : 0));

        dest.writeInt(this.id);

        dest.writeString(this.fullName);
        dest.writeString(this.profession);
        dest.writeString(this.externalUserId);
        dest.writeString(this.username);
        dest.writeString(this.phone);
        dest.writeString(this.email);
        dest.writeString(this.linkedin);
        dest.writeString(this.personalUrl);
        //if(gcmUser!=null)
        //dest.writeLong(this.gcmUser);

    }

    // private String fullName,profession,externalUserId,username,phone,email,linkedin,personalUrl;
    protected MyQuestionsFetchedUser(Parcel in) {

        this.active =  in.readByte() != 0;

        this.id = in.readInt();


        this.fullName = in.readString();
        this.profession = in.readString();
        this.externalUserId = in.readString();
        this.username = in.readString();
        this.phone = in.readString();
        this.email = in.readString();
        this.linkedin= in.readString();
        this.personalUrl = in.readString();

        //if(gcmUser!=null)
        //this.gcmUser = in.readLong();
    }

    public static final Creator<MyQuestionsFetchedUser> CREATOR = new Creator<MyQuestionsFetchedUser>() {
        public MyQuestionsFetchedUser createFromParcel(Parcel source) {
            return new MyQuestionsFetchedUser(source);
        }

        public MyQuestionsFetchedUser[] newArray(int size) {
            return new MyQuestionsFetchedUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
