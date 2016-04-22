package com.indiainnovates.pucho.presenters;

import com.indiainnovates.pucho.api.EditAnswerApi;
import com.indiainnovates.pucho.api.EditQuestionApi;
import com.indiainnovates.pucho.events.EditAnswerFailureEvent;
import com.indiainnovates.pucho.events.EditAnswerPostSuccessEvent;
import com.indiainnovates.pucho.listeners.EditPostAnswerListener;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by Raghunandan on 18-04-2016.
 */
public class EditAnswerPresenter implements EditPostAnswerListener{

    private EditAnswerApi editAnswerApi;

    @Inject
    public EditAnswerPresenter(EditAnswerApi editAnswerApi) {
        this.editAnswerApi = editAnswerApi;
    }

    public void post_editedanswer(int question_id, int answer_id, String content) {

        editAnswerApi.post(question_id,answer_id,content,this);
    }

    @Override
    public void onSuccessfullEdit(String success) {

        EventBus.getDefault().post(new EditAnswerPostSuccessEvent(success));
    }

    @Override
    public void onFailure(String failure) {

        EventBus.getDefault().post(new EditAnswerFailureEvent(failure));
    }
}
