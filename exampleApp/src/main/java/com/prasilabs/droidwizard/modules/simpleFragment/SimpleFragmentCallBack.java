package com.prasilabs.droidwizard.modules.simpleFragment;

import com.prasilabs.droidwizard.pojos.QuestionsPojo;
import com.prasilabs.droidwizardlib.core.CoreCallBack;

import java.util.List;

/**
 * Created by prasi on 9/5/17.
 */

public interface SimpleFragmentCallBack extends CoreCallBack
{
    void showQuestions(List<QuestionsPojo> questions);

    void showEmpty();
}
