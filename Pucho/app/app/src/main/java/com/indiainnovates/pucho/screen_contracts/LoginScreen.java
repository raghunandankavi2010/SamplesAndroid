package com.indiainnovates.pucho.screen_contracts;

import com.indiainnovates.pucho.models.LoginResponse;

/**
 * Created by Raghunandan on 16-12-2015.
 */


public interface LoginScreen {

    void launchActivity();
    void showProgressDialog();
    void dismissProgressDialog();
    void googlesignin();
    void showRationale();
    void checkPermissionForEmail();
    void signupClicked();

    void showEmailError();
    void showPasswordError();
    void onSuccess();


}


