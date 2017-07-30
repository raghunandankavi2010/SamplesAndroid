package assignment.com.raghu.androdiassignment.utils;

/**
 * Created by raghu on 29/7/17.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static final String dummynumber = "919986929644";
    public static final String dummypassword = "Dummy@123";
    private static Pattern pattern;
    private static Matcher matcher;

    //Email Pattern
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String NAME_PATTERN = "[a-zA-Z]+";

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
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

    private static final String MOBILENUMBER_PATTERN = "([0-9]{10})";

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


    public static boolean validatePhoneNumber(String phonenumber) {
        pattern = Pattern.compile(MOBILENUMBER_PATTERN);
        matcher = pattern.matcher(phonenumber);
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
}
