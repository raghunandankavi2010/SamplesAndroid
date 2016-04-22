package com.indiainnovates.pucho.models;

/**
 * Created by Raghunandan on 29-01-2016.
 */
public class PostAnswerContentModel {


    private int userId,upvote,downvote;
    private String content,answerdOn;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswerdOn() {
        return answerdOn;
    }

    public void setAnswerdOn(String answerdOn) {
        this.answerdOn = answerdOn;
    }
}
