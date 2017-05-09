package com.prasilabs.droidwizardlib.core;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;

import com.prasilabs.droidwizardlib.BuildConfig;

/**
 * CoreApp. The starting point of android app
 */
public class CoreApp extends Application
{
    public static final String TAG = CoreApp.class.getSimpleName();
    public static boolean appDebug = BuildConfig.DEBUG;

    private static CoreApp mInstance;

    public static Context getAppContext()
    {
        return mInstance.getApplicationContext();
    }

    public static synchronized CoreApp getInstance() {
        return mInstance;
    }

    public static String getDeviceId()
    {
        String android_id = Settings.Secure.getString(CoreApp.getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return android_id;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
    }
}