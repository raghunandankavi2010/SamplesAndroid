package assignment.com.raghu.androdiassignment.presenters;

import android.support.annotation.Nullable;

/**
 * Created by raghu on 29/7/17.
 */

public interface RegisterPresenterContract {


    interface View {

        void showProgress(boolean active);

        void showSnackBar(String message);

        void passwordError();

        void phoneError();

        void emailError();

        void namerror();

        void registerSuccess();

        void registerFailed();

    }

    interface Presenter extends BasePresenter{

        void register(@Nullable String phone, @Nullable String email, @Nullable String name,@Nullable String password);
    }
}