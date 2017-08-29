/*
 *  @category DroidWizard
 *  @copyright Copyright (C) 2017 Prasilabs. All rights reserved.
 *  @license http://www.apache.org/licenses/LICENSE-2.0
 */

package com.prasilabs.droidwizardlib.core.modelEngines;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;


import com.prasilabs.droidwizardlib.core.CoreApp;
import com.prasilabs.droidwizardlib.debug.ConsoleLog;

import java.util.Observable;

/**
 * CoreModelEngine.
 *
 * ModelEngine is a singleton class runs in application context and takes care of fetching data from REST API, LOCAL
 * DATABASE and from other resources
 *
 * All ModelEngine should extend CoreModelEngine
 *
 * ModelEngines should be a Singleton class
 *
 * ModelEngine will decide whether to fetch data from cache or server.
 *
 * @author Prasanna Anbazhagan <praslnx8@gmail.com>
 * @version 1.0
 */
public abstract class CoreModelEngine extends Observable
{
    protected CoreModelEngine(){}

    /**
     * This method will help to send broadcast to view models that listens fot the data change.
     * Data changes is handled in model engine and if so will be notified to respective active view model with help
     * of broadcast
     * @param intent the broadcast intent with data change message
     */
    protected void sendBroadCast(Intent intent)
    {
        if(CoreApp.getAppContext() != null)
        {
            LocalBroadcastManager.getInstance(CoreApp.getAppContext()).sendBroadcast(intent);
        }
    }

    /**
     * Helper method to run asynchronous operation in model engine.
     * Fetching data from server and database can be run in background thread in run() method and result can be
     * fetched in result() method
     * @param runAsyncCallBack interface to run the job {@link RunAsyncCallBack}
     * @param <T> return data type
     */
    public <T> void runAsync(final RunAsyncCallBack runAsyncCallBack)
    {
        new AsyncTask<Void, Void, T>()
        {
            //Actual background runs here
            @Override
            protected T doInBackground(Void... params)
            {
                if(runAsyncCallBack != null)
                {
                    try
                    {
                        //run your background operation in run() method
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
                    //reap your result data here
                    runAsyncCallBack.result(t);
                }
            }
        }.execute();
    }

    /**
     * CallBack interface for runAsync method
     */
    public interface RunAsyncCallBack
    {
        <T> T run() throws Exception;

        <T> void result(T t);
    }
}
