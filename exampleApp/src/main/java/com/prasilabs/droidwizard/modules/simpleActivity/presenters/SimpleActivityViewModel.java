package com.prasilabs.droidwizard.modules.simpleActivity.presenters;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.prasilabs.droidwizard.modelEngines.QuestionsModelEngine;
import com.prasilabs.droidwizard.pojos.QuestionsPojo;
import com.prasilabs.droidwizard.utils.KeyConstants;
import com.prasilabs.droidwizard.utils.NetworkManager;
import com.prasilabs.droidwizardlib.core.presenters.CoreViewModel;

import java.util.List;
import java.util.Observable;

/**
 * Created by prasi on 9/5/17.
 */

public class SimpleActivityViewModel extends CoreViewModel<SimpleActivityCallBack>
{
    @Override
    protected void onCreateCalled() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(KeyConstants.NETWORK_BROADCAST_KEY);
        registerReciever(intentFilter);
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
                public void getQuestions(List<QuestionsPojo> questions)
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
                            getCoreCallBack().showEmpty();
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
