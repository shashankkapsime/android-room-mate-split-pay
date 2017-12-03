package com.splitpay.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by shashank on 29-01-2017.
 */

public class SplitPaySession {

    private Context mContext;
    private SharedPreferences sharedPreferences;
    private final static String USER_ID = "split_pay_user_id";
    private final static String USER_PHONE_NUMBER = "user_phone_number";
    private final static String LOGGED_IN_STATUS = "logged_in_status";
    private static SplitPaySession mInstance;

    public static SplitPaySession getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new SplitPaySession(ctx);
        }
        return mInstance;
    }

    private SplitPaySession(Context mContext) {
        this.mContext = mContext;
        sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void setUserId(String userIDValue) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, userIDValue);
        editor.apply();
    }

    public String getUserID() {
        return sharedPreferences.getString(USER_ID, "");
    }

    public void setUserPhoneNumber(String phoneNumber) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_PHONE_NUMBER, phoneNumber);
        editor.apply();
    }

    public String getUserPhoneNumber() {
        return sharedPreferences.getString(USER_PHONE_NUMBER, "");
    }

    public void setUserLoggedIn(boolean isLoggedIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LOGGED_IN_STATUS, isLoggedIn);
        editor.apply();
    }

    public boolean getUserLoggedInStatus() {
        return sharedPreferences.getBoolean(LOGGED_IN_STATUS, false);
    }

    public void clearSession() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
