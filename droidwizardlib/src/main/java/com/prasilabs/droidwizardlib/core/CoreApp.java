package com.prasilabs.droidwizardlib.core;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;

import com.prasilabs.droidwizardlib.BuildConfig;

/**
 * CoreApp. The starting point of android app
 */
public abstract class CoreApp extends Application
{
    private static CoreApp mInstance;

    public static Context getAppContext()
    {
        return mInstance.getApplicationContext();
    }

    public static synchronized CoreApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
    }
}