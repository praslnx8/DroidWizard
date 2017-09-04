/*
 *  @category DroidWizard
 *  @copyright Copyright (C) 2017 Prasilabs. All rights reserved.
 *  @license http://www.apache.org/licenses/LICENSE-2.0
 */
package com.prasilabs.droidwizardlib.core.views;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.prasilabs.droidwizardlib.core.viewModels.CoreCallBack;
import com.prasilabs.droidwizardlib.core.viewModels.CoreViewModel;

/**
 * Base Activity class
 * CoreActivityView is the base class of activity with support for binding view model and callback
 *
 * T is the view model that is being attached to activity
 *
 * @author Prasanna Anbazhagan <praslnx8@gmail.com>
 * @version 1.0
 */
public abstract class CoreActivityView<T extends CoreViewModel> extends AppCompatActivity
{
    private T coreViewModel;

    /**
     * onCreate() method where view model onCreate() will be binded to Activity
     * and callback will be attached
     * @param savedInstanceState from the activity
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        coreViewModel = setCoreViewModel();
        if(coreViewModel != null)
        {
            coreViewModel.onCreate(this);
            coreViewModel.setVMCallBack(getCoreCallBack());
        }
    }

    /**
     * abstract method to get the view model interface
     * @return view model interface {@link CoreCallBack}
     */
    protected abstract CoreCallBack getCoreCallBack();

    protected abstract T setCoreViewModel();

    /**
     * abstract method for registering layouts
     * @param layoutResID layout id
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        super.setContentView(layoutResID);
    }

    /**
     * onDestroy() lifecycle view model will be detached
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(coreViewModel != null)
        {
            coreViewModel.onDestroy();
        }
        coreViewModel = null;
    }

    protected T getViewModel()
    {
        return coreViewModel;
    }
}