package com.prasilabs.droidwizard.modelEngines;

import android.util.Log;

import com.prasilabs.droidwizard.pojos.QuestionsPojo;
import com.prasilabs.droidwizardlib.core.modelEngines.CoreModelEngine;
import com.prasilabs.droidwizardlib.debug.ConsoleLog;
import com.prasilabs.droidwizardlib.utils.JsonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Contus team on 10/5/17.
 */

public class QuestionsModelEngine extends CoreModelEngine
{
    private static final String TAG = QuestionsModelEngine.class.getSimpleName();
    private static final QuestionsModelEngine instance = new QuestionsModelEngine();

    //For caching local data
    private List<QuestionsPojo> cachedData = new ArrayList<>();

    public static QuestionsModelEngine getInstance() {
        return instance;
    }

    public void getQuestions(final GetQuestionsCallBack getQuestionsCallBack)
    {
        final String stackOverFlowUrl = "https://api.stackexchange.com/2.2/questions?key=U4DMV*8nvpm3EOpvf69Rxw((&site=stackoverflow&order=desc&sort=activity&filter=default";

        runAsync(new RunAsyncCallBack() {
            @Override
            public List<QuestionsPojo> run() throws Exception {
                List<QuestionsPojo> questionsPojos = new ArrayList<QuestionsPojo>();

                String server_response = null;
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(stackOverFlowUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    int responseCode = urlConnection.getResponseCode();

                    if(responseCode == HttpURLConnection.HTTP_OK){
                        server_response = readStream(urlConnection.getInputStream());
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ConsoleLog.i(TAG, server_response);

                JSONObject responseObject = JsonUtil.createjsonobject(server_response);
                JSONArray questionsArray = JsonUtil.checkHasArray(responseObject, "items");

                if(questionsArray != null) {
                    for(int i=0;i <questionsArray.length(); i++) {
                        JSONObject questionsObject = JsonUtil.getJSonObjectFromJsonArray(questionsArray, i);

                        QuestionsPojo questionsPojo = JsonUtil.getObjectFromJson(questionsObject, QuestionsPojo.class);

                        questionsPojos.add(questionsPojo);
                    }
                }

                return questionsPojos;
            }

            @Override
            public <T> void result(T t) {

                List<QuestionsPojo> questionsPojos = (List<QuestionsPojo>) t;

                if(getQuestionsCallBack != null) {
                    getQuestionsCallBack.getQuestions(questionsPojos);
                }
            }
        });
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    public interface GetQuestionsCallBack
    {
        void getQuestions(List<QuestionsPojo> questions);
    }
}
