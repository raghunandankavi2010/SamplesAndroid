package com.example.raghu.contactsdashboard_raghunandan;

import android.text.TextUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by raghu on 10/5/17.
 */

public class Utils {

    public static boolean checkNotEmpty(String check)
    {
        if(!TextUtils.isEmpty(check))
            return true;
        return false;
    }


    public static List<Contacts> sort(List<Contacts> list) {
        Collections.sort(list, new Comparator<Contacts>() {
            @Override
            public int compare(Contacts lhs, Contacts rhs) {
                return rhs.getDuration() - lhs.getDuration();
            }
        });
        return list;
    }

    public static String timeConversion(int seconds) {

        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        int minutes = seconds / SECONDS_IN_A_MINUTE;
        seconds -= minutes * SECONDS_IN_A_MINUTE;

        int hours = minutes / MINUTES_IN_AN_HOUR;
        minutes -= hours * MINUTES_IN_AN_HOUR;

        return hours + " hrs " + minutes + " min " + seconds + " sec";
    }
}
