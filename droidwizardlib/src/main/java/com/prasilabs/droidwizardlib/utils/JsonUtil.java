package com.prasilabs.droidwizardlib.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.prasilabs.droidwizardlib.debug.ConsoleLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtil {

    private static final String TAG = JsonUtil.class.getSimpleName();

    public static JSONObject createjsonobject(String jsonObjectString) {
        try
        {
            return new JSONObject(jsonObjectString);
        } catch (Exception e)
        {
            ConsoleLog.e(e);
        }

        return new JSONObject();
    }

    public static JSONArray createJsonArray(String jsonArrayString)
    {
        try
        {
            return new JSONArray(jsonArrayString);
        } catch (Exception e)
        {
            ConsoleLog.e(e);
        }

        return new JSONArray();
    }

    public static JSONObject getJSonObjectFromJsonArray(JSONArray jsonArray, int position) {
        try {
            return jsonArray.getJSONObject(position);
        } catch (JSONException e)
        {
            ConsoleLog.e(e);
        }

        return new JSONObject();
    }

    public static String getStringFromJsonArray(JSONArray jsonArray, int position) {
        try {
            return String.valueOf(jsonArray.get(position));
        } catch (JSONException e)
        {
            ConsoleLog.e(e);
        }

        return "";
    }

    public static String checkHasString(JSONObject jsonObject, String toCheck) {
        if (jsonObject != null) {
            if (jsonObject.has(toCheck)) {
                try {
                    String string = jsonObject.getString(toCheck);
                    if (string.equals("null") || string.equals("Null")) {
                        string = "";
                    }
                    return string;
                } catch (Exception e) {
                    ConsoleLog.e(e);
                    return "";
                }
            } else {
                return "";
            }
        }
        return "";
    }

    public static double checkHasDouble(JSONObject jsonObject, String toCheck) {
        if (jsonObject != null) {
            if (jsonObject.has(toCheck)) {
                try {
                    return jsonObject.getDouble(toCheck);
                } catch (Exception e) {
                    ConsoleLog.e(e);
                    return 0d;
                }
            } else {
                return 0d;
            }
        }
        return 0d;
    }

    public static int checkHasInt(JSONObject jsonObject, String toCheck) {
        if (jsonObject != null) {
            if (jsonObject.has(toCheck)) {
                try {
                    return jsonObject.getInt(toCheck);
                } catch (Exception e) {
                    ConsoleLog.e(e);
                    return 0;
                }
            } else {
                return 0;
            }
        }
        return 0;
    }

    public static long checkHasLong(JSONObject jsonObject, String toCheck) {
        if (jsonObject != null) {
            if (jsonObject.has(toCheck)) {
                try {
                    return jsonObject.getLong(toCheck);
                } catch (Exception e) {
                    ConsoleLog.e(e);
                    return 0;
                }
            } else {
                return 0;
            }
        }
        return 0;
    }

    public static boolean checkHasBoolean(JSONObject jsonObject, String toCheck) {
        if (jsonObject != null) {
            if (jsonObject.has(toCheck)) {
                try {
                    return jsonObject.getBoolean(toCheck);
                } catch (JSONException je) {
                    try {
                        return "1".equals(jsonObject.getString(toCheck)) || "success".equals(jsonObject.getString(toCheck));
                    } catch (Exception e) {
                        ConsoleLog.e(e);
                    }
                }
            }
        }
        return false;
    }

    public static JSONObject checkHasObject(JSONObject jsonObject, String toCheck) {
        if (jsonObject != null) {
            if (jsonObject.has(toCheck)) {
                try {
                    return jsonObject.getJSONObject(toCheck);
                } catch (Exception e) {
                    ConsoleLog.e(e);
                    return null;
                }
            } else {
                return null;
            }
        }
        return null;
    }

    public static JSONArray checkHasArray(JSONObject jsonObject, String toCheck) {
        if (jsonObject != null) {
            if (jsonObject.has(toCheck)) {
                try {
                    return jsonObject.getJSONArray(toCheck);
                } catch (Exception e) {
                    ConsoleLog.e(e);
                    return null;
                }
            } else {
                return null;
            }
        }
        return null;
    }

    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            ConsoleLog.e(e);
            return null;
        }
        return json;
    }


    public static <T> T getObjectFromJson(String jsonStr, Class<T> tClass)
    {
        try
        {
            return new Gson().fromJson(jsonStr, tClass);
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }

        return null;
    }

    public static <T> T getObjectFromJson(JSONObject jsonObject, Class<T> tClass)
    {
        return getObjectFromJson(jsonObject.toString(), tClass);
    }

    public static <T> JSONObject getJsonFromClass(T t)
    {
        try
        {
            String str = new Gson().toJson(t);
            JSONObject jsonObject = createjsonobject(str);

            return jsonObject;
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }

        return null;
    }
}