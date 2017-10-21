package com.peakaeriest.ladyspyder.helpers;

import android.app.Application;
import android.content.res.Configuration;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

import java.util.Locale;

public class LSApp extends Application {
    private static LSApp m_instance;

    public static String sDefSystemLanguage;
    public static String sDefSystemLanguageCode;
    private static LSApp mInstance;

    private KDPrefs prefs;
    private Toast m_toast;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        FirebaseApp.initializeApp(this);
        prefs = new KDPrefs(this);
        m_instance = this;

        sDefSystemLanguage = Locale.getDefault().getLanguage();
        switch (sDefSystemLanguage) {
            case "en":
                sDefSystemLanguageCode = "1";
                break;
            case "ar":
                sDefSystemLanguageCode = "2";
                break;
            default:
                sDefSystemLanguageCode = "3";
                break;
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        sDefSystemLanguage = newConfig.locale.getLanguage();
        switch (sDefSystemLanguage) {
            case "en":
                sDefSystemLanguageCode = "1";
                break;
            case "ar":
                sDefSystemLanguageCode = "2";
                break;
            default:
                sDefSystemLanguageCode = "3";
                break;
        }
    }

    /**
     * App Instance
     *
     * @return
     */
    public static LSApp getInstance() {
        return mInstance;
    }

    /**
     * Preference Object
     *
     * @return
     */

    public KDPrefs getPrefs() {
        return prefs;
    }


    /**
     * Summary
     *
     * @param message
     */
    public void showToast(String message, int duration) {
        if (m_toast == null) {
            m_toast = Toast.makeText(this, message, duration);
        } else {
            m_toast.setText(message);
            m_toast.setDuration(duration);
        }

        m_toast.show();
    }
}
