package com.indiainnovates.pucho.presenters;

import com.indiainnovates.pucho.api.QuestionsApi;

import javax.inject.Inject;

/**
 * Created by Raghunandan on 28-12-2015.
 */
public class QuestionFeedPresenter {

    private QuestionsApi questionsApi;
    @Inject
    public QuestionFeedPresenter(QuestionsApi questionsApi)
    {
        this.questionsApi = questionsApi;
    }
}
