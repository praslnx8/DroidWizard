package com.prasilabs.droidwizardlib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.prasilabs.droidwizardlib.debug.ConsoleLog;

import java.util.ArrayList;

/**
 * Created by prasi on 7/2/16.
 */
public class LocalPreference
{
    private static final String TAG = LocalPreference.class.getSimpleName();

    private static final String SESSION_DATA = "session_data";
    private static final String PERSISTENT_DATA = "persistent_data";

    public static void saveLoginDataInShared(Context context, String field, String values)
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

    public static String getLoginDataFromShared(Context context, String field, String defaultValue)
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

    public static void saveLoginDataInShared(Context context, String field, boolean values)
    {
        saveLoginDataInShared(context, field, String.valueOf(values));
    }

    public static void saveLoginDataInShared(Context context, String field, double values)
    {
        saveLoginDataInShared(context, field, String.valueOf(values));
    }

    public static void saveLoginDataInShared(Context context, String field, int values)
    {
        saveLoginDataInShared(context, field, String.valueOf(values));
    }

    public static void saveLoginDataInShared(Context context, String field, long values)
    {
        saveLoginDataInShared(context, field, String.valueOf(values));
    }

    public static void saveSessionDataInShared(Context context, String field, ArrayList<String> value)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.size(); i++) {
            sb.append(value.get(i)).append(",");
        }

        saveLoginDataInShared(context, field, sb.toString());
    }


    public static boolean getLoginDataFromShared(Context context, String field, boolean defaultValue)
    {
        String value = getLoginDataFromShared(context, field, "");

        if(!TextUtils.isEmpty(value)) {
            try {
                defaultValue = Boolean.parseBoolean(value);
            } catch (Exception e) {}
        }

        return defaultValue;
    }

    public static long getLoginDataFromShared(Context context, String field, long defaultValue)
    {
        String value = getLoginDataFromShared(context, field, "");
        if(!TextUtils.isEmpty(value)) {
            try {
                defaultValue = Long.parseLong(value);
            } catch (Exception e) {}
        }

        return defaultValue;
    }

    public static int getLoginDataFromShared(Context context, String field, int defaultValue)
    {
        String value = getLoginDataFromShared(context, field, "");
        if(!TextUtils.isEmpty(value)) {
            try {
                defaultValue = Integer.parseInt(value);
            } catch (Exception e) {}
        }

        return defaultValue;
    }

    public static double getLoginDataFromShared(Context context, String field, double defaultValue)
    {
        String value = getLoginDataFromShared(context, field, "");
        if(!TextUtils.isEmpty(value)) {
            try {
                defaultValue = Double.parseDouble(value);
            } catch (Exception e) {}
        }

        return defaultValue;
    }

    public static ArrayList<String> getSessionStringArrayData(Context context, String field, String defaultValue)
    {
        ArrayList<String> arrayList = new ArrayList<>();
        String arrayDataString = getLoginDataFromShared(context, field, defaultValue);

        if(!TextUtils.isEmpty(arrayDataString))
        {
            String[] arrayData = arrayDataString.split(",");

            for (int i = 0; i < arrayData.length; i++) {
                arrayList.add(i, arrayData[i]);
            }
        }
        return arrayList;
    }


    public static void clearLoginSharedPreferences(Context context)
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

    //App data ends


    //Location data starts
    /*public static void storeLocation(Context context, LatLng location, String key)
    {

        SharedPreferences prefs = context.getSharedPreferences(SESSION_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (location != null)
        {
            ConsoleLog.i(TAG, "Saving location is lat: " + location.latitude + " lon : " + location.longitude);

            editor.putLong(key + "LAT", Double.doubleToRawLongBits(location.latitude));
            editor.putLong(key + "LON", Double.doubleToRawLongBits(location.longitude));
            editor.apply();
        }
    }

    public static LatLng getLocationFromPrefs(Context context, String key)
    {
        SharedPreferences prefs = context.getSharedPreferences(SESSION_DATA, Context.MODE_PRIVATE);
        Long lat = prefs.getLong(key + "LAT", Long.MAX_VALUE);
        Long lng = prefs.getLong(key + "LON", Long.MAX_VALUE);
        LatLng latLng = computeLatLng(lat, lng);
        if (latLng == null)
        {
            ConsoleLog.i(TAG, "latlng is null for key " + key);
        }
        return latLng;
    }

    private static LatLng computeLatLng(Long lat, Long lng)
    {
        if ((lat != Long.MAX_VALUE && lng != Long.MAX_VALUE) && (lat != 0 && lng != 0))
        {
            Double latDbl = Double.longBitsToDouble(lat);
            Double lngDbl = Double.longBitsToDouble(lng);
            return new LatLng(latDbl, lngDbl);
        }
        return null;
    }*/

}
