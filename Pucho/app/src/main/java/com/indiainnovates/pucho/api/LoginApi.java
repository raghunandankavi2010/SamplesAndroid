package com.indiainnovates.pucho.api;

import android.text.TextUtils;
import android.util.Log;

import com.indiainnovates.pucho.PuchoApplication;
//import com.indiainnovates.pucho.dagger.DependencyInjector;
import com.indiainnovates.pucho.listeners.OnLoginCredentialsValidatorListener;
import com.indiainnovates.pucho.models.LoginPost;
import com.indiainnovates.pucho.models.LoginResponse;
import com.indiainnovates.pucho.models.LoginViaServerPost;
import com.indiainnovates.pucho.models.SignupResponse;
import com.indiainnovates.pucho.utils.Utility;
import com.squareup.okhttp.OkHttpClient;


import java.io.InputStream;
import java.security.cert.CertificateException;

import javax.inject.Inject;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.Retrofit;
import rx.Observable;

/**
 * Created by Raghunandan on 16-12-2015.
 */
public class LoginApi {

    //@Inject
    //OkHttpClient okHttpClient;

    @Inject
    Retrofit retrofit;

    private boolean checke,checkp;


    public LoginApi() {
        //DependencyInjector.getNetworkComponent().inject(this);
        PuchoApplication.netcomponent().inject(this);
    }

    public void validateLoginCredentials(String emailId, String password, OnLoginCredentialsValidatorListener validatorListener) {
        if (!TextUtils.isEmpty(emailId) && Utility.validateEmailRegex(emailId)) {

            checke = true;
        } else {
            validatorListener.onLoginEmailError();
            checke = false;
        }
        if (!TextUtils.isEmpty(password) && Utility.validatePasswordRegex(password)) {

            checkp = true;
        } else {
            validatorListener.onLoginPasswordError();
            checkp =false;
        }

        if( checke && checkp)
        {
            validatorListener.onLoginSuccess();
        }
    }

    public  Observable<SignupResponse> postLoginDetails_Server(String emailId, String password) {

        LoginViaServerPost loginPost = new LoginViaServerPost();
        loginPost.setUser_name(emailId);
        loginPost.setPassword(password);


        return retrofit.create(Api.class).post_loginDetails(loginPost);
    }


    public Observable<LoginResponse> postDetails_Server(String personName, String personId, String idToken) {

        Log.i("Token api",idToken);
        LoginPost loginPost = new LoginPost();
        loginPost.setPerson_name(personName);
        loginPost.setPersonId(personId);
        loginPost.setId_token(idToken);

        return retrofit.create(Api.class).post_userdetails(loginPost);
       // return getApi().post_userdetails(loginPost);

    }


}



