package com.example.user.nudg;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by user on 18/08/2016.
 */
public class SharedPrefRunner {

    public static void setStoredText(String key,Context context, String text){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,text);
        editor.apply();
    }

    public static String getStoredText(String key, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String text = sharedPreferences.getString(key, null);
        return text;
    }

    public static void clear(Context context,String key){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().remove(key).commit();
    }
}
