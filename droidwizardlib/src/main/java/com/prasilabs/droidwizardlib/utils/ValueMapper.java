package com.prasilabs.droidwizardlib.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.prasilabs.droidwizardlib.debug.ConsoleLog;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by prasi on 11/1/16.
 */
public class ValueMapper
{
    private static final String TAG = ValueMapper.class.getSimpleName();


    public static Map<String, Object> bundleToParams(Bundle bundle)
    {
        Map<String, Object> mParams = new ArrayMap<>();

        for(String key : bundle.keySet())
        {
            if(bundle.get(key) != null)
            {
                mParams.put(key, bundle.get(key));
            }
        }

        return mParams;
    }

    public static ContentValues mapObjectToContentValues(ContentValues contentValues, Object object)
    {
        ArrayMap<String, Field> objectArrayMap = getFieldMapFromObject(object);

        for (String key : objectArrayMap.keySet())
        {
            Field field = objectArrayMap.get(key);

            try
            {

                field.setAccessible(true);
                if(field.getType().equals(int.class))
                {
                    int val = field.getInt(object);

                    if(val != 0) //checking not a default value
                    {
                        contentValues.put(key, val);
                    }
                }
                else if(field.getType().equals(double.class))
                {
                    double val =  field.getDouble(object);
                    if(val != 0.0)
                    {
                        contentValues.put(key, val);
                    }
                }
                else if(field.getType().equals(boolean.class))
                {
                    boolean val = field.getBoolean(object);
                    if(val)
                    {
                        contentValues.put(key, val);
                    }
                }
                else if(field.getType().equals(String.class))
                {
                    String val = (String) field.get(object);
                    if(!TextUtils.isEmpty(val))
                    {
                        contentValues.put(key, val);
                    }
                }
                else if(field.getType().equals(long.class))
                {
                    long val = field.getLong(object);
                    if(val != Long.MIN_VALUE && val != Long.MAX_VALUE)
                    {
                        contentValues.put(key, val);
                    }
                }
                else if(field.getType().equals(short.class))
                {
                    short val = field.getShort(object);
                    if(val != 0)
                    {
                        contentValues.put(key, val);
                    }
                }
                else if(field.getType().equals(float.class))
                {
                    float val = field.getFloat(object);
                    if(val != 0)
                    {
                        contentValues.put(key, val);
                    }
                }
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }

        return contentValues;
    }

    public static ArrayMap<String, String> getArrayMapFromObject(ArrayMap<String, Object> objectArrayMap)
    {
        ArrayMap<String, String> mParams = new ArrayMap<>();

        for (String key : objectArrayMap.keySet())
        {
            Object value = objectArrayMap.get(key);

            if(value != null)
            {
                if(value instanceof Integer)
                {
                    int val = (int) value;
                    mParams.put(key, String.valueOf(val));
                }
                else if(value instanceof Double)
                {
                    Double val = (Double) value;
                    mParams.put(key, String.valueOf(val));
                }
                else if(value instanceof Boolean)
                {
                    Boolean val = (Boolean) value;
                    mParams.put(key, String.valueOf(val));
                }
                else if(value instanceof String)
                {
                    String val = (String) value;
                    mParams.put(key, val);
                }
                else if(value instanceof Long)
                {
                    Long val = (Long) value;
                    mParams.put(key, String.valueOf(val));
                }
                else if(value instanceof Short)
                {
                    Short val = (Short) value;
                    mParams.put(key, String.valueOf(val));
                }
                else if(value instanceof Float)
                {
                    Float val = (Float) value;
                    mParams.put(key, String.valueOf(val));
                }
                else
                {
                    throw new IllegalArgumentException("Not a datatype");
                }
            }
        }

        return mParams;
    }


    public static Object mapJsontoObject(JSONObject jsonObject, Object object)
    {
        Field[] fields = object.getClass().getFields();
        if (jsonObject != null)
        {
            for(Field field : fields)
            {
                setFieldValueFromJson(object, field, jsonObject);
            }
        }

        return object;
    }

    public static Object mapCursortoObject(Cursor cursor, Object object)
    {
        Field[] fields = object.getClass().getDeclaredFields();
        if (cursor != null)
        {
            for(int i=0; i < cursor.getCount(); i++)
            {
                for(Field field : fields)
                {
                    field.setAccessible(true);
                    setFieldValueFromCursor(object, field, cursor);
                }
            }
        }

        return object;
    }


    //Cusrsor must be moved to the respective position
    private static Field setFieldValueFromCursor(Object o, Field field, Cursor cursor)
    {

        try
        {
            int columnIndex = cursor.getColumnIndex(field.getName());
            if(columnIndex > -1)
            {
                if (field.getType().equals(int.class))
                {
                    field.setInt(o, cursor.getInt(columnIndex));
                }
                else if (field.getType().equals(String.class))
                {
                    field.set(o, cursor.getString(columnIndex));
                }
                else if (field.getType().equals(boolean.class))
                {
                    boolean val = cursor.getInt(columnIndex) > 0;
                    field.setBoolean(o, val);
                }
                else if (field.getType().equals(double.class))
                {
                    field.setDouble(o, cursor.getDouble(columnIndex));
                }
                else if(field.getType().equals(long.class))
                {
                    field.setLong(o, cursor.getLong(columnIndex));
                }
            }
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }


        return field;
    }

    private static Field setFieldValueFromJson(Object o, Field field, JSONObject jsonObject)
    {
        if(field != null && jsonObject != null)
        {
            try
            {
                if(field.getType().isAssignableFrom(Integer.class))
                {
                    field.setInt(o, JsonUtil.checkHasInt(jsonObject, field.getName()));
                }
                else if(field.getType().isAssignableFrom(String.class))
                {
                    field.set(o, JsonUtil.checkHasString(jsonObject, field.getName()));
                }
                else if(field.getType().isAssignableFrom(Boolean.class))
                {
                    field.setBoolean(o, JsonUtil.checkHasBoolean(jsonObject, field.getName()));
                }
                else if(field.getType().isAssignableFrom(Double.class))
                {
                    field.setDouble(o, JsonUtil.checkHasDouble(jsonObject, field.getName()));
                }
                else if(field.getType().isAssignableFrom(int.class))
                {
                    field.setInt(o, JsonUtil.checkHasInt(jsonObject, field.getName()));
                }
                else if(field.getType().isAssignableFrom(boolean.class))
                {
                    field.setBoolean(o, JsonUtil.checkHasBoolean(jsonObject, field.getName()));
                }
                else if(field.getType().isAssignableFrom(double.class))
                {
                    field.setDouble(o, JsonUtil.checkHasDouble(jsonObject, field.getName()));
                }
            }
            catch (Exception e)
            {
                ConsoleLog.e(e);
            }
        }

        return field;
    }

    public static ArrayMap<String, Object> getMapFromObject(Object object)
    {
        ArrayMap<String, Object> objectArrayMap = new ArrayMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for(Field field : fields)
        {
            field.setAccessible(true);
            String name = field.getName();
            Object value;
            try
            {
                value = field.get(object);
                objectArrayMap.put(name, value);
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }

        }

        return objectArrayMap;
    }

    public static ArrayMap<String, Field> getFieldMapFromObject(Object object)
    {
        ArrayMap<String, Field> objectArrayMap = new ArrayMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for(Field field : fields)
        {
            objectArrayMap.put(field.getName(), field);
        }

        return objectArrayMap;
    }
}
