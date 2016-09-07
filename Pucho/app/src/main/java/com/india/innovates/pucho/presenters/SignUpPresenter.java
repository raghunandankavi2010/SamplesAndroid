package com.india.innovates.pucho.presenters;

import com.india.innovates.pucho.SignUpActivity;
import com.india.innovates.pucho.api.SignUpApi;
import com.india.innovates.pucho.events.ErrorEvent;
import com.india.innovates.pucho.events.SuccessSignUpResponseEvent;
import com.india.innovates.pucho.listeners.OnLoginFinishedListener;
import com.india.innovates.pucho.models.SuccessSignUpResponse;
import com.india.innovates.pucho.screen_contracts.SignUpScreen;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Raghunandan on 23-12-2015.
 */

public class SignUpPresenter implements OnLoginFinishedListener {

    private SignUpApi signUpApi;
    private SignUpScreen signUpScreen;
    private String name,email,password;
    private Observable<List<SuccessSignUpResponse>> signUpResponseObservable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSignUpScreen(SignUpActivity signUpScreen) {
        this.signUpScreen =  signUpScreen;
    }


    @Inject
    public SignUpPresenter(SignUpApi signUpApi) {

        this.signUpApi = signUpApi;

    }

    public void valiDate_SignUp() {

        signUpApi.validate(getName(), getEmail(), getPassword(), this);
    }

    @Override
    public void onUsernameError() {

        EventBus.getDefault().post("User Name Error");
    }

    @Override
    public void onPasswordError() {
        EventBus.getDefault().post("Password Error");
    }

    @Override
    public void onEmailError() {
        EventBus.getDefault().post("Email Error");
    }

    @Override
    public void onSuccess() {
        EventBus.getDefault().post("Success");
    }

    public void sendDetailsToserver() {

        signUpResponseObservable = signUpApi
                                   .postSignUpDetails_server(getName(),getEmail(),getPassword());

        signUpResponseObservable
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread())
                .subscribe(new Subscriber<List<SuccessSignUpResponse>>() {
                    @Override
                    public void onNext(List<SuccessSignUpResponse> response) {

                     //signUpScreen.onNext(response);
                      EventBus.getDefault().post(new SuccessSignUpResponseEvent(response));
                    }

                    @Override
                    public void onCompleted() {
                     // signUpScreen.onCompleted();
                       // EventBus.getDefault().post("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                     // signUpScreen.onError(e);
                        EventBus.getDefault().post(new ErrorEvent(e));
                    }

                });

    }

    public void onDestroyActivity()
    {

        if(signUpResponseObservable!=null)
            signUpResponseObservable.unsubscribeOn(Schedulers.io());
        this.signUpScreen = null;
    }

    public void showProgressDialog() {

        signUpScreen.onShowProgressDialog();
    }


}
