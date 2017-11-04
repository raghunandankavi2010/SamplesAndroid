package com.ladyspyd.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.WindowManager;

import com.ladyspyd.R;


public class LSUtilities {

    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int PASSWORD_MAX_LENGTH = 12;
    private static String TAG = "LSUtilities";

    public static ProgressDialog createProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        try {
            dialog.show();
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.ma_progress_dialog);

        } catch (WindowManager.BadTokenException exception) {
            Log.d(TAG, "Caught Exception" + exception.getMessage());
        }
        return dialog;
    }

}