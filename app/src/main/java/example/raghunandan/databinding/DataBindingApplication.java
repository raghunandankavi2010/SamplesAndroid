package example.raghunandan.databinding;

import android.app.Application;
import android.os.StrictMode;



/**
 * Created by Raghunandan on 25-09-2016.
 */

public class DataBindingApplication extends Application {




    @Override
    public void onCreate() {

        if (BuildConfig.DEBUG) {
            // do something for a debug build
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectAll() //for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
        super.onCreate();


    }



}
