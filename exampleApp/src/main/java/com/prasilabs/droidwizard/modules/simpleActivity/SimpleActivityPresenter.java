package com.prasilabs.droidwizard.modules.simpleActivity;

import android.content.Context;
import android.content.Intent;

import com.prasilabs.droidwizard.modelEngines.QuestionsModelEngine;
import com.prasilabs.droidwizard.pojos.QuestionsPojo;
import com.prasilabs.droidwizardlib.core.CorePresenter;
import com.prasilabs.droidwizardlib.enums.ErrorCode;
import com.prasilabs.droidwizardlib.services.network.NetworkManager;

import java.util.List;

/**
 * Created by prasi on 9/5/17.
 */

public class SimpleActivityPresenter extends CorePresenter<SimpleActivityCallBack>
{
    @Override
    protected void onCreateCalled() {

    }

    @Override
    protected void broadCastRecieved(Context context, Intent intent) {

    }

    public void getQuestions()
    {
        if(getCoreCallBack() != null)
        {
            getCoreCallBack().showProgress();
        }
        if(NetworkManager.isConnected(getContext()))
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
                    }
                    else
                    {
                        if(getCoreCallBack() != null)
                        {
                            if(errorCode == ErrorCode.NOT_CONNECTED)
                            {
                                getCoreCallBack().showNoInternet();
                            }
                            else
                            {
                                getCoreCallBack().showEmpty();
                            }
                        }
                    }
                }
            });
        }
        else
        {
            if(getCoreCallBack() != null)
            {
                getCoreCallBack().showNoInternet();
            }
        }

    }
}
