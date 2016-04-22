package com.indiainnovates.pucho.presenters;

import com.indiainnovates.pucho.api.EditQuestionApi;
import com.indiainnovates.pucho.events.EditQuestionErrorEvent;
import com.indiainnovates.pucho.events.EditQuestionEvent;
import com.indiainnovates.pucho.listeners.EditQuestionResponseListener;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by Raghunandan on 14-04-2016.
 */
public class EditQuestionPresenter implements EditQuestionResponseListener{

    private EditQuestionApi editQuestionApi;

    @Inject
    public EditQuestionPresenter(EditQuestionApi editQuestionApi) {
        this.editQuestionApi = editQuestionApi;
    }
/*
    public void setContext(EditActivity context) {
        this.context = context;
    }*/

    public void postEditQuestion(int questionid, String content, int user_id) {


        editQuestionApi.postEditQuestion_details(questionid,content,user_id,this);

    }

    public void onDestroy() {

    }

    @Override
    public void onSuccess(String response) {

        EventBus.getDefault().post(new EditQuestionEvent(response));
    }

    @Override
    public void onFailure(String failure) {

        EventBus.getDefault().post(new EditQuestionErrorEvent("failure"));
    }
}
