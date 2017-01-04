package com.indiainnovates.pucho.api;

import com.indiainnovates.pucho.PuchoApplication;
import com.indiainnovates.pucho.models.MyQuestionsFetched;

import java.util.List;

import javax.inject.Inject;

import retrofit.Retrofit;
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

        Observable<List<MyQuestionsFetched>> questions = retrofit.create(Api.class).fetch_question_userId(String.valueOf(userId));
        questions.cache();
        return questions;
        }
}
