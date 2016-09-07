package com.india.innovates.pucho.listeners;

/**
 * Created by Raghunandan on 24-12-2015.
 */
public interface OnLoginFinishedListener {


    public void onUsernameError();

    public void onPasswordError();

    public void onEmailError();

    public void onSuccess();

}
