package com.prasilabs.droidwizard.modules.simpleFragment.viewModels;

import android.content.Context;
import android.content.Intent;

import com.prasilabs.droidwizard.modelEngines.QuestionsModelEngine;
import com.prasilabs.droidwizard.pojos.QuestionsPojo;
import com.prasilabs.droidwizardlib.core.viewModels.CoreViewModel;

import java.util.List;

/**
 * Created by prasi on 9/5/17.
 */

public class SimpleFragmentViewModel extends CoreViewModel<SimpleFragmentCallBack>
{

    @Override
    protected void onCreateCalled() {

    }

    @Override
    protected void broadCastRecieved(Context context, Intent intent) {

    }

    public void getQuestions()
    {
        QuestionsModelEngine.getInstance().getQuestions(new QuestionsModelEngine.GetQuestionsCallBack() {
            @Override
            public void getQuestions(List<QuestionsPojo> questions)
            {
                if(questions != null && !questions.isEmpty())
                {
                    if(getCoreCallBack() != null)
                    {
                        getCoreCallBack().showQuestions(questions);
                    }
                    else
                    {
                        getCoreCallBack().showEmpty();
                    }
                }
            }
        });
    }
}
