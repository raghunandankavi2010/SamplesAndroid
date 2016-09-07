package com.india.innovates.pucho.events;

import com.india.innovates.pucho.models.QuestionContentModel;

import java.util.List;

/**
 * Created by Raghunandan on 29-01-2016.
 */
public class PostQuestionResponeEvent {

    private List<QuestionContentModel> questionContentModelList;

    public PostQuestionResponeEvent(List<QuestionContentModel> questionContentModelList)
    {
        this.questionContentModelList =questionContentModelList;
    }

    public List<QuestionContentModel> getQuestionContentModelList() {
        return questionContentModelList;
    }
}
