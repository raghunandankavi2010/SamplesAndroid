package com.indiainnovates.pucho.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tamil on 5/16/2015.
 */
public class Answers implements Parcelable{


    private boolean active;
    private int id,questionId,upvote,downvote;
    private int userId;
    private String content;
    private int shareCount;
    private String  answeredOn;

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
        return answeredOn;
    }

    public void setAnsweredOn(String answeredOn) {
        this.answeredOn = answeredOn;
    }


/*    private boolean active;
    private int id,questionId,upvote,downvote;
    private int userId;
    private String content;
    private int shareCount;
    private String  answeredOn;*/
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
        
        dest.writeString(this.answeredOn);

        

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
        this.answeredOn = in.readString();

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
