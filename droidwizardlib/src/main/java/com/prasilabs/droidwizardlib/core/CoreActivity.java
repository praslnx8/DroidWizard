package com.prasilabs.droidwizardlib.core;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class CoreActivity<T extends CorePresenter> extends AppCompatActivity
{
    private static final String TAG = CoreActivity.class.getSimpleName();
    private T corePresenter;

    private boolean isVisible;
    private Dialog dialog;

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

    @Override
    protected void onStop() {
        super.onStop();

        isVisible = false;

    }

    @Override
    protected void onResume() {
        super.onResume();

        isVisible = true;
    }



    public boolean isVisible() {
        return isVisible;
    }

    protected T getPresenter()
    {
        return corePresenter;
    }
}