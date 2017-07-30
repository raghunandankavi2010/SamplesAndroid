package assignment.com.raghu.androdiassignment.presenters;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import assignment.com.raghu.androdiassignment.listeners.OnRegisterListener;
import assignment.com.raghu.androdiassignment.model.RegisterModel;

/**
 * Created by raghu on 29/7/17.
 */

public class RegisterPresenter extends AbstractPresenter<RegisterPresenterContract.View> implements RegisterPresenterContract.Presenter, OnRegisterListener {

    private RegisterPresenterContract.View regView;
    private RegisterModel registerModel;

    @Inject
    public RegisterPresenter( RegisterModel registerModel) {

        this.registerModel = registerModel;
    }
    @Override
    public void register(@Nullable String phone, @Nullable String email, @Nullable String name, @Nullable String password) {

        this.regView = getView();
        regView.showProgress(true);
        registerModel.validateCredentials(phone,email,name,password,this);
    }

    @Override
    public void onPhoneError(String message) {

        regView.showProgress(false);
        regView.showSnackBar(message);
        regView.phoneError();
    }

    @Override
    public void onPasswordError(String message) {

        regView.showProgress(false);
        regView.showSnackBar(message);
        regView.passwordError();
    }

    @Override
    public void onNamedError(String message) {

        regView.showProgress(false);
        regView.showSnackBar(message);
        regView.namerror();
    }

    @Override
    public void onEmailError(String message) {

        regView.showProgress(false);
        regView.emailError();
    }

    @Override
    public void onRegisteruccess() {

        regView.showProgress(false);
        regView.showSnackBar("Success!");
        regView.registerSuccess();
    }

    @Override
    public void onUserAlreadyExists() {

        regView.showProgress(false);
        regView.showSnackBar("User already Exists!.Login.");
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
