package com.prasilabs.droidwizard.simpleActivity;

import com.prasilabs.droidwizardlib.core.CoreActivity;
import com.prasilabs.droidwizardlib.core.CoreCallBack;

/**
 * Created by prasi on 9/5/17.
 */

public class SimpleActivity extends CoreActivity<SimpleActivityPresenter> implements SimpleActivityCallBack
{

    @Override
    protected CoreCallBack getCoreCallBack() {
        return this;
    }

    @Override
    protected SimpleActivityPresenter setCorePresenter() {
        return new SimpleActivityPresenter();
    }
}
