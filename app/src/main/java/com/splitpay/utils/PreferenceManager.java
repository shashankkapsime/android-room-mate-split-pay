package com.splitpay.utils;

import android.content.Context;
import android.content.SharedPreferences;

/*
This class is used to store the certain values in the shared preferences.
 */
public class PreferenceManager {

    /*
    This method is used to store string values in shared preferences.
     */
    public static void saveStringPreferences(Context context, String key, String value) {
        SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    /*
       This method is used to store integer values in shared preferences.
        */
    public static void saveIntPreferences(Context context, String key, int value) {
        SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    /*
       This method is used to store boolean values in shared preferences.
        */
    public static void saveBooleanPreferences(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /*
   This method is used to get string values from shared preferences.
    */
    public static String getStringPreferences(Context context, String key) {
        SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");
    }
    /*
      This method is used to get integer values from shared preferences.
       */
    public static int getIntPreferences(Context context, String key) {
        SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, 0);
    }
    /*
      This method is used to get boolean values from shared preferences.
       */
    public static boolean getBooleanPreferences(Context context, String key) {
        SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, false);
    }
}