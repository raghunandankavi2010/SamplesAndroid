package com.indiainnovates.pucho.api;

import android.text.TextUtils;

import com.indiainnovates.pucho.PuchoApplication;
import com.indiainnovates.pucho.listeners.OnCheckPostQuestionContentEmptyListener;
import com.indiainnovates.pucho.models.PostQuestionContent;
import com.indiainnovates.pucho.models.QuestionContentModel;
import com.indiainnovates.pucho.utils.Utility;
import com.squareup.okhttp.OkHttpClient;

import java.nio.charset.Charset;
import java.util.List;

import javax.inject.Inject;

import retrofit.Retrofit;
import rx.Observable;

/**
 * Created by Raghunandan on 29-01-2016.
 */
public class AskQuestionApi {
    //@Inject
    //OkHttpClient okHttpClient;

    @Inject
    Retrofit retrofit;

    public AskQuestionApi()
    {
        PuchoApplication.netcomponent().inject(this);
    }


    public void validateifContentEmpty(String questionContent, OnCheckPostQuestionContentEmptyListener checkEmpty) {

        if(!TextUtils.isEmpty(questionContent.trim()) && (questionContent.length()>10))
        {
            checkEmpty.onSuccess();
        }else
        {
            checkEmpty.onContentEmpty();
        }
    }

    public Observable<List<QuestionContentModel>> sendQuestionContentToServer(String questionContent, int userid) {

        PostQuestionContent postQuestionContent = new PostQuestionContent();
        postQuestionContent.setTitle(questionContent);
        /// for testing use user id 1. in production use userid
        postQuestionContent.setUserId(userid);
        postQuestionContent.setAskedOn(Utility.Datetime());
        postQuestionContent.setUpvote(0);
        postQuestionContent.setDownvote(0);


        return retrofit.create(Api.class).postQuestion_server(postQuestionContent);

    }
}
