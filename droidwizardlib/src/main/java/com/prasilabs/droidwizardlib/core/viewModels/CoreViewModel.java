/*
 *  @category DroidWizard
 *  @copyright Copyright (C) 2017 Prasilabs. All rights reserved.
 *  @license http://www.apache.org/licenses/LICENSE-2.0
 */
package com.prasilabs.droidwizardlib.core.viewModels;

import android.content.Context;

import com.prasilabs.droidwizardlib.core.modelEngines.CoreModelEngine;
import com.prasilabs.droidwizardlib.core.views.CoreActivityView;
import com.prasilabs.droidwizardlib.core.views.CoreFragmentView;

import java.util.Observable;
import java.util.Observer;

/**
 * Base view model class
 * View model is coupled with view and attached to lifecycle of views {@link android.app.Activity},
 * {@link android.support.v4.app.Fragment}
 *
 * view model talks to modelEngine {@link com.prasilabs.droidwizardlib.core.modelEngines.CoreModelEngine} to get the
 * data and based on the data it will ask the view to do the work
 *
 * Views are actually dumb. their only job is to present the data in a nice way. A view model will have the logic to
 * decide what the view should do for the type of data that it received
 * @author Prasanna Anbazhagan <praslnx8@gmail.com>
 * @version 1.0
 */
public abstract class CoreViewModel<T extends CoreCallBack> implements Observer
{
    private Context context;
    private T coreCallBack;

    /**
     * Public default constructor
     */
    public CoreViewModel() {
    }

    /**
     * onCreate() lifecycle of view model. attached to views like
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

        onCreateCalled();
    }

    /**
     * Oncreate() method for the extended view model
     */
    protected abstract void onCreateCalled();


    /**
     * Abstract method that will be triggered if any observer data arrived.
     *
     * @param modelEngine Updated model engine.
     * @param data updated data/message if any.
     */
    protected abstract void modelEngineUpdated(CoreModelEngine modelEngine, Object data);

    /**
     * onDestroy() lifecycle. The broadcast will be killed at this point
     */
    public void onDestroy()
    {
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
     * Method for getting the view model callback to communicate back to view
     * @return return {@link CoreCallBack}
     */
    public T getCoreCallBack() {
        return coreCallBack;
    }

    /**
     * for registering the view model callback interface
     * @param t callback interface
     */
    public void setCoreCallBack(T t) {
        this.coreCallBack = t;
    }

    @Override
    public final void update(Observable o, Object arg) {
        if(o instanceof CoreModelEngine) {
            modelEngineUpdated((CoreModelEngine) o, arg);
        }
    }
}
