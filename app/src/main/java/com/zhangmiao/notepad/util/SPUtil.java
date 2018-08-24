package com.zhangmiao.notepad.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author: zhangmiao
 * Date: 2018/8/24
 */
public class SPUtil {

    private static final String NAME = "Record";
    public static final String MOOD_PASSWORD = "MoodPassword";

    private static SharedPreferences sSharedPreferences;

    public static void saveString(Context context, String key, String value) {
        if (sSharedPreferences == null) {
            sSharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key, String defaultValue) {
        if (sSharedPreferences == null) {
            sSharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        return sSharedPreferences.getString(key, defaultValue);
    }

}
