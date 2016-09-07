package com.india.innovates.pucho.api;

import android.util.Log;

import com.india.innovates.pucho.PuchoApplication;
import com.india.innovates.pucho.listeners.EditQuestionResponseListener;
import com.india.innovates.pucho.models.PostQuestionContent;
import com.india.innovates.pucho.utils.Utility;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Raghunandan on 14-04-2016.
 */
public class EditQuestionApi {

    @Inject
    Retrofit retrofit;

    private String res;
    private EditQuestionResponseListener editQuestionResponseListener;

    public EditQuestionApi() {
        PuchoApplication.netcomponent().inject(this);
    }

    public String postEditQuestion_details(int questionid, String content, int user_id, final EditQuestionResponseListener editQuestionResponseListener) {

        this.editQuestionResponseListener = editQuestionResponseListener;
        PostQuestionContent postQuestionContent = new PostQuestionContent();

        postQuestionContent.setTitle(content);
        /// for testing use user id 1. in production use userid
        postQuestionContent.setUserId(user_id);
        postQuestionContent.setAskedOn(Utility.Datetime());
        postQuestionContent.setUpvote(0);
        postQuestionContent.setDownvote(0);

        Log.i("content",""+content);
        Log.i("user id",""+user_id);
        Log.i("time",""+Utility.Datetime());
        Log.i("upvote",""+0);
        Log.i("downvote",""+0);
        Call<ResponseBody> response = retrofit.create(Api.class).edit_question(postQuestionContent, questionid);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                int code = response.code();
                if (code == 204) {

                    editQuestionResponseListener.onSuccess("Succeeded");

                }
                 /* status code 404 not found if question with id is not found on server */
                else if (code == 404) {

                    editQuestionResponseListener.onFailure("Failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                t.printStackTrace();
                editQuestionResponseListener.onFailure("Failed");
            }

        });
        return res;
    }
}
