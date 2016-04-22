package com.indiainnovates.pucho.api;

import com.indiainnovates.pucho.PuchoApplication;
import com.indiainnovates.pucho.models.Answers;

import java.util.List;

import javax.inject.Inject;

import retrofit.Retrofit;
import rx.Observable;

/**
 * Created by Raghunandan on 01-02-2016.
 */
public class AnswerFetchApi {

    @Inject
    Retrofit retrofit;


    public AnswerFetchApi()
    {
        PuchoApplication.netcomponent().inject(this);
    }

    public Observable<List<Answers>> fetchAnswer(int questionId) {


        Observable<List<Answers>> response =retrofit.create(Api.class).fetch_Answers(questionId);
        response.cache();
        return response;
    }
}
