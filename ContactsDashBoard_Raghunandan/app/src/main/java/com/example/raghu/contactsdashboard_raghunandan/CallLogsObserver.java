package com.example.raghu.contactsdashboard_raghunandan;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

/**
 * Created by raghu on 13/5/17.
 */

public class CallLogsObserver extends ContentObserver {


    public interface CallLogChangeCallback
    {
         void calllogContentChanged(boolean selfChange,Uri uri);
    }

    private CallLogChangeCallback callLogChangeCallback;

    public CallLogsObserver(Handler handler,CallLogChangeCallback callLogChangeCallback ) {
        super(handler);

        this.callLogChangeCallback = callLogChangeCallback;
    }

    @Override
    public void onChange(boolean selfChange) {
        this.onChange(selfChange,null);

        Log.d("Change",""+selfChange);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {

        Log.d("Change",""+selfChange);
        if(callLogChangeCallback!=null)
        {
            callLogChangeCallback.calllogContentChanged(selfChange,uri);
        }
    }

}
