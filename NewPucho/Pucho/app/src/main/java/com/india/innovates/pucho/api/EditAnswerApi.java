package com.india.innovates.pucho.api;

import com.india.innovates.pucho.PuchoApplication;
import com.india.innovates.pucho.listeners.EditPostAnswerListener;
import com.india.innovates.pucho.models.PostEditAnswer;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() ==204)
                {
                    editPostAnswerListener.onSuccessfullEdit("Success");
                }else
                {
                    editPostAnswerListener.onSuccessfullEdit("Failure");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call,Throwable t) {

                t.printStackTrace();
                editPostAnswerListener.onSuccessfullEdit("Failure");
            }
        });
    }
}
