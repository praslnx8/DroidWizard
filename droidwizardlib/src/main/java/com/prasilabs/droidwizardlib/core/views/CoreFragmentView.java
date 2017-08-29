package com.prasilabs.droidwizardlib.core.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prasilabs.droidwizardlib.core.viewModels.CoreCallBack;
import com.prasilabs.droidwizardlib.core.viewModels.CoreViewModel;
import com.prasilabs.droidwizardlib.debug.ConsoleLog;

/**
 * Created by prasi on 6/2/16.
 * All fragment should extend this class
 */
public abstract class CoreFragmentView<T extends CoreViewModel> extends Fragment
{
    private View mFragmentView;
    private Context context;
    private CoreActivityView coreActivityView;
    private T coreViewModel;


    public View getFragmentView()
    {
        return mFragmentView;
    }

    private void setFragmentView(View fragmentView)
    {
        this.mFragmentView = fragmentView;
    }

    protected abstract T setViewModel();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        coreViewModel = setViewModel();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (getFragmentView() == null) {
            setFragmentView(inflater.inflate(getLayout(), container, false));
        }

        if (coreViewModel != null) {
            coreViewModel.onCreate(context);
            coreViewModel.setCoreCallBack(getCoreCallBack());
        }


        return getFragmentView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeView(savedInstanceState);
    }

    protected abstract void initializeView(Bundle savedInstanceState);

    protected abstract int getLayout();

    protected abstract CoreCallBack getCoreCallBack();

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (coreViewModel != null) {
            coreViewModel.onDestroy();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        coreViewModel = null;
    }

    public CoreActivityView getCoreActivityView()
    {
        if(coreActivityView == null)
        {
            try {
                coreActivityView = (CoreActivityView) getActivity();
            }catch (Exception e)
            {
                ConsoleLog.e(e);
            }
        }
        return coreActivityView;
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

        if(context instanceof CoreActivityView)
        {
            this.coreActivityView = (CoreActivityView) context;
        }
    }

    @Override
    public Context getContext()
    {
        return context;
    }

    protected T getViewModel()
    {
        return coreViewModel;
    }



    public boolean onBackPressed()
    {
        return true;
    }
}
