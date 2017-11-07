package com.ladyspyd.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.ladyspyd.helpers.LSUtilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by raghu on 7/11/17.
 */

public class Utilities {

    private ProgressDialog mProgressDialog;

    public static boolean isvalidemail(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }



}
