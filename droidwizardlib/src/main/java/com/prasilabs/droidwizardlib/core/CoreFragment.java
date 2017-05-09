package com.prasilabs.droidwizardlib.core;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prasilabs.droidwizardlib.debug.ConsoleLog;

/**
 * Created by prasi on 6/2/16.
 * All fragment should extend this class
 */
public abstract class CoreFragment<T extends CorePresenter> extends Fragment
{
    private static final String TAG = CoreFragment.class.getSimpleName();
    private View mFragmentView;
    private Context context;
    private CoreActivity coreActivity;
    private T corePresenter;
    private CoreDialogFragment coreDialogFragment;


    public View getFragmentView()
    {
        return mFragmentView;
    }

    private void setFragmentView(View fragmentView)
    {
        this.mFragmentView = fragmentView;
    }

    protected abstract T setCorePresenter();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        corePresenter = setCorePresenter();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (getFragmentView() == null) {
            setFragmentView(inflater.inflate(getLayout(), container, false));
        }

        if (corePresenter != null) {
            corePresenter.onCreate(context);
        }

        corePresenter.setCoreCallBack(getCoreCallBack());

        initializeView(savedInstanceState);

        return getFragmentView();
    }

    protected abstract void initializeView(Bundle savedInstanceState);

    protected abstract int getLayout();

    protected abstract CoreCallBack getCoreCallBack();

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (corePresenter != null) {
            corePresenter.onDestroy();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected boolean onDialogBackPressed()
    {
        return true;
    }

    protected void setDialogFragment(CoreDialogFragment coreDialogFragment) {
        this.coreDialogFragment = coreDialogFragment;
    }

    public CoreDialogFragment getCoreDialogFragment() {
        return coreDialogFragment;
    }

    public CoreActivity getCoreActivity()
    {
        if(coreActivity == null)
        {
            try {
                coreActivity = (CoreActivity) getActivity();
            }catch (Exception e)
            {
                ConsoleLog.e(e);
            }
        }
        return coreActivity;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;

        if(context instanceof CoreActivity)
        {
            this.coreActivity = (CoreActivity) context;
        }
    }

    @Override
    public Context getContext()
    {
        return context;
    }

    protected T  getPresenter()
    {
        return corePresenter;
    }



    public boolean onBackPressed()
    {
        return true;
    }
}
