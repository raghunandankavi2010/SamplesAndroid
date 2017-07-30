package assignment.com.raghu.androdiassignment.presenters;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import assignment.com.raghu.androdiassignment.listeners.OnForgotPasswordListener;
import assignment.com.raghu.androdiassignment.model.ForgotPasswordModel;

/**
 * Created by raghu on 30/7/17.
 */

public class ForgotPasswordPresenter extends AbstractPresenter<ForgotPasswordPresenterContract.View> implements ForgotPasswordPresenterContract.Presenter, OnForgotPasswordListener {

    private ForgotPasswordModel forgotPasswordModel;
    private ForgotPasswordPresenterContract.View view;

    @Inject
    public ForgotPasswordPresenter(ForgotPasswordModel forgotPasswordModel) {

        this.forgotPasswordModel = forgotPasswordModel;
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void resetPassword(@Nullable String phone) {

        view = getView();

        view.showProgress(true);

        forgotPasswordModel.resetPassword(phone,this);
    }


    @Override
    public void onPhoneError(String message) {

        view.showProgress(false);
        view.showSnackBar(message);
        view.phoneError(message);
    }

    @Override
    public void onPhoneNumberNotExist(String message) {

        view.showProgress(false);
        view.showSnackBar(message);
        view.phoneError(message);
    }

    @Override
    public void onPassword(String password) {

        view.showProgress(false);
        view.passwordReset(true,password);
    }
}
