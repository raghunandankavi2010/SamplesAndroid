package com.ladyspyd.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.ladyspyd.R;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import com.ladyspyd.helpers.LSUtilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LSBaseActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       if (getResources().getBoolean(R.bool.isTablet)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onCreate(savedInstanceState);
        //TPLog.v(TAG, "onCreate()");
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //TPLog.v(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //TPLog.v(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TPLog.v(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //TPLog.v(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //TPLog.v(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TPLog.v(TAG, "onDestroy()");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //TPLog.v(TAG, "onNewIntent()");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //TPLog.v(TAG, "onConfigurationChanged()");
    }

    /**
     * Method to hide the key board
     *
     * @param view The view
     */
    public void dismissKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public boolean isvalidemail(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Method to show the Progress dialog
     */
    public void showProgressDialog(Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = LSUtilities.createProgressDialog(context);
            mProgressDialog.show();
        } else {
            mProgressDialog.show();
        }
    }

    /**
     * Method to hide the progress dialog
     */
    public void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public void updateFragmentTitle(String title, boolean isBackEnabled, boolean isDisableActionBar) {
        ActionBar actionBar = getSupportActionBar();
        try {
            actionBar.setTitle(" " + title);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(isBackEnabled);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowHomeEnabled(isDisableActionBar);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void showAlert(Context context, String title, String message) {
        //TPAlertDialogManager tpAlertDialogManager=new TPAlertDialogManager();
        //tpAlertDialogManager.openQuantitiesDialog(context,title,message,false);
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

    }

    public boolean getIsTablet(Context context) {
        boolean isTablet = false;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        if (diagonalInches >= 6.5) {
            // 6.5inch device or bigger
            isTablet = true;
        } else {
            // smaller device
            isTablet = false;
        }
        return isTablet;
    }


    public MixpanelAPI getMixPanel(Context context) {
        MixpanelAPI mixpanel = null;
        try {
            String projectToken = "5aa99d5f40f7c5121e005174e4b3b8c1"; // e.g.: "1ef7e30d2a58d27f4b90c42e31d6d7ad"
            mixpanel = MixpanelAPI.getInstance(context, projectToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mixpanel;

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            //   Drawable background = activity.getResources().getDrawable(R.drawable.toolbar_gradient);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(R.color.transparent));
            //  window.setBackgroundDrawable(background);
        }
    }
}
