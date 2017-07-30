package assignment.com.raghu.androdiassignment.dagger.modules.components;


import android.content.SharedPreferences;

import javax.inject.Singleton;

import assignment.com.raghu.androdiassignment.ProfileActivity;
import assignment.com.raghu.androdiassignment.dagger.modules.ApplicationModule;
import dagger.Component;

/**
 * Created by Raghunandan on 14-12-2015.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(ProfileActivity profileActivity);

    SharedPreferences provideSharedPreferences();

}