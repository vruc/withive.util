package com.withive.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.orhanobut.logger.Logger;


/**
 * Created by Oscar.Chen on 9/10/2016.
 */
public class PreferencesUtil {

    private static final String PREF_NAME = "CoreData";

    private static PreferencesUtil sInstance;
    private final SharedPreferences mPref;

    private PreferencesUtil(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        Logger.d("initialize PreferencesUtil Instance via [%s]", context.getClass().getSimpleName());
        if (sInstance == null) {
            sInstance = new PreferencesUtil(context);
        }
    }
//
//    public static synchronized PreferencesUtil getInstance() {
//        if (sInstance == null) {
//            throw new IllegalStateException(PreferencesUtil.class.getSimpleName() +
//                    " is not initialized, call initializeInstance(..) method first.");
//        }
//        return sInstance;
//    }

    public static PreferencesUtil setString(String key, String value) {
        sInstance.mPref.edit().putString(key, value).commit();
        Logger.d("setString %s:%s", key, value);
        return sInstance;
    }

    public static PreferencesUtil setInt(String key, int value) {
        sInstance.mPref.edit().putInt(key, value).commit();
        Logger.d("setInt %s:%s", key, value);
        return sInstance;
    }

    public static PreferencesUtil setBoolean(String key, Boolean value) {
        sInstance.mPref.edit().putBoolean(key, value).commit();
        Logger.d("setBoolean %s:%s", key, value);
        return sInstance;
    }

    public static PreferencesUtil setLong(String key, Long value) {
        sInstance.mPref.edit().putLong(key, value).commit();
        Logger.d("setLong %s:%s", key, value);
        return sInstance;
    }

    public static String getString(String key) {
        return sInstance.mPref.getString(key, "");
    }

    public static int getInt(String key) {
        return sInstance.mPref.getInt(key, 0);
    }

    public static long getLong(String key) {
        return sInstance.mPref.getLong(key, 0);
    }

    public static boolean getBoolean(String key) {
        return sInstance.mPref.getBoolean(key, false);
    }

    public static void remove(String key) {
        sInstance.mPref.edit().remove(key).commit();
    }

    public static boolean contains(String key) {
        return sInstance.mPref.contains(key);
    }

    public static boolean clear() {
        return sInstance.mPref.edit().clear().commit();
    }
}