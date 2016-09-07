package com.india.innovates.pucho.api;

import com.india.innovates.pucho.PuchoApplication;
import com.india.innovates.pucho.listeners.OnSuccessfulDeleteListener;
import com.india.innovates.pucho.models.Answers;
import com.india.innovates.pucho.models.FollowModel;


import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
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
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                int code = response.code();
                //* on Success the http status code is 204 *//*
                if(code == 204)
                {
                    onSuccessfulDeleteListener.onSuccess();

                }
                //* status code 404 not found if question with id is not found on server *//*
                else if(code == 404)
                {
                    onSuccessfulDeleteListener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {


                onSuccessfulDeleteListener.onError(t);

            }

        });
    }

    public Observable<FollowModel> follow_question(int user_id, int question_id) {

        Observable<FollowModel> response =retrofit.create(Api.class).follow_question(user_id,question_id);
        return response;
    }
}
