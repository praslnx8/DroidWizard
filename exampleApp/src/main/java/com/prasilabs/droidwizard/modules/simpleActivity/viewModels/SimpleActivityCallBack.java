package com.prasilabs.droidwizard.modules.simpleActivity.viewModels;

import com.prasilabs.droidwizard.pojos.QuestionsPojo;
import com.prasilabs.droidwizardlib.core.viewModels.CoreCallBack;

import java.util.List;

/**
 * Created by prasi on 9/5/17.
 */

public interface SimpleActivityCallBack extends CoreCallBack
{
    void showQuestions(List<QuestionsPojo> questions);

    void showEmpty();

    void showNoInternet();

    void showProgress();
}
