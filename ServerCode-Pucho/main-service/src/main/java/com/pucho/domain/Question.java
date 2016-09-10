package com.pucho.domain;

import org.minnal.instrument.entity.AggregateRoot;

import com.pucho.data.AnswerESData;
import com.pucho.data.QuestionESData;
import org.minnal.instrument.entity.Searchable;

import javax.persistence.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dinesh.rathore on 22/02/15.
 */
@Entity
@Table(name="questions")
@AggregateRoot
@EntityListeners(value = {QuestionEventListener.class})   
public class Question extends SoftDeletableModel {
    private Long id;
    @Searchable
    private Long userId;
    private String title;
    private String content;
    private Integer upvote;
    private Integer downvote;
    private Integer shareCount;
    private String audioFileUrl;

    @OrderBy(value = "DESC")
    private Date askedOn;

    private User user;
    private Set<Answer> answers;
    private Set<QuestionLanguageContent> languageContents;


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public Integer getUpvote() {
        return upvote;
    }

    public void setUpvote(Integer upvote) {
        this.upvote = upvote;
    }

    public Integer getDownvote() {
        return downvote;
    }

    public void setDownvote(Integer downvote) {
        this.downvote = downvote;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public String getAudioFileUrl() {
        return audioFileUrl;
    }

    public void setAudioFileUrl(String audioFileUrl) {
        this.audioFileUrl = audioFileUrl;
    }

    public Date getAskedOn() {
        return askedOn;
    }

    public void setAskedOn(Date askedOn) {
        this.askedOn = askedOn;
    }

    @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="userId", insertable=false, updatable=false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(fetch= FetchType.EAGER,cascade= CascadeType.ALL, mappedBy = "questionId")
    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public void addAnswer(Answer answer){
        answer.setQuestionId(this.getId());
        this.answers.add(answer);
    }

    public QuestionESData toESData() {
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
    	String askedOnISO = df.format(this.askedOn);
    	QuestionESData quesESData = new QuestionESData(this.id, this.userId, this.title, this.content,
    			this.upvote, this.downvote, this.shareCount, 
    			this.audioFileUrl, askedOnISO, null, null);
    	return quesESData;
    }

    @OneToMany(fetch= FetchType.EAGER,cascade= CascadeType.ALL, mappedBy = "questionId")
    public Set<QuestionLanguageContent> getLanguageContents() {
        return languageContents;
    }

    public void setLanguageContents(Set<QuestionLanguageContent> languageContents) {
        this.languageContents = languageContents;
    }
    public void addLanguageContents(QuestionLanguageContent questionLanguageContent){
        questionLanguageContent.setQuestionId(this.getId());
        questionLanguageContent.persist();
    }
}
