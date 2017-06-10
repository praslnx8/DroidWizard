package com.prasilabs.droidwizardlib.core.modelEngines;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;


import com.prasilabs.droidwizardlib.core.CoreApp;
import com.prasilabs.droidwizardlib.debug.ConsoleLog;

/**
 * Created by prasi on 27/5/16.
 */
public class CoreModelEngine
{
    protected CoreModelEngine(){}

    protected void sendBroadCast(Intent intent)
    {
        if(CoreApp.getAppContext() != null)
        {
            LocalBroadcastManager.getInstance(CoreApp.getAppContext()).sendBroadcast(intent);
        }
    }

    public <T> void runAsync(final RunAsyncCallBack runAsyncCallBack)
    {
        new AsyncTask<Void, Void, T>()
        {

            @Override
            protected T doInBackground(Void... params)
            {
                if(runAsyncCallBack != null)
                {
                    try
                    {
                        return runAsyncCallBack.run();
                    }
                    catch (Exception e)
                    {
                        ConsoleLog.e(e);
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(T t)
            {
                super.onPostExecute(t);

                if(runAsyncCallBack != null)
                {
                    runAsyncCallBack.result(t);
                }
            }
        }.execute();
    }

    public interface RunAsyncCallBack
    {
        <T> T run() throws Exception;

        <T> void result(T t);
    }
}
