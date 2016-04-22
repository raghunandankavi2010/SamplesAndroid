package com.indiainnovates.pucho.presenters;

import android.content.Context;
import android.util.Log;

import com.indiainnovates.pucho.R;
import com.indiainnovates.pucho.api.LoginApi;
import com.indiainnovates.pucho.events.ErrorEvent;
import com.indiainnovates.pucho.events.LoginErrorEvent;
import com.indiainnovates.pucho.events.LoginResponseEvent;
import com.indiainnovates.pucho.events.LoginViaServerResponseEvent;
import com.indiainnovates.pucho.listeners.OnLoginCredentialsValidatorListener;
import com.indiainnovates.pucho.models.LoginResponse;
import com.indiainnovates.pucho.models.SignupResponse;
import com.indiainnovates.pucho.screen_contracts.LoginScreen;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStream;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Raghunandan on 16-12-2015.
 */
public class LoginPresenter implements OnLoginCredentialsValidatorListener{

    private LoginScreen loginScreen;

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private LoginApi loginApi;
    private Observable<LoginResponse> loginResponseObservable;

    private Observable<SignupResponse> loginviaserverObservable;

    private String personName,personId,idToken,emailId,password;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }


    @Inject
    public LoginPresenter(LoginApi loginApi) {
        this.loginApi = loginApi;


    }

    public void OnShowProgressDialog() {
        loginScreen.showProgressDialog();
    }

    public void OnDismissProgressDialog() {
        loginScreen.dismissProgressDialog();
    }



    public void OnGooglePlusSignButtonClicked() {
        loginScreen.googlesignin();
    }

    public void CheckPermission_Email() {
        loginScreen.checkPermissionForEmail();
    }

    public void OnShowRationale() {
        loginScreen.showRationale();
    }

    public void sendDetailsToserver() {

        Log.i(TAG,"Person Name"+getPersonName());
        Log.i(TAG,"Person id"+getPersonId());;
        Log.i(TAG,getIdToken());
        loginResponseObservable =  loginApi.postDetails_Server(getPersonName(),getPersonId(),getIdToken());

        loginResponseObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread())
                .subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onNext(LoginResponse response) {

                        EventBus.getDefault().post(new LoginResponseEvent(response));

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                            EventBus.getDefault().post(new LoginErrorEvent(e));
                    }

                });
    }


    public void setLoginScreen(LoginScreen loginScreen) {

        this.loginScreen = loginScreen;

    }

    public void onDestroyActivity()
    {
        //this.loginScreen = null;
        if(loginResponseObservable!=null)
            loginResponseObservable.unsubscribeOn(Schedulers.io());
        if(loginviaserverObservable!=null)
            loginviaserverObservable.unsubscribeOn(Schedulers.io());
    }

    public void launcQuestionFeedActivity() {
        loginScreen.launchActivity();
    }

    public void OnSignUpClicked() {
        loginScreen.signupClicked();
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }

    public void validateLoginCredentials() {
        loginApi.validateLoginCredentials(getEmailId(),getPassword(),this);
    }

    @Override
    public void onLoginEmailError() {
        loginScreen.showEmailError();
    }

    @Override
    public void onLoginPasswordError() {
        loginScreen.showPasswordError();

    }

    @Override
    public void onLoginSuccess() {
        loginScreen.onSuccess();
    }

    public void postLoginDetails() {

       loginviaserverObservable =
               loginApi.postLoginDetails_Server(getEmailId(),getPassword());
        loginviaserverObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread())
                .subscribe(new Subscriber<SignupResponse>() {
                    @Override
                    public void onNext(SignupResponse response) {

                        EventBus.getDefault().post(new LoginViaServerResponseEvent(response));

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new LoginErrorEvent(e));
                    }

                });
    }


}
