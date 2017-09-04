package com.prasilabs.droidwizard.modules.simpleActivity.viewModels;

import com.prasilabs.droidwizard.modelEngines.QuestionsModelEngine;
import com.prasilabs.droidwizard.pojos.QuestionsPojo;
import com.prasilabs.droidwizard.utils.NetworkManager;
import com.prasilabs.droidwizardlib.core.modelEngines.CoreModelEngine;
import com.prasilabs.droidwizardlib.core.viewModels.CoreViewModel;

import java.util.List;

/**
 * Created by prasi on 9/5/17.
 */

public class SimpleActivityViewModel extends CoreViewModel<SimpleActivityCallBack>
{
    public void getQuestions()
    {
        if(getVMCallBack() != null)
        {
            getVMCallBack().showProgress();
        }
        if(NetworkManager.isConnected(getContext()))
        {
            QuestionsModelEngine.getInstance().getQuestions(new QuestionsModelEngine.GetQuestionsCallBack() {
                @Override
                public void getQuestions(List<QuestionsPojo> questions)
                {
                    if(questions != null && !questions.isEmpty())
                    {
                        if(getVMCallBack() != null)
                        {
                            getVMCallBack().showQuestions(questions);
                        }
                    }
                    else
                    {
                        if(getVMCallBack() != null)
                        {
                            getVMCallBack().showEmpty();
                        }
                    }
                }
            });
        }
        else
        {
            if(getVMCallBack() != null)
            {
                getVMCallBack().showNoInternet();
            }
        }

    }

    @Override
    protected void onCreateCalled() {

    }

    @Override
    protected void modelEngineUpdated(CoreModelEngine modelEngine, Object data) {
        //update view based on model change.
    }
}
