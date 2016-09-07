package com.india.innovates.pucho.api;

import android.text.TextUtils;

import com.india.innovates.pucho.PuchoApplication;
import com.india.innovates.pucho.listeners.OnCheckPostQuestionContentEmptyListener;
import com.india.innovates.pucho.models.PostQuestionContent;
import com.india.innovates.pucho.models.QuestionContentModel;
import com.india.innovates.pucho.utils.Utility;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
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
