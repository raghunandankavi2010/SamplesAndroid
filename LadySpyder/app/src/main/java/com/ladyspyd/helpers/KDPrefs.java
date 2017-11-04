package com.ladyspyd.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ladyspyd.activities.LSSplashActivity;

public class KDPrefs {

    private static final String TAG = "APPrefs";
    private static final String PREFS = "SMP";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    Context _context;
    private static final String KEY_MOBILE_COUNTRY_CODE = "country_code";
    private SharedPreferences m_prefsRead;
    private SharedPreferences.Editor m_prefsWrite;
    private String usertype;
    private int userId;
    private String authToken;
    private String filePath;
    private String loginType;
    private boolean signUpRemove;
    private boolean sgneUpScreen;
    private String userType;
    private int status;
    private String launguage;
    private String address;
    private String stateInfor;
    private int pin;
    private int badgeCount;
    private String addressID;


    public KDPrefs(Context context) {
        _context = context;
        m_prefsRead = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        m_prefsWrite = m_prefsRead.edit();
    }

    public void setPassword(String name) {
        m_prefsWrite.putString("setPassword", name);
        m_prefsWrite.commit();
    }

    public String getName() {
        return m_prefsRead.getString("setName", null);
    }

    public void setName(String name) {
        m_prefsWrite.putString("setName", name);
        m_prefsWrite.commit();
    }

    public String getEmail() {
        return m_prefsRead.getString("setEmail", null);
    }

    public void setEmail(String name) {
        m_prefsWrite.putString("setEmail", name);
        m_prefsWrite.commit();
    }

    public String getPassword() {
        return m_prefsRead.getString("setPassword", null);
    }

    public void setMobileNumber(String mobileNumber) {
        m_prefsWrite.putString("setMobileNumber", mobileNumber);
        m_prefsWrite.commit();
    }

    public String getMobileNumber() {
        return m_prefsRead.getString("setMobileNumber", null);
    }

    public String getProfileImage() {
        return m_prefsRead.getString("setProfileImage", null);
    }

    public void setProfileImage(String setProfileImage) {

        m_prefsWrite.putString("setProfileImage", setProfileImage);
        m_prefsWrite.commit();

    }

    public void setUserType(String userType) {
        m_prefsWrite.putString("setUserType", userType);
        m_prefsWrite.commit();
    }

    public String getUserType() {
        return m_prefsRead.getString("setUserType", null);
    }

    public void setUserId(String userId) {
        m_prefsWrite.putString("setUserId", userId);
        m_prefsWrite.commit();
    }

    public String getUserId() {
        return m_prefsRead.getString("setUserId", null);
    }

    public void setFBUserId(String userId) {
        m_prefsWrite.putString("setFBUserId", userId);
        m_prefsWrite.commit();
    }

    public String getFBUserId() {
        return m_prefsRead.getString("setFBUserId", null);
    }

    public String setUserId() {
        return m_prefsRead.getString("setUserId", null);
    }

    public void createLoginSession() {
        // Storing login value as TRUE
        m_prefsWrite.putBoolean(IS_LOGIN, true);

        // commit changes
        m_prefsWrite.commit();
    }

    public boolean checkLogin() {
        // Check login status
        return this.isLoggedIn();

    }

    public boolean isLoggedIn() {
        return m_prefsRead.getBoolean(IS_LOGIN, false);
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        m_prefsWrite.clear();
        m_prefsWrite.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LSSplashActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    public void setStatus(int status) {
        m_prefsWrite.putInt("setStatus", status);
        m_prefsWrite.commit();
    }

    public int getStatus() {
        return m_prefsRead.getInt("setStatus", 0);
    }

    public void setlaunguage(String launguage) {
        m_prefsWrite.putString("setlaunguage", launguage);
        m_prefsWrite.commit();
    }


    public String getlaunguage() {
        return m_prefsRead.getString("setlaunguage", null);
    }

    public void setFlatNumber(String address) {
        m_prefsWrite.putString("setFlatNumber", address);
        m_prefsWrite.commit();
    }

    public void setStreetAddress(String stateInfor) {
        m_prefsWrite.putString("setStreetAddress", stateInfor);
        m_prefsWrite.commit();
    }

    public void setCity(String stateInfor) {
        m_prefsWrite.putString("setCity", stateInfor);
        m_prefsWrite.commit();
    }
    public void setState(String stateInfor) {
        m_prefsWrite.putString("setState", stateInfor);
        m_prefsWrite.commit();
    }

    public void setPostBoxNumber(String stateInfor) {
        m_prefsWrite.putString("setPostBoxNumber", stateInfor);
        m_prefsWrite.commit();
    }


    public String getCity() {
        return m_prefsRead.getString("setCity", null);
    }

    public String getPostBoxNumber() {
        return m_prefsRead.getString("setPostBoxNumber", null);
    }


    public String getState() {
        return m_prefsRead.getString("setState", null);
    }


    public void setPin(String setPin) {
        m_prefsWrite.putString("setPin", setPin);
        m_prefsWrite.commit();
    }

    public String getFlatNumber() {
        return m_prefsRead.getString("setFlatNumber", null);
    }

    public String getStateInfor() {
        return m_prefsRead.getString("setStreetAddress", null);
    }

    public String getPin() {
        return m_prefsRead.getString("setPin", null);
    }

    public void setBadgeCount(int badgeCount) {
        m_prefsWrite.putInt("setBadgeCount", badgeCount);
        m_prefsWrite.commit();
    }

    public int getBadgeCount() {
        return m_prefsRead.getInt("setBadgeCount", 0);
    }

    public void setFavCount(int badgeCount) {
        m_prefsWrite.putInt("setFavCount", badgeCount);
        m_prefsWrite.commit();
    }

    public int getFavCount() {
        return m_prefsRead.getInt("setFavCount", 0);
    }

    public void setTitle(String setAddressID) {
        m_prefsWrite.putString("getTitle", setAddressID);
        m_prefsWrite.commit();
    }

    public String getTitle() {
        return m_prefsRead.getString("getTitle", null);
    }

}
