package com.indiainnovates.pucho.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tamil on 5/16/2015.
 */
public class Answers implements Parcelable{

/*    [{
        "active": true,
                "id": 1,
                "questionId": 2,
                "userId": 1,
                "content": "Test Answer",
                "upvote": 0,
                "downvote": 0,
                "shareCount": null,
                "answerdOn": "2015-04-30 08:36:23",
    "user": {
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
    }*/

    private boolean active;
    private int id,questionId,upvote,downvote;
    private int userId;
    private String content;
    private int shareCount;
    private String  answerdOn;

    private AnsweredByUser user;

    public AnsweredByUser getAnsweredByUser() {
        return user;
    }

    public void setAnsweredByUser(AnsweredByUser answeredByUser) {
        this.user = answeredByUser;
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

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getUpvote() {
        return upvote;
    }

    public void setUpvote(int upvote) {
        this.upvote = upvote;
    }

    public int getDownvote() {
        return downvote;
    }

    public void setDownvote(int downvote) {
        this.downvote = downvote;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public String getAnsweredOn() {
        return answerdOn;
    }

    public void setAnsweredOn(String answeredOn) {
        this.answerdOn = answeredOn;
    }

/*
    "languageContents": [{
        "active": true,
                "id": 109,
                "answerId": 72,
                "language": "HINDI",
                "content": "à¤®à¥à¤à¥‡ à¤®à¥‡à¤°à¥‡"
    }]*/

    private List<AnswerLanguageContents> languageContents = new ArrayList<>();

    public List<AnswerLanguageContents> getLanguageContents() {
        return languageContents;
    }

    public void setLanguageContents(List<AnswerLanguageContents> languageContents) {
        this.languageContents = languageContents;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeByte((byte) (isActive() ? 1 : 0));

        dest.writeInt(this.id);
        dest.writeInt(this.questionId);
        dest.writeInt(this.userId);
        dest.writeInt(this.upvote);
        dest.writeInt(this.downvote);
        dest.writeInt(this.shareCount);

        
        dest.writeString(this.content);
        
        dest.writeString(this.answerdOn);

        dest.writeParcelable(this.user, flags);

        dest.writeTypedList(this.languageContents);

        

    }

    protected Answers(Parcel in) {

        this.active = in.readByte() != 0;

        this.id = in.readInt();
        this.questionId = in.readInt();
        this.userId = in.readInt();
        this.upvote = in.readInt();
        this.downvote = in.readInt();
        this.shareCount = in.readInt();

        this.content = in.readString();
        this.answerdOn = in.readString();


        this.user = in.readParcelable(AnsweredByUser.class.getClassLoader());
        in.readTypedList(languageContents,AnswerLanguageContents.CREATOR);
    }

    public static final Creator<Answers> CREATOR = new Creator<Answers>() {
        public Answers createFromParcel(Parcel source) {
            return new Answers(source);
        }

        public Answers[] newArray(int size) {
            return new Answers[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
