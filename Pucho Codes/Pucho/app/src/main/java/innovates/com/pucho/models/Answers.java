package innovates.com.pucho.models;

/**
 * Created by Tamil on 5/16/2015.
 */
public class Answers { // No need to implement parcelable. We will use EventBus

    private boolean active;
    private int answerID;
    private int questionID;
    private int userID;
    private String content;
    private int upvote;
    private int downvote;
    private int shareCount;
    private String  answeredOn;
    private User user;
    private String userName;
    private String profession;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
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

    public String getAnsweredOn() {
        return answeredOn;
    }

    public void setAnsweredOn(String answeredOn) {
        this.answeredOn = answeredOn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String professoion) {
        this.profession = professoion;
    }


}
