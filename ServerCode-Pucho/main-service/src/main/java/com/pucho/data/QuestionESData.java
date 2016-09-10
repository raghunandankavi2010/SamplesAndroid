package com.pucho.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.pucho.domain.Answer;
import com.pucho.domain.Question;
import com.pucho.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class QuestionESData {
	private Long id;
	private Long userId;
	private String title;
	private String content;
	private Integer upvote;
	private Integer downvote;
	private Integer shareCount;
	private String audioFileUrl;
	private String askedOn;
	private List<String> tags;
	private String userName;

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Question getQuestion() {
		Question ques = new Question();
		ques.setId(this.id);
		ques.setUserId(this.userId);
		ques.setTitle(this.title);
		ques.setContent(this.content);
		ques.setUpvote(this.upvote);
		ques.setDownvote(this.downvote);
		ques.setShareCount(this.shareCount);
		ques.setAudioFileUrl(this.audioFileUrl);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
		try {
			ques.setAskedOn(df.parse(this.askedOn));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ques;
	}
}
