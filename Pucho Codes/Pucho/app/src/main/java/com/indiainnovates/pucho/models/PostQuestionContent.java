package com.indiainnovates.pucho.models;

/**
 * Created by Raghunandan on 29-01-2016.
 */
public class PostQuestionContent {


    private int userId,upvote,downvote;
    private String title;
    private String askedOn;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAskedOn() {
        return askedOn;
    }

    public void setAskedOn(String askedOn) {
        this.askedOn = askedOn;
    }
}
