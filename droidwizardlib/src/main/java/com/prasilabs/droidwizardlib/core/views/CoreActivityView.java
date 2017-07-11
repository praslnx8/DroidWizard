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

import com.prasilabs.droidwizardlib.core.presenters.CoreCallBack;
import com.prasilabs.droidwizardlib.core.presenters.CoreModelView;

/**
 * Base Activity class
 * CoreActivityView is the base class of activity with support for binding presenters and callback
 *
 * T is the presenter that is being attached to activity
 *
 * @author Prasanna Anbazhagan <praslnx8@gmail.com>
 * @version 1.0
 */
public abstract class CoreActivityView<T extends CoreModelView> extends AppCompatActivity
{
    private T corePresenter;

    /**
     * onCreate() method where presenter onCreate() will be binded to Activity
     * and callback will be attached
     * @param savedInstanceState from the activity
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        corePresenter = setCorePresenter();
        if(corePresenter != null)
        {
            corePresenter.onCreate(this);
            corePresenter.setCoreCallBack(getCoreCallBack());
        }
    }

    /**
     * abstract method to get the presenter interface
     * @return presenter interface {@link CoreCallBack}
     */
    protected abstract CoreCallBack getCoreCallBack();

    protected abstract T setCorePresenter();

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
     * onDestroy() lifecylce presenter will be detached
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(corePresenter != null)
        {
            corePresenter.onDestroy();
        }
        corePresenter = null;
    }

    protected T getPresenter()
    {
        return corePresenter;
    }
}