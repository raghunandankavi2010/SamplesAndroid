package com.indiainnovates.pucho.api;

import com.indiainnovates.pucho.PuchoApplication;
import com.indiainnovates.pucho.listeners.EditPostAnswerListener;
import com.indiainnovates.pucho.models.PostEditAnswer;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Raghunandan on 18-04-2016.
 */
public class EditAnswerApi {

    @Inject
    Retrofit retrofit;

    public EditAnswerApi()
    {
        PuchoApplication.netcomponent().inject(this);
    }

    public void post(int question_id, int answer_id, String content, final EditPostAnswerListener editPostAnswerListener) {


        PostEditAnswer postEditAnswer = new PostEditAnswer();
        postEditAnswer.setContent(content);
        Call<ResponseBody> response = retrofit.create(Api.class).edit_answer(postEditAnswer,answer_id,question_id);

        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                if(response.code() ==204)
                {
                    editPostAnswerListener.onSuccessfullEdit("Success");
                }else
                {
                    editPostAnswerListener.onSuccessfullEdit("Failure");
                }
            }

            @Override
            public void onFailure(Throwable t) {

                t.printStackTrace();
                editPostAnswerListener.onSuccessfullEdit("Failure");
            }
        });
    }
}
