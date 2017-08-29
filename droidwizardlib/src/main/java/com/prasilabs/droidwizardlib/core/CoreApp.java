/*
 *  @category DroidWizard
 *  @copyright Copyright (C) 2017 Prasilabs. All rights reserved.
 *  @license http://www.apache.org/licenses/LICENSE-2.0
 */
package com.prasilabs.droidwizardlib.core;

import android.app.Application;
import android.content.Context;


/**
 * Starting point of app. Application context
 * @author Prasanna Anbazhagan <praslnx8@gmail.com>
 * @version 1.0
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