/*
 *  @category DroidWizard
 *  @copyright Copyright (C) 2017 Prasilabs. All rights reserved.
 *  @license http://www.apache.org/licenses/LICENSE-2.0
 */
package com.prasilabs.droidwizardlib.core.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.prasilabs.droidwizardlib.core.CoreApp;
import com.prasilabs.droidwizardlib.core.views.CoreActivityView;
import com.prasilabs.droidwizardlib.core.views.CoreFragmentView;
import com.prasilabs.droidwizardlib.debug.ConsoleLog;

import java.util.Observer;

/**
 * Base presenter class
 * Presenter is coupled with view and attched to lifecycle of views {@link android.app.Activity}, {@link android.support.v4.app.Fragment}
 *
 * presenter talks to modelEngine {@link com.prasilabs.droidwizardlib.core.modelEngines.CoreModelEngine} to get the
 * data and based on the data it will ask the view to do the work
 *
 * Views are actually dumb. their only job is to present the data in a nice way. A presenter will have the logic to
 * decide what the view should do for the type of data that it received
 * @author Prasanna Anbazhagan <praslnx8@gmail.com>
 * @version 1.0
 */
public abstract class CoreModelView<T extends CoreCallBack> implements Observer
{
    private static final String TAG = CoreModelView.class.getSimpleName();
    private BroadcastReceiver broadcastReceiver;
    private Context context;
    private T coreCallBack;

    /**
     * Public default constructor
     */
    public CoreModelView() {
    }

    /**
     * onCreate() lifecycle of presenter. attached to views like
     * {@link CoreFragmentView}, {@link CoreActivityView}
     *
     * Broadcast will be register in the onCreate() and destroyed at onDestroy()
     *
     * Broadcast will be listen for data change from modelEngine {@link com.prasilabs.droidwizardlib.core.modelEngines.CoreModelEngine}
     * @param context view context
     */
    public void onCreate(Context context)
    {
        this.context = context;
        broadcastReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                broadCastRecieved(context, intent);
            }
        };

        onCreateCalled();
    }

    /**
     * Oncreate() method for the extended presenters
     */
    protected abstract void onCreateCalled();

    /**
     * Registering the intents for listening to data change broadcast
     * @param intentFilter intent filter {@link IntentFilter}
     */
    protected void registerReciever(IntentFilter intentFilter)
    {
        if(CoreApp.getAppContext() != null)
        {
            LocalBroadcastManager.getInstance(CoreApp.getAppContext()).registerReceiver(broadcastReceiver, intentFilter);
        }
        else
        {
            ConsoleLog.w(TAG, "context is null :( core app");
        }
    }

    /**
     * Abstract method that will be triggered if any registered broadcast arrived
     * @param context broadcast context
     * @param intent broadcast intent message
     */
    protected abstract void broadCastRecieved(Context context, Intent intent);

    /**
     * onDestroy() lifecycle. The broadcast will be killed at this point
     */
    public void onDestroy()
    {
        if(broadcastReceiver != null)
        {
            LocalBroadcastManager.getInstance(CoreApp.getAppContext()).unregisterReceiver(broadcastReceiver);
        }

        coreCallBack = null;
    }

    /**
     * Method for accesing views context
     * @return context
     */
    public Context getContext() {
        return context;
    }

    /**
     * Method for getting the presenter callback to communicate back to view
     * @return return {@link CoreCallBack}
     */
    public T getCoreCallBack() {
        return coreCallBack;
    }

    /**
     * for registering the presenters callback interface
     * @param t callback interface
     */
    public void setCoreCallBack(T t) {
        this.coreCallBack = t;
    }
}
