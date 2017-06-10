package com.prasilabs.droidwizardlib.core.views;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.prasilabs.droidwizardlib.core.presenters.CoreCallBack;
import com.prasilabs.droidwizardlib.core.presenters.CorePresenter;

public abstract class CoreActivity<T extends CorePresenter> extends AppCompatActivity
{
    private T corePresenter;

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

    protected abstract CoreCallBack getCoreCallBack();

    protected abstract T setCorePresenter();

    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(corePresenter != null)
        {
            corePresenter.onDestroy();
        }
    }

    protected T getPresenter()
    {
        return corePresenter;
    }
}