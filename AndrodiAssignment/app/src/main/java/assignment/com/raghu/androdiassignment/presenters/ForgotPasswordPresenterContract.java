package assignment.com.raghu.androdiassignment.presenters;

import android.support.annotation.Nullable;

/**
 * Created by raghu on 30/7/17.
 */

public class ForgotPasswordPresenterContract {


    public interface View {

        void showProgress(boolean active);

        void showSnackBar(String message);

        void phoneError(String message);

        void passwordReset(boolean check,String password);


    }

    public interface Presenter extends BasePresenter {

        void resetPassword(@Nullable String phone);
    }
}
