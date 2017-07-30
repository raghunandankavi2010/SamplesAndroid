package assignment.com.raghu.androdiassignment.presenters;

import android.support.annotation.Nullable;

/**
 * Created by raghu on 29/7/17.
 */

public interface LoginPresenterContract {


    interface View {

        void showProgress(boolean active);

        void showSnackBar(String message);

        void passwordError();

        void phoneError();

        void loginSuccess();

        void loginFailed();

    }

    interface Presenter extends BasePresenter {

        void validate(@Nullable String phone, @Nullable String email);
    }
}

