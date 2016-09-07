package com.india.innovates.pucho.api;

import com.india.innovates.pucho.PuchoApplication;
import com.india.innovates.pucho.models.MyQuestionsFetched;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Raghunandan on 18-03-2016.
 */
public class MyQuestionsApi {

    @Inject
    Retrofit retrofit;

    public MyQuestionsApi()
    {
        PuchoApplication.netcomponent().inject(this);
    }

    public Observable<List<MyQuestionsFetched>> fetch_My_Questions(int userId) {

        Observable<List<MyQuestionsFetched>> questions = retrofit.create(Api.class).fetch_question_userId(String.valueOf(userId),true);
        questions.cache();
        return questions;
        }
}
