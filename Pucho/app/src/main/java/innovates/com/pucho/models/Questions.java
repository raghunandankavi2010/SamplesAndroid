package innovates.com.pucho.models;

import java.util.ArrayList;

/**
 * Created by Tamil on 5/16/2015.
 */
public class Questions {


    private boolean active;
    private int questionID;
    private int userID;
    private String title;
    private String content;
    private int upvote;
    private int downvote;
    private int shareCount;
    private String audioFileURL;
    private String askedOn;
    private User user;
    private ArrayList<Answers> answers;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public String getAudioFileURL() {
        return audioFileURL;
    }

    public void setAudioFileURL(String audioFileURL) {
        this.audioFileURL = audioFileURL;
    }

    public String getAskedOn() {
        return askedOn;
    }

    public void setAskedOn(String askedOn) {
        this.askedOn = askedOn;
    }

    public ArrayList<Answers> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answers> answers) {
        this.answers = answers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
