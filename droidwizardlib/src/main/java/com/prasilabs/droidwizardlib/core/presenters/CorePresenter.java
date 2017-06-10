package com.prasilabs.droidwizardlib.core.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.prasilabs.droidwizardlib.core.CoreApp;
import com.prasilabs.droidwizardlib.debug.ConsoleLog;


/**
 * Created by prasi on 26/5/16.
 */
public abstract class CorePresenter<T extends CoreCallBack>
{
    private static final String TAG = CorePresenter.class.getSimpleName();
    private BroadcastReceiver broadcastReceiver;
    private Context context;
    private T coreCallBack;

    public CorePresenter() {
    }

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

    protected abstract void onCreateCalled();

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

    protected abstract void broadCastRecieved(Context context, Intent intent);

    public void onDestroy()
    {
        if(broadcastReceiver != null)
        {
            LocalBroadcastManager.getInstance(CoreApp.getAppContext()).unregisterReceiver(broadcastReceiver);
        }

        coreCallBack = null;
    }

    public Context getContext() {
        return context;
    }

    public T getCoreCallBack() {
        return coreCallBack;
    }

    public void setCoreCallBack(T t) {
        this.coreCallBack = t;
    }
}
