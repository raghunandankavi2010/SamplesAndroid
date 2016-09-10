package com.pucho.service;

import com.pucho.domain.Question;
import com.pucho.helper.Feed;
import com.pucho.helper.GenericResponse;

import java.util.List;

/**
 * Created by dinesh.rathore on 12/04/16.
 */
public interface QAService {
    public List<Question> getFollowedQuestions(Long userId);
    public List<Feed> getFeedForUser(Long userId);

    public GenericResponse followUser(Long followerUserId, Long followedUserId);

    public GenericResponse followQuestion(Long followerUserId, Long followedQuestionId);
}
