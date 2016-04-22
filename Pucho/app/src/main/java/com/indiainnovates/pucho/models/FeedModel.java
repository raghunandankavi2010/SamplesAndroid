package com.indiainnovates.pucho.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raghunandan on 31-01-2016.
 */
public class FeedModel implements Parcelable {

   /* {
        "active": true,
            "id": 2,
            "userId": 1,
            "title": "What does/did Germany do right?",
            "content": null,
            "upvote": 0,
            "downvote": 0,
            "shareCount": null,
            "audioFileUrl": null,
            "askedOn": "2015-04-30 08:28:32",
            "user": {

    },
        "answers": []
    },*/


    private boolean active;
    private int id, userId, upvote, downvote, shareCount;
    private String title, content, audioFileUrl,askedOn;

    private QuestionAskedBy user;

    public FeedModel() {

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public String getAskedOn() {
        return askedOn;
    }

    public void setAskedOn(String askedOn) {
        this.askedOn = askedOn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAudioFileUrl() {
        return audioFileUrl;
    }

    public void setAudioFileUrl(String audioFileUrl) {
        this.audioFileUrl = audioFileUrl;
    }

    public QuestionAskedBy getUser() {
        return user;
    }

    public void setUser(QuestionAskedBy user) {
        this.user = user;
    }


    private List<QuestionLanguageContents> languageContents = new ArrayList<>();

    public List<QuestionLanguageContents> getLanguageContents ()
    {
        return languageContents;
    }

    public void setLanguageContents (List<QuestionLanguageContents> languageContents)
    {
        this.languageContents = languageContents;
    }

    // Parcelling part

    /*private boolean active;
    private int id,userId,upvote,downvote,shareCount;
    private String title,content,audioFileUrl,askedOn;

    private QuestionAskedBy  user;*/
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeByte((byte) (isActive() ? 1 : 0));

        dest.writeInt(this.id);
        dest.writeInt(this.userId);
        dest.writeInt(this.upvote);
        dest.writeInt(this.downvote);
        dest.writeInt(this.shareCount);


        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.audioFileUrl);
        dest.writeString(this.askedOn);


        dest.writeParcelable(user, flags);

        dest.writeTypedList(this.languageContents);
    }

    protected FeedModel(Parcel in) {

        this.active = in.readByte() != 0;

        this.id = in.readInt();
        this.userId = in.readInt();
        this.upvote = in.readInt();
        this.downvote = in.readInt();
        this.shareCount = in.readInt();


        this.title = in.readString();
        this.content = in.readString();
        this.audioFileUrl = in.readString();
        this.askedOn = in.readString();

        this.user = in.readParcelable(QuestionAskedBy.class.getClassLoader());

        in.readTypedList(languageContents,QuestionLanguageContents.CREATOR);
    }

    public static final Creator<FeedModel> CREATOR = new Creator<FeedModel>() {
        public FeedModel createFromParcel(Parcel source) {
            return new FeedModel(source);
        }

        public FeedModel[] newArray(int size) {
            return new FeedModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
