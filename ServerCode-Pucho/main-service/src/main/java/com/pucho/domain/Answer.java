package com.pucho.domain;

import javax.persistence.*;

import com.pucho.data.AnswerESData;
import org.minnal.instrument.entity.Searchable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by dinesh.rathore on 22/02/15.
 */
@Entity
@EntityListeners(value = { AnswerEventListener.class })
@Table(name = "answers")
public class Answer extends SoftDeletableModel {
	private Long id;
	private Long questionId;

	@Searchable
	private Long userId;
	private String content;
	private Integer upvote;
	private Integer downvote;
	private Integer shareCount;
	private Date answerdOn;
	private User user;
    private Set<AnswerLanguageContent> languageContents;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Date getAnswerdOn() {
		return answerdOn;
	}

	public void setAnswerdOn(Date answerdOn) {
		this.answerdOn = answerdOn;
	}

	public AnswerESData toESData() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
		String answeredOnISO = df.format(this.answerdOn);
		AnswerESData esData = new AnswerESData(this.id, this.questionId, this.userId, this.content, this.upvote,
				this.downvote, this.shareCount, answeredOnISO);
		return esData;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    @OneToMany(fetch= FetchType.EAGER,cascade= CascadeType.ALL, mappedBy = "answerId")
    public Set<AnswerLanguageContent> getLanguageContents() {
        return languageContents;
    }

    public void setLanguageContents(Set<AnswerLanguageContent> languageContents) {
        this.languageContents = languageContents;
    }

    public void addLanguageContents(AnswerLanguageContent answerLanguageContent){
        answerLanguageContent.setAnswerId(this.getId());
        answerLanguageContent.persist();
    }
}
