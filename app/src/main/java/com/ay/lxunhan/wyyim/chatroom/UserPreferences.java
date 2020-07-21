package com.ay.lxunhan.wyyim.chatroom;

import android.content.Context;
import android.content.SharedPreferences;

import com.ay.lxunhan.utils.UserInfo;
import com.netease.LSMediaCapture.util.NimUIKit;

public class UserPreferences {

    private final static String KEY_EARPHONE_MODE = "KEY_EARPHONE_MODE";

    public static void setEarPhoneModeEnable(boolean on) {
        saveBoolean(KEY_EARPHONE_MODE, on);
    }

    public static boolean isEarPhoneModeEnable() {
        return getBoolean(KEY_EARPHONE_MODE, true);
    }

    private static boolean getBoolean(String key, boolean value) {
        return getSharedPreferences().getBoolean(key, value);
    }

    private static void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    static SharedPreferences getSharedPreferences() {
        return NimUIKit.getContext().getSharedPreferences("UIKit." + UserInfo.getInstance().getWyyAccount(), Context.MODE_PRIVATE);
    }
}
