/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.indiainnovates.pucho;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.indiainnovates.pucho.api.Api;
import com.indiainnovates.pucho.dagger.modules.SharedPreferencesGet;
import com.indiainnovates.pucho.models.PostGcmToken;
import com.indiainnovates.pucho.models.SendTokenResponse;

import java.io.IOException;

import javax.inject.Inject;

import retrofit.Retrofit;
import rx.Observable;
import rx.Subscriber;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};

    @Inject
    Retrofit retrofit;

    @Inject
    SharedPreferencesGet sharedPreferencesget;

    public RegistrationIntentService() {
        super(TAG);

    }


    @Override
    public void onCreate() {
        super.onCreate();
        //((PuchoApplication) getApplication()).component().inject(this);
        ((PuchoApplication) getApplication()).netcomponent().inject(this);

    }


    @Override
    protected void onHandleIntent(Intent intent) {
       // SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            // [START register_for_gcm]
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
            // See https://developers.google.com/cloud-messaging/android/start for details on this file.
            // [START get_token]
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // [END get_token]
            Log.i(TAG, "GCM Registration Token: " + token);

            // TODO: Implement this method to send any registration to your app's servers.
            sendRegistrationToServer(token);

            // Subscribe to topic channels
            subscribeTopics(token);

            // You should store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.
            //sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();
            // [END register_for_gcm]
        } catch (Exception e) {
            Log.d(TAG, "", e);
            // If aFailed to complete token refreshn exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferencesget.getSharedPreferences().edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    /**
     * Persist registration to third-party servers.
     * <p/>
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        // need to write code for sending token to our server

        int id = sharedPreferencesget.getSharedPreferences().getInt("user_id", -1);
        boolean bool = sharedPreferencesget.getSharedPreferences().getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER,false);
        if (id != -1 && bool== false) {
           Observable<SendTokenResponse> responseObservable = postGCMToken(token, id);

            responseObservable.subscribe(new Subscriber<SendTokenResponse>() {
                @Override
                public void onNext(SendTokenResponse response) {

                    if (response.isSuccess()) {
                        sharedPreferencesget.getSharedPreferences().edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();
                    } else {
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

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
    // [END subscribe_topics]

    public Observable<SendTokenResponse> postGCMToken(String token, int userid) {

        PostGcmToken postGcmToken = new PostGcmToken();
        postGcmToken.setRegistration_id(token);
        postGcmToken.setUser_id(userid);

        return retrofit.create(Api.class).post_GCMToken(postGcmToken);

    }
}
