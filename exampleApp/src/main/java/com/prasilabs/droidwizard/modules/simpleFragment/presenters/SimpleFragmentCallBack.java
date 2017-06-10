package com.prasilabs.droidwizard.modules.simpleFragment.presenters;

import com.prasilabs.droidwizard.pojos.QuestionsPojo;
import com.prasilabs.droidwizardlib.core.presenters.CoreCallBack;

import java.util.List;

/**
 * Created by prasi on 9/5/17.
 */

public interface SimpleFragmentCallBack extends CoreCallBack
{
    void showQuestions(List<QuestionsPojo> questions);

    void showEmpty();
}
