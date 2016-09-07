package com.india.innovates.pucho.api;

import android.text.TextUtils;
import android.util.Log;

import com.india.innovates.pucho.PuchoApplication;
import com.india.innovates.pucho.listeners.OnLoginFinishedListener;
import com.india.innovates.pucho.models.SignUpPost;
import com.india.innovates.pucho.models.SuccessSignUpResponse;
import com.india.innovates.pucho.utils.Utility;


import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;

//import com.india.innovates.pucho.dagger.DependencyInjector;

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



