package com.pucho.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;

import com.pucho.domain.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AnswerESData {
	private Long id;
    private Long questionId;
    private Long userId;
    private String content;
    private Integer upvote;
    private Integer downvote;
    private Integer shareCount;
    private String answerdOn;
    
    @Override
    public String toString() {
    	return new Gson().toJson(this);
    }

    public Answer getAnswer() {
        Answer answer = new Answer();
        answer.setId(this.id);
        answer.setQuestionId(this.questionId);
        answer.setUserId(this.userId);
        answer.setContent(this.content);
        answer.setUpvote(this.upvote);
        answer.setDownvote(this.downvote);
        answer.setShareCount(this.shareCount);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
        try {
            answer.setAnswerdOn(df.parse(this.answerdOn));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return answer;
    }
}
