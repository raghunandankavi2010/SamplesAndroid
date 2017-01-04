package com.indiainnovates.pucho.api;

import android.text.TextUtils;

import com.indiainnovates.pucho.ComposeAnswer;
import com.indiainnovates.pucho.PuchoApplication;
import com.indiainnovates.pucho.listeners.OnCheckPostAnswerContent;
import com.indiainnovates.pucho.models.PostAnswerContentModel;
import com.indiainnovates.pucho.models.PostAnswerResponse;
import com.indiainnovates.pucho.presenters.ComposeAnswerPresenter;
import com.indiainnovates.pucho.utils.Utility;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import javax.inject.Inject;

import retrofit.Retrofit;
import rx.Observable;

/**
 * Created by Raghunandan on 29-01-2016.
 */
public class ComposeAnswerApi {

    @Inject
    OkHttpClient okHttpClient;

    @Inject
    Retrofit retrofit;

    public ComposeAnswerApi()
    {
        PuchoApplication.netcomponent().inject(this);

    }

    public void validatePostAnswerContent(String postAnswerContent, OnCheckPostAnswerContent checkPostAnswer) {

        if(!TextUtils.isEmpty(postAnswerContent.trim()) && postAnswerContent.length()>10)
        {
            checkPostAnswer.onSuccess();
        }
        else
        {
            checkPostAnswer.onEmptyContent();
        }
    }

    public Observable<List<PostAnswerResponse>> postAnswerServer(int userId,int questionid, String postAnswerContent) {

        PostAnswerContentModel postAnswerContentModel = new PostAnswerContentModel();
        // for testing user id is 1. for production use userID
        postAnswerContentModel.setUserId(userId);
        postAnswerContentModel.setContent(postAnswerContent);
        postAnswerContentModel.setUpvote(0);
        postAnswerContentModel.setDownvote(0);
        postAnswerContentModel.setAnswerdOn(Utility.Datetime());

        // for testing question id is set to 4
        return retrofit.create(Api.class).postAnswer_server(questionid,postAnswerContentModel);
    }
}
