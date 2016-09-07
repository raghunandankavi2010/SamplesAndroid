package com.india.innovates.pucho.fcm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.india.innovates.pucho.PuchoApplication;
import com.india.innovates.pucho.R;
import com.india.innovates.pucho.api.Api;
import com.india.innovates.pucho.dagger.modules.SharedPreferencesGet;
import com.india.innovates.pucho.models.PostGcmToken;
import com.india.innovates.pucho.models.SendTokenResponse;
import com.india.innovates.pucho.utils.QuickstartPreferences;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Raghunandan on 13-08-2016.
 */

public class SendTokenToServer extends IntentService {


    private static final String TAG = "SendTokenToServer" ;

    public SendTokenToServer() {
        super("SendTokenIntentService");
    }

    @Inject
    SharedPreferencesGet sharedPreferencesget;


    @Inject
    Retrofit retrofit;


    @Override
    public void onCreate() {
        super.onCreate();
        //((PuchoApplication) getApplication()).component().inject(this);
        ((PuchoApplication) getApplication()).netcomponent().inject(this);

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
        String msg = getString(R.string.msg_token_fmt, token);
        Log.d(TAG, msg);

        int id = sharedPreferencesget.getSharedPreferences().getInt("user_id", -1);
        boolean bool = sharedPreferencesget.getSharedPreferences().getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
        Log.d(TAG,"Sent : "+bool);
        if (id != -1 && bool == false) {
            Observable<SendTokenResponse> responseObservable = postGCMToken(token, id);

            responseObservable.subscribe(new Subscriber<SendTokenResponse>() {
                @Override
                public void onNext(SendTokenResponse response) {

                    if (response.isSuccess()) {
                        Log.d(TAG,"SUCCESSS");
                        sharedPreferencesget.getSharedPreferences().edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();
                    } else {
                        Log.d(TAG,"FAILED");
                        sharedPreferencesget.getSharedPreferences().edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
                    }

                }

                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    sharedPreferencesget.getSharedPreferences().edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
                }

            });
        }
    }

    public Observable<SendTokenResponse> postGCMToken(String token, int userid) {

        PostGcmToken postGcmToken = new PostGcmToken();
        postGcmToken.setRegistration_id(token);
        postGcmToken.setUser_id(userid);

        return retrofit.create(Api.class).post_GCMToken(postGcmToken);

    }
}
