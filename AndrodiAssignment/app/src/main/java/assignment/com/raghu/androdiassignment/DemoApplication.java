package assignment.com.raghu.androdiassignment;

import android.app.Application;
import android.util.Log;

import assignment.com.raghu.androdiassignment.dagger.modules.ApplicationModule;
import assignment.com.raghu.androdiassignment.dagger.modules.components.ApplicationComponent;
import assignment.com.raghu.androdiassignment.dagger.modules.components.DaggerApplicationComponent;
import io.realm.Realm;
import timber.log.Timber;

/**
 * Created by raghu on 28/7/17.
 */


public class DemoApplication extends Application {

    private static ApplicationComponent applicationComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
       if (applicationComponent == null) {

            applicationComponent = DaggerApplicationComponent.builder()
                    // list of modules that are part of this component need to be created here too
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree(){
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + ':' + element.getLineNumber();
                }
            });
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    /** A tree which logs important information for crash reporting. */
    private static class CrashReportingTree extends Timber.Tree {
        @Override protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
                return;
            }

            //FakeCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    //FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    //FakeCrashLibrary.logWarning(t);
                }
            }
        }
    }


    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}