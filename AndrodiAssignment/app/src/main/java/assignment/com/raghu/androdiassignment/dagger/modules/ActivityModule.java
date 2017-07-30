package assignment.com.raghu.androdiassignment.dagger.modules;

import android.content.SharedPreferences;

import assignment.com.raghu.androdiassignment.model.ForgotPasswordModel;
import assignment.com.raghu.androdiassignment.model.LoginModel;
import assignment.com.raghu.androdiassignment.model.RegisterModel;
import assignment.com.raghu.androdiassignment.presenters.ForgotPasswordPresenter;
import assignment.com.raghu.androdiassignment.presenters.ForgotPasswordPresenterContract;
import assignment.com.raghu.androdiassignment.presenters.LoginPresenter;
import assignment.com.raghu.androdiassignment.presenters.LoginPresenterContract;
import assignment.com.raghu.androdiassignment.presenters.RegisterPresenter;
import assignment.com.raghu.androdiassignment.presenters.RegisterPresenterContract;
import dagger.Module;
import dagger.Provides;

/**
 * Created by raghu on 29/7/17.
 */

@Module
@PerActivity
public class ActivityModule {

    @PerActivity
    @Provides
    public LoginPresenterContract.Presenter provideLoginPresenter(LoginPresenter presenter) {
        return presenter;
    }


    @PerActivity
    @Provides
    public RegisterPresenterContract.Presenter provideRegisterPresenter(RegisterPresenter presenter) {
        return presenter;
    }

    @PerActivity
    @Provides
    public ForgotPasswordPresenterContract.Presenter provideForgotPasswordPresenter(ForgotPasswordPresenter presenter) {
        return presenter;
    }

    @PerActivity
    @Provides
    public RegisterModel provideRegisterModel() {
        return new RegisterModel();
    }

    @PerActivity
    @Provides
    public LoginModel provideLoginModel() {
        return new LoginModel();
    }

    @PerActivity
    @Provides
    public ForgotPasswordModel provideForgotPasswordModel() {
        return new ForgotPasswordModel();
    }

    @Provides
    @UserScope
    SharedPreferencesGet provideSharedPreferences(SharedPreferences sharedPreferences)
    {
        return new SharedPreferencesGet(sharedPreferences);
    }
}