package com.indiainnovates.pucho.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raghunandan on 18-03-2016.
 */
public class MyQuestionsFetched implements Parcelable{

    private boolean active;
    private int id;
    private int userId;
    private String title;
    private String content;
    private int upvote;
    private int downvote;
    private int shareCount;
    private String audioFileUrl;
    private String askedOn;
    private MyQuestionsFetchedUser user;

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

    public String getAudioFileUrl() {
        return audioFileUrl;
    }

    public void setAudioFileUrl(String audioFileUrl) {
        this.audioFileUrl = audioFileUrl;
    }

    public String getAskedOn() {
        return askedOn;
    }

    public void setAskedOn(String askedOn) {
        this.askedOn = askedOn;
    }

    public MyQuestionsFetchedUser getUser() {
        return user;
    }

    public void setUser( MyQuestionsFetchedUser user) {
        this.user = user;
    }



    // Parcelling part

 /*   "languageContents":[
    {
        "active":true,
            "id":19,
            "questionId":25,
            "language":"HINDI",
            "content":"Android à¤“à¤à¤¸ à¤•à¥‡ à¤¨à¤µà¥€à¤¨à¤¤à¤® à¤¸à¤‚à¤¸à¥à¤•à¤°à¤£ à¤®à¥‡à¤‚ à¤•à¥à¤¯à¤¾ à¤¹à¥ˆ?"
    }
    ]*/

    private List<MyQuestionLanguageContents> languageContents = new ArrayList<>();

    public List<MyQuestionLanguageContents> getLanguageContents() {
        return languageContents;
    }

    public void setLanguageContents(List<MyQuestionLanguageContents> languageContents) {
        this.languageContents = languageContents;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeByte((byte) (isActive() ? 1 : 0));

        dest.writeInt(this.id);
        dest.writeInt(this.userId);

        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeInt(this.upvote);
        dest.writeInt(this.downvote);
        dest.writeInt(this.shareCount);
        dest.writeString(this.audioFileUrl);
        dest.writeString(this.askedOn);
        dest.writeParcelable(this.user, flags);
        dest.writeTypedList(this.languageContents);
        //if(gcmUser!=null)
        //dest.writeLong(this.gcmUser);

    }

    protected MyQuestionsFetched(Parcel in) {

        this.active =  in.readByte() != 0;

        this.id = in.readInt();
        this.userId = in.readInt();

        this.title = in.readString();
        this.content = in.readString();
        this.upvote = in.readInt();
        this.downvote = in.readInt();
        this.shareCount = in.readInt();
        this.audioFileUrl = in.readString();
        this.askedOn = in.readString();
        this.user = in.readParcelable(MyQuestionsFetchedUser.class.getClassLoader());
        in.readTypedList(languageContents,MyQuestionLanguageContents.CREATOR);

        //if(gcmUser!=null)
        //this.gcmUser = in.readLong();
    }

    public static final Creator<MyQuestionsFetched> CREATOR = new Creator<MyQuestionsFetched>() {
        public MyQuestionsFetched createFromParcel(Parcel source) {
            return new MyQuestionsFetched(source);
        }

        public MyQuestionsFetched[] newArray(int size) {
            return new MyQuestionsFetched[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
