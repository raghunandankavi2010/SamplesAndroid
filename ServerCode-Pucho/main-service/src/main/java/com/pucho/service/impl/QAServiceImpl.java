package com.pucho.service.impl;

import com.pucho.domain.Question;
import com.pucho.domain.QuestionFollower;
import com.pucho.domain.UserFollower;
import com.pucho.helper.Feed;
import com.pucho.helper.GenericResponse;
import com.pucho.service.QAService;

import java.util.List;

/**
 * Created by dinesh.rathore on 25/01/16.
 */
public class QAServiceImpl implements QAService {
    @Override
    public List<Question> getFollowedQuestions(Long userId) {
        return null;
    }

    @Override
    public List<Feed> getFeedForUser(Long userId) {
        return null;
    }

    @Override
    public GenericResponse followUser(Long followerUserId, Long followedUserId) {
        GenericResponse response = new GenericResponse();
        UserFollower userFollower = new UserFollower();
        userFollower.setFollowedUserId(followedUserId);
        userFollower.setFollowerUserId(followerUserId);
        userFollower.persist();
        response.setSuccess(true);
        return response;
    }

    @Override
    public GenericResponse followQuestion(Long followerUserId, Long followedQuestionId) {
        GenericResponse response = new GenericResponse();
        QuestionFollower questionFollower = new QuestionFollower();
        questionFollower.setFollowedQuestionId(followedQuestionId);
        questionFollower.setFollowerUserId(followerUserId);
        questionFollower.persist();
        response.setSuccess(true);
        return response;
    }
}
