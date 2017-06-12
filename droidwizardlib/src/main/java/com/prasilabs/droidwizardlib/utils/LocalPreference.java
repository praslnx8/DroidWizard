package com.prasilabs.droidwizardlib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.prasilabs.droidwizardlib.debug.ConsoleLog;

import java.util.ArrayList;

/**
 * Bouiler plate code for sharing app preference data
 * @author Prasanna Anbazhagan <praslnx8@gmail.com>
 * @version 1.0
 */
public class LocalPreference
{
    private static final String TAG = LocalPreference.class.getSimpleName();

    private static final String SESSION_DATA = "session_data";
    private static final String PERSISTENT_DATA = "persistent_data";

    public static void saveUserDataInShared(Context context, String field, String values)
    {
        try
        {
            SharedPreferences sp = context.getSharedPreferences(SESSION_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(field, values);
            edit.apply();
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }
    }

    public static String getUserDataFromShared(Context context, String field, String defaultValue)
    {
        try
        {
            SharedPreferences sp = context.getSharedPreferences(SESSION_DATA, Context.MODE_PRIVATE);
            return sp.getString(field, defaultValue);
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
            return defaultValue;
        }
    }

    public static void saveUserDataInShared(Context context, String field, boolean values)
    {
        saveUserDataInShared(context, field, String.valueOf(values));
    }

    public static void saveUserDataInShared(Context context, String field, double values)
    {
        saveUserDataInShared(context, field, String.valueOf(values));
    }

    public static void saveUserDataInShared(Context context, String field, int values)
    {
        saveUserDataInShared(context, field, String.valueOf(values));
    }

    public static void saveUserDataInShared(Context context, String field, long values)
    {
        saveUserDataInShared(context, field, String.valueOf(values));
    }

    public static void saveUserDataInShared(Context context, String field, ArrayList<String> value)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.size(); i++) {
            sb.append(value.get(i)).append(",");
        }

        saveUserDataInShared(context, field, sb.toString());
    }


    public static boolean getUserDataFromShared(Context context, String field, boolean defaultValue)
    {
        String value = getUserDataFromShared(context, field, "");

        if(!TextUtils.isEmpty(value)) {
            try {
                defaultValue = Boolean.parseBoolean(value);
            } catch (Exception e) {}
        }

        return defaultValue;
    }

    public static long getUserDataFromShared(Context context, String field, long defaultValue)
    {
        String value = getUserDataFromShared(context, field, "");
        if(!TextUtils.isEmpty(value)) {
            try {
                defaultValue = Long.parseLong(value);
            } catch (Exception e) {}
        }

        return defaultValue;
    }

    public static int getUserDataFromShared(Context context, String field, int defaultValue)
    {
        String value = getUserDataFromShared(context, field, "");
        if(!TextUtils.isEmpty(value)) {
            try {
                defaultValue = Integer.parseInt(value);
            } catch (Exception e) {}
        }

        return defaultValue;
    }

    public static double getUserDataFromShared(Context context, String field, double defaultValue)
    {
        String value = getUserDataFromShared(context, field, "");
        if(!TextUtils.isEmpty(value)) {
            try {
                defaultValue = Double.parseDouble(value);
            } catch (Exception e) {}
        }

        return defaultValue;
    }

    public static ArrayList<String> getUserStringArrayData(Context context, String field, String defaultValue)
    {
        ArrayList<String> arrayList = new ArrayList<>();
        String arrayDataString = getUserDataFromShared(context, field, defaultValue);

        if(!TextUtils.isEmpty(arrayDataString))
        {
            String[] arrayData = arrayDataString.split(",");

            for (int i = 0; i < arrayData.length; i++) {
                arrayList.add(i, arrayData[i]);
            }
        }
        return arrayList;
    }


    public static void clearUserSharedPreferences(Context context)
    {
        ConsoleLog.i(TAG, "Cleared login");
        SharedPreferences lp = context.getSharedPreferences(SESSION_DATA, Context.MODE_PRIVATE);
        lp.edit().clear().apply();
    }

    //Login Data ends


    //App Data starts

    public static void saveAppDataInShared(Context context, String field, String values) {
        try
        {
            SharedPreferences sp = context.getSharedPreferences(PERSISTENT_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(field, values);
            edit.apply();
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }
    }

    public static String getAppDataFromShared(Context context, String field, String defaultValue)
    {
        try
        {
            SharedPreferences sp = context.getSharedPreferences(PERSISTENT_DATA, Context.MODE_PRIVATE);
            return sp.getString(field, defaultValue);
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
            return "";
        }
    }

    public static void saveAppDataInShared(Context context, String field, int values)
    {
        saveAppDataInShared(context, field, String.valueOf(values));
    }

    public static void saveAppDataInShared(Context context, String field, boolean values)
    {
        saveAppDataInShared(context, field, String.valueOf(values));
    }

    public static void saveAppDataInShared(Context context, String field, double values)
    {
        saveAppDataInShared(context, field, String.valueOf(values));
    }

    public static void saveAppDataInShared(Context context, String field, long values)
    {
        saveAppDataInShared(context, field, String.valueOf(values));
    }

    public static void saveAppDataInShared(Context context, String field, ArrayList<String> value) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.size(); i++) {
            sb.append(value.get(i)).append(",");
        }
        saveAppDataInShared(context, field, sb.toString());
    }

    public static int getAppDataFromShared(Context context, String field, int defaultValue)
    {
        String value = getAppDataFromShared(context, field, "");
        if(!TextUtils.isEmpty(value)) {
            try {
                defaultValue = Integer.parseInt(value);
            } catch (Exception e) {}
        }

        return defaultValue;
    }

    public static boolean getAppDataFromShared(Context context, String field, boolean defaultValue)
    {
        String value = getAppDataFromShared(context, field, "");
        if(!TextUtils.isEmpty(value)) {
            try {
                defaultValue = Boolean.parseBoolean(value);
            } catch (Exception e) {}
        }

        return defaultValue;
    }


    public static Long getAppDataFromShared(Context context, String field, long defaultValue)
    {
        String value = getAppDataFromShared(context, field, "");
        if(!TextUtils.isEmpty(value)) {
            try {
                defaultValue = Long.parseLong(value);
            } catch (Exception e) {}
        }

        return defaultValue;
    }

    public static ArrayList<String> getPersistentStringArrayData(Context context, String field, String defaultValue) {
        ArrayList<String> arrayList = new ArrayList<>();
        String arrayDataString = getAppDataFromShared(context, field, defaultValue);

        if(!TextUtils.isEmpty(arrayDataString))
        {
            String[] arrayData = arrayDataString.split(",");

            for (int i = 0; i < arrayData.length; i++) {
                arrayList.add(i, arrayData[i]);
            }
        }
        return arrayList;
    }

    public static void clearAppSharedPreferences(Context context)
    {
        SharedPreferences lp = context.getSharedPreferences(PERSISTENT_DATA, Context.MODE_PRIVATE);
        lp.edit().clear().apply();
    }
}
