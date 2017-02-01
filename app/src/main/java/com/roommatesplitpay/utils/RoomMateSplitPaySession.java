package com.roommatesplitpay.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by shashank on 29-01-2017.
 */

public class RoomMateSplitPaySession {

    private Context mContext;
    private SharedPreferences sharedPreferences;
    private String userID = "userID";

    public RoomMateSplitPaySession(Context mContext) {
        this.mContext = mContext;
        sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void setRoomMateSession(String userIDValue) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(userID, userIDValue);
        editor.apply();
    }

    public String getUserID() {
        return sharedPreferences.getString(userID, "");
    }
}
