package assignment.test.raghu.peppersqaure.sync;

/**
 * Created by Raghunandan on 22-11-2015.
 */
/**
 * Created by Raghunandan on 20-10-2015.
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;



public class MySyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static MySyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate() {

        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new MySyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}