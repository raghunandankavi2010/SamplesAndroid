package com.peakaeriest.ladyspyder.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.peakaeriest.ladyspyder.R;
import com.peakaeriest.ladyspyder.helpers.LSUtilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LSBaseActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        actionBar.setTitle(" " + title);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(isBackEnabled);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(isDisableActionBar);
    }


    public void showAlert(Context context, String title, String message) {
        //TPAlertDialogManager tpAlertDialogManager=new TPAlertDialogManager();
        //tpAlertDialogManager.openQuantitiesDialog(context,title,message,false);
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

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
