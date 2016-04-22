package com.indiainnovates.pucho.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Raghunandan on 28-02-2016.
 */
public class SearchQuestions implements Parcelable {

    private String content;

    private int id;

    private String title;

    private String asked_on;

    private boolean active;

    private int user_id,upvote,downvote;

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

    private SearchUser user;

    protected SearchQuestions(Parcel in) {
        content = in.readString();
        id = in.readInt();
        title = in.readString();
        asked_on = in.readString();
        active =   in.readByte() != 0;
        user_id = in.readInt();
        upvote = in.readInt();
        downvote = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(asked_on);
        dest.writeByte((byte) (isActive() ? 1 : 0));
        dest.writeInt(user_id);
        dest.writeInt(upvote);
        dest.writeInt(downvote);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchQuestions> CREATOR = new Creator<SearchQuestions>() {
        @Override
        public SearchQuestions createFromParcel(Parcel in) {
            return new SearchQuestions(in);
        }

        @Override
        public SearchQuestions[] newArray(int size) {
            return new SearchQuestions[size];
        }
    };

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getAsked_on ()
    {
        return asked_on;
    }

    public void setAsked_on (String asked_on)
    {
        this.asked_on = asked_on;
    }

    public boolean isActive ()
    {
        return active;
    }

    public void setActive (boolean active)
    {
        this.active = active;
    }

    public int getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (int user_id)
    {
        this.user_id = user_id;
    }

    public SearchUser getUser ()
    {
        return user;
    }

    public void setUser (SearchUser user)
    {
        this.user = user;
    }

}
