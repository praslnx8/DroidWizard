package com.prasilabs.droidwizardlib.debug;

import android.text.TextUtils;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

/**
 * Debug tool
 *
 * @author Prasanna Anbazhagan <praslnx8@gmail.com>
 * @version 1.0
 */
public class ConsoleLog
{
    private static boolean isDebugMode;

    public static void setDebugMode(boolean isDebug) {
        isDebugMode = isDebug;
    }

    public static void i(String tag, String message)
    {
        if(isDebugMode)
        {
            if(TextUtils.isEmpty(message))
            {
                message = "null";
            }

            Logger.getLogger(tag).info(message);
        }
    }

    public static void w(String tag, String message)
    {
        if(isDebugMode)
        {
            if(TextUtils.isEmpty(message))
            {
                message = "null";
            }

            Logger.getLogger(tag).warning(message);
        }
    }

    public static void s(String tag, String message)
    {
        if(isDebugMode)
        {
            if(TextUtils.isEmpty(message))
            {
                message = "null";
            }

            Logger.getLogger(tag).severe(message);
        }
    }

    public static void t(Throwable throwable)
    {
        if(isDebugMode)
        {
            throwable.printStackTrace();
        }
    }
    public static void e(Exception e)
    {
        if(isDebugMode)
        {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));

            Log.e("EXCEPTION", errors.toString());
        }
    }
}
