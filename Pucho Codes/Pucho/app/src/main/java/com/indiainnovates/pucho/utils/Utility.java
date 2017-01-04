package com.indiainnovates.pucho.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    private static Pattern pattern;
    private static Matcher matcher;

    // question characters 10 limit
    //Email Pattern
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String NAME_PATTERN = "[a-zA-z]+([ '-][a-zA-Z]+)";

    /*
    (			# Start of group
        (?=.*\d)		#   must contains one digit from 0-9
        (?=.*[a-z])		#   must contains one lowercase characters
        (?=.*[A-Z])		#   must contains one uppercase characters
        (?=.*[@#$%])		#   must contains one special symbols in the list "@#$%"
              .		#     match anything with previous condition checking
                {6,20}	#        length at least 6 characters and maximum of 20
    )			# End of group
     */
    private static final String PASSWORD_PATTERN = "[A-Za-z0-9]+";////"((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

    private static final String MOBILENUMBER_PATTERN = "^(91)([0-9]{10})";

    /*
     * Validate the username with a regex email
     *
     * @param : email address entered in the login / Register screen
     * returns true if it is a valid email address else false     *
     */
    public static boolean validateEmailRegex(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validateNameRegex(String name) {
        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(name);
        return matcher.matches();
    }


    public static boolean validatePasswordRegex(String name) {
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(name);
        return matcher.matches();
    }


    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static String Datetime() {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String formattedDate = df.format(c.getTime());
        return formattedDate;

    }

    public StringBuffer computeMD5Hash(String password) {
        StringBuffer MD5Hash = null;
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            MD5Hash = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                MD5Hash.append(h);
            }


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return MD5Hash;
    }

    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static String calcualte_timeDifference(String datetobeFormatted) {

        int days = 0,hours = 0,minutes = 0,seconds = 0;
        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df_current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = df_current.format(c.getTime());
            Date  date_current = df_current.parse(formattedDate);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = df.parse(datetobeFormatted);


            DateTime dt1 = new DateTime(date);
            DateTime dt2 = new DateTime(date_current);

            days = Days.daysBetween(dt1, dt2).getDays();
            hours = Hours.hoursBetween(dt1, dt2).getHours() % 24;
            minutes = Minutes.minutesBetween(dt1, dt2).getMinutes() % 60;
            seconds = Seconds.secondsBetween(dt1, dt2).getSeconds() % 60;

            Log.i("Date ",datetobeFormatted);
            Log.i("Days ",(Days.daysBetween(dt1, dt2).getDays() + " days, "));
            //Log.i("Days ",Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, ");
            //Log.i("Days ",Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes, ");



        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days+"d"+" "+hours+"h"+" "+minutes+"m"+" "+seconds+"s";
    }
}

