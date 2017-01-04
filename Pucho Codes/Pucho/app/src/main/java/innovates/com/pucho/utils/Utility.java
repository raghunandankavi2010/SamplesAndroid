package innovates.com.pucho.utils;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    private static Pattern pattern;
    private static Matcher matcher;

    //Email Pattern
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /*
     * Validate the username with a regex email
     *
     * @param : email address entered in the login / Register screen
     * returns true if it is a valid email address else false     *
     */
    public static boolean validateRegex(String email){
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /*
    * Check whether the string is null
    * @param string input
    * @return true if not null else return false
    */
    public static boolean isNotNull(String txt){
        if(TextUtils.isEmpty(txt.trim().toString()))
        {
            return false;

        }else
        {
            return true;
        }
    }

    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
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

    public static String Datetime()
    {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String formattedDate = df.format(c.getTime());
        return formattedDate;

    }
}

