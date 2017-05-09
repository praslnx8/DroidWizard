package com.prasilabs.droidwizard.simpleFragment;

import android.os.Bundle;

import com.prasilabs.droidwizardlib.core.CoreCallBack;
import com.prasilabs.droidwizardlib.core.CoreFragment;

/**
 * Created by prasi on 9/5/17.
 */

public class SimpleFragment extends CoreFragment<SimpleFragmentPresenter> implements SimpleFragmentCallBack {

    @Override
    protected com.prasilabs.droidwizard.simpleFragment.SimpleFragmentPresenter setCorePresenter() {
        return null;
    }

    @Override
    protected void initializeView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected CoreCallBack getCoreCallBack() {
        return null;
    }
}
