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

/**
 * The service which allows the sync adapter framework to access the authenticator.
 */
public class MyAuthenticatorService extends Service {
    // Instance field that stores the authenticator object
    private MyAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new MyAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
