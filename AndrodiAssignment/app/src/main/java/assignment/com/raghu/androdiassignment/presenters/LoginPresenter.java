package assignment.com.raghu.androdiassignment.presenters;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import assignment.com.raghu.androdiassignment.listeners.OnLoginValidateListener;
import assignment.com.raghu.androdiassignment.model.LoginModel;

/**
 * Created by raghu on 29/7/17.
 */

public class LoginPresenter extends AbstractPresenter<LoginPresenterContract.View> implements LoginPresenterContract.Presenter, OnLoginValidateListener {

    private LoginPresenterContract.View loginView;
    private LoginModel loginModel;

    @Inject
    public LoginPresenter( LoginModel loginModel) {


        this.loginModel = loginModel;
    }

    @Override
    public void validate(@Nullable String phone, @Nullable String password) {

        this.loginView = getView();
        loginView.showProgress(true);
        loginModel.validateCredentials(phone,password,this);

    }

    @Override
    public void onLoginPhoneError(String message) {
        loginView.showProgress(false);
        loginView.phoneError();
        loginView.showSnackBar(message);
    }

    @Override
    public void onLoginPasswordError(String message) {
        loginView.showProgress(false);
        loginView.passwordError();
        loginView.showSnackBar(message);

    }

    @Override
    public void register_newUser(String message) {
        loginView.showProgress(false);
        loginView.showSnackBar(message);
    }

    @Override
    public void onLoginSuccess() {
        loginView.showProgress(false);
        loginView.loginSuccess();
    }

    @Override
    public void onLoginFailed() {
        loginView.showProgress(false);
        loginView.loginFailed();
        loginView.showSnackBar("Login Failed");
    }


    @Override
    public void subscribe() {

        // ignore for now
    }

    @Override
    public void unsubscribe() {

        // ignore for now
        // clean up later
    }
}
