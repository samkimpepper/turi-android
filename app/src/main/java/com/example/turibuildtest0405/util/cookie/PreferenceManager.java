//package com.example.turibuildtest0405.util;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import java.util.HashSet;
//
//public class PreferenceManager {
//    public static final String COOKIE_KEY = "cookie_key";
//
//    public HashSet<String> getCookies(Context context) {
//        return getStringSetPreference(context, COOKIE_KEY);
//    }
//
//    public void setCookies(Context context, HashSet<String>  cookies) {
//        putStringSetPreference(context, COOKIE_KEY, cookies);
//    }
//
//    protected void putStringPreference(Context context, String prefsName, String key, String value) {
//
//        SharedPreferences preferences = context.getSharedPreferences(prefsName, Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//
//        editor.putString(key, value);
//        editor.commit();
//    }
//
//    protected String getStringPreference(Context context, String prefsName,
//                                         String key) {
//
//        SharedPreferences preferences = context.getSharedPreferences(
//                prefsName, Activity.MODE_PRIVATE);
//        String value = preferences.getString(key, "");
//        return value;
//    }
//
////    private static SharedPreferences getPreferences(Context context) {
////        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
////
////    }
//
////    public static void setString(Context context, String key, String value) {
////
////        SharedPreferences prefs = getPreferences(context);
////
////        SharedPreferences.Editor editor = prefs.edit();
////
////        editor.putString(key, value);
////
////        editor.commit();
////
////    }
////
////    public static String getString(Context context, String key) {
////
////        SharedPreferences prefs = getPreferences(context);
////
////        String value = prefs.getString(key, "");
////
////        return value;
////
////    }
//}
