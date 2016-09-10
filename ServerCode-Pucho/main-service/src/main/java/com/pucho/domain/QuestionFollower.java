package com.pucho.domain;

import javax.persistence.*;

/**
 * Created by dinesh.rathore on 12/04/16.
 */
@Entity
@Table(name = "question_followers")
public class QuestionFollower extends SoftDeletableModel {

    private Long id;
    private Long followedQuestionId;
    private Long followerUserId;

    public void setFollowedQuestionId(Long followedQuestionId) {
        this.followedQuestionId = followedQuestionId;
    }

    public Long getFollowedQuestionId() {
        return followedQuestionId;
    }

    public void setFollowerUserId(Long followerUserId) {
        this.followerUserId = followerUserId;
    }

    public Long getFollowerUserId() {
        return followerUserId;
    }

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
