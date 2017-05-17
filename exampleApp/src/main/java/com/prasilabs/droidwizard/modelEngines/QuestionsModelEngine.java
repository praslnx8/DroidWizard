package com.prasilabs.droidwizard.modelEngines;

import com.prasilabs.droidwizard.pojos.QuestionsPojo;
import com.prasilabs.droidwizardlib.core.CoreModelEngine;
import com.prasilabs.droidwizardlib.enums.ErrorCode;
import com.prasilabs.droidwizardlib.utils.JsonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Contus team on 10/5/17.
 */

public class QuestionsModelEngine extends CoreModelEngine
{
    private static final QuestionsModelEngine instance = new QuestionsModelEngine();

    //For caching local data
    private List<QuestionsPojo> cachedData = new ArrayList<>();

    public static QuestionsModelEngine getInstance() {
        return instance;
    }

    public void getQuestions(final GetQuestionsCallBack getQuestionsCallBack)
    {
        if (!cachedData.isEmpty())
        {
            if(getQuestionsCallBack != null)
            {
                getQuestionsCallBack.getQuestions(cachedData, null);
            }
        }
        else
        {
            String url = "https://api.stackexchange.com/2.2/questions?key=U4DMV*8nvpm3EOpvf69Rxw((&site=stackoverflow&order=desc&sort=activity&filter=default";


            makeVolleyCall(RequestType.GET, url, null, new ApiCallBack() {
                @Override
                public void result(String result) throws Exception {
                    JSONObject jsonObject = JsonUtil.createjsonobject(result);

                    JSONArray itemsArray = JsonUtil.checkHasArray(jsonObject, "items");

                    List<QuestionsPojo> questionsPojoList = new ArrayList<>();

                    for (int i = 0; i < itemsArray.length(); i++) {
                        JSONObject questionObject = JsonUtil.getJSonObjectFromJsonArray(itemsArray, i);

                        QuestionsPojo questionsPojo = JsonUtil.getObjectFromJson(questionObject, QuestionsPojo.class);

                        questionsPojoList.add(questionsPojo);
                    }

                    cachedData.clear();
                    cachedData.addAll(questionsPojoList);
                    if (getQuestionsCallBack != null)
                    {
                        getQuestionsCallBack.getQuestions(questionsPojoList, null);
                    }
                }

                @Override
                public void error(ErrorCode errorCode) {

                    if (getQuestionsCallBack != null) {
                        getQuestionsCallBack.getQuestions(null, errorCode);
                    }
                }
            });
        }
    }

    public interface GetQuestionsCallBack
    {
        void getQuestions(List<QuestionsPojo> questions, ErrorCode errorCode);
    }
}
