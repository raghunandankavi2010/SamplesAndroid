package com.indiainnovates.pucho.api;

import android.text.TextUtils;
import android.util.Log;

import com.indiainnovates.pucho.PuchoApplication;
import com.indiainnovates.pucho.listeners.OnLoginFinishedListener;
import com.indiainnovates.pucho.models.SignUpPost;
import com.indiainnovates.pucho.models.SuccessSignUpResponse;
import com.indiainnovates.pucho.utils.Utility;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import javax.inject.Inject;

import retrofit.Retrofit;
import rx.Observable;

//import com.indiainnovates.pucho.dagger.DependencyInjector;

/**
 * Created by Raghunandan on 24-12-2015.
 */
public class SignUpApi {
    private boolean checkn,checke,checkp;

    //@Inject
    //OkHttpClient okHttpClient;

    @Inject
    Retrofit retrofit;

    public SignUpApi() {
        PuchoApplication.netcomponent().inject(this);
       // DependencyInjector.getNetworkComponent().inject(this);
    }

    public void validate(final String name, final String email, final String password, OnLoginFinishedListener listener) {


        if (!TextUtils.isEmpty(name) && Utility.validateNameRegex(name)) {
            checkn = true;
        } else {
            listener.onUsernameError();
            checkn = false;
        }
        if (!TextUtils.isEmpty(email) && Utility.validateEmailRegex(email)) {

            checke = true;
        } else {
            listener.onEmailError();
            checke = false;
        }
        if (!TextUtils.isEmpty(password) && Utility.validatePasswordRegex(password)) {

            checkp = true;
        } else {
            listener.onPasswordError();
            checkp =false;
        }

        if(checkn && checke && checkp)
        {
            listener.onSuccess();
        }else
        {
            Log.i("Failed","Failed");
        }



    }

    public Observable<List<SuccessSignUpResponse>> postSignUpDetails_server(String name, String email, String password) {

        SignUpPost signuppost=new SignUpPost();
        signuppost.setFullName(name);
        signuppost.setEmail(email);
        signuppost.setUsername(name);
        signuppost.setPassword(password);


        return retrofit.create(Api.class).post_signUpdetails_server(signuppost);

    }

}



