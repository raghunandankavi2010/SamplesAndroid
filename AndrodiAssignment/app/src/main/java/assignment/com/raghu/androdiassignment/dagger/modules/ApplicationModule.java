package assignment.com.raghu.androdiassignment.dagger.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by raghu on 29/7/17.
 */

@Module
public class ApplicationModule {

    private Application app;
    private String PREF_NAME = "prefs";

    public ApplicationModule(Application app) {
        this.app = app;
    }

    @Singleton
    @Provides
    public Application provideApplication() {
        return app;
    }

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences() {
        return app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }


}
