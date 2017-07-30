package assignment.com.raghu.androdiassignment.dagger.modules.components;

import assignment.com.raghu.androdiassignment.ForgotPassword;
import assignment.com.raghu.androdiassignment.LoginActivity;
import assignment.com.raghu.androdiassignment.RegisterActivity;
import assignment.com.raghu.androdiassignment.dagger.modules.ActivityModule;
import assignment.com.raghu.androdiassignment.dagger.modules.PerActivity;
import dagger.Component;

/**
 * Created by raghu on 29/7/17.
 */

@Component(dependencies = {ApplicationComponent.class},
        modules = ActivityModule.class)
@PerActivity
public interface ActivityComponent {

    void inject(LoginActivity loginActivity);

    void inject(RegisterActivity registerActivity);

    void inject(ForgotPassword forgotPassword);


}
