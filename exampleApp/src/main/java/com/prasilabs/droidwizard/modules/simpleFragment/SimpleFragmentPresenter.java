package com.prasilabs.droidwizard.modules.simpleFragment;

import android.content.Context;
import android.content.Intent;

import com.prasilabs.droidwizard.modelEngines.QuestionsModelEngine;
import com.prasilabs.droidwizard.pojos.QuestionsPojo;
import com.prasilabs.droidwizardlib.core.CorePresenter;
import com.prasilabs.droidwizardlib.enums.ErrorCode;

import java.util.List;

/**
 * Created by prasi on 9/5/17.
 */

public class SimpleFragmentPresenter extends CorePresenter<SimpleFragmentCallBack>
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
            public void getQuestions(List<QuestionsPojo> questions, ErrorCode errorCode)
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
