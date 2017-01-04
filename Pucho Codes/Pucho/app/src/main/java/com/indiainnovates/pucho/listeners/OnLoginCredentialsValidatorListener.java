package com.indiainnovates.pucho.listeners;

/**
 * Created by Raghunandan on 01-01-2016.
 */
public interface OnLoginCredentialsValidatorListener {

    void onLoginEmailError();

    void onLoginPasswordError();

    void onLoginSuccess();

}
