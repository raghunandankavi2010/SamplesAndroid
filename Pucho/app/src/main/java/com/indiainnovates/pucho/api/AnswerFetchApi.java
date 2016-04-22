package com.indiainnovates.pucho.api;

import android.util.Log;

import com.indiainnovates.pucho.PuchoApplication;
import com.indiainnovates.pucho.listeners.OnSuccessfulDeleteListener;
import com.indiainnovates.pucho.models.Answers;
import com.indiainnovates.pucho.models.FollowModel;


import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
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

    public void delete_question(int question_id, final OnSuccessfulDeleteListener onSuccessfulDeleteListener) {

        Call<ResponseBody> response =retrofit.create(Api.class).delete_question(question_id);

        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                int code = response.code();
                /* on Success the http status code is 204 */
                 if(code == 204)
                 {
                    onSuccessfulDeleteListener.onSuccess();

                 }
                 /* status code 404 not found if question with id is not found on server */
                 else if(code == 404)
                 {
                     onSuccessfulDeleteListener.onFailure();
                 }
            }

            @Override
            public void onFailure(Throwable t) {

                onSuccessfulDeleteListener.onError(t);
            }
        });
    }

    public Observable<FollowModel> follow_question(int user_id, int question_id) {

        Observable<FollowModel> response =retrofit.create(Api.class).follow_question(user_id,question_id);
        return response;
    }
}
