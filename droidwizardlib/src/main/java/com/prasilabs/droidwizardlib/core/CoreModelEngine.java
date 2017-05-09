package com.prasilabs.droidwizardlib.core;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.ArrayMap;
import android.util.Base64;

import com.prasilabs.droidwizardlib.debug.ConsoleLog;
import com.prasilabs.droidwizardlib.enums.ErrorCode;
import com.prasilabs.droidwizardlib.services.network.NetworkManager;
import com.prasilabs.droidwizardlib.services.network.volley.VolleySingleton;
import com.prasilabs.droidwizardlib.utils.ViewUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * Created by prasi on 27/5/16.
 */
public abstract class CoreModelEngine
{
    private static final String TAG = CoreModelEngine.class.getSimpleName();

    protected <T> void asyncApiCall(final AsyncCallBack asyncCallBack)
    {
        asyncApiCall(asyncCallBack, false);
    }

    protected void makeTraccarVolleyCall(int mode, String url, Map<String, String> mParams, final ApiCallBack apiCallBack)
    {
        VolleySingleton.VolleyCallBack volleyCallBack =  new VolleySingleton.VolleyCallBack() {
            @Override
            public void result(String result)
            {
                if (apiCallBack != null)
                {
                    try
                    {
                        apiCallBack.result(result);
                    }
                    catch (Exception e)
                    {
                        ConsoleLog.e(e);
                        apiCallBack.error(ErrorCode.GENERAL);
                    }
                }
            }

            @Override
            public void error(ErrorCode errorCode) {
                if (apiCallBack != null) {
                    apiCallBack.error(errorCode);
                }
            }
        };

        Map<String, String> headers = getBasicAuthHeader("praslnx8@gmail.com", "prasi123");

        VolleySingleton.getInstance().makeStringRequest(mode, url, headers, mParams,volleyCallBack);
    }

    protected void makeTraccarVolleyCall(String url, JSONObject jsonObject, final ApiCallBack apiCallBack)
    {
        VolleySingleton.VolleyCallBack volleyCallBack =  new VolleySingleton.VolleyCallBack() {
            @Override
            public void result(String result)
            {
                if (apiCallBack != null)
                {
                    try
                    {
                        apiCallBack.result(result);
                    }
                    catch (Exception e)
                    {
                        ConsoleLog.e(e);
                        apiCallBack.error(ErrorCode.GENERAL);
                    }
                }
            }

            @Override
            public void error(ErrorCode errorCode) {
                if (apiCallBack != null) {
                    apiCallBack.error(errorCode);
                }
            }
        };

        Map<String, String> headers = getBasicAuthHeader("praslnx8@gmail.com", "prasi123");


        VolleySingleton.getInstance().makeStringRequest(url, jsonObject, headers, volleyCallBack);
    }

    protected void makeVolleyCall(String url, JSONObject jsonObject, final ApiCallBack apiCallBack)
    {
        VolleySingleton.VolleyCallBack volleyCallBack =  new VolleySingleton.VolleyCallBack() {
            @Override
            public void result(String result)
            {
                if (apiCallBack != null)
                {
                    try
                    {
                        apiCallBack.result(result);
                    }
                    catch (Exception e)
                    {
                        ConsoleLog.e(e);
                        apiCallBack.error(ErrorCode.GENERAL);
                    }
                }
            }

            @Override
            public void error(ErrorCode errorCode) {
                if (apiCallBack != null) {
                    apiCallBack.error(errorCode);
                }
            }
        };

        Map<String, String> headers = new ArrayMap<>();
        //TODO headers.put(CommonConstant.HASHAUTHHEADER, FUserManager.getUserHash(CoreApp.getAppContext()));


        VolleySingleton.getInstance().makeStringRequest(url, jsonObject, headers, volleyCallBack);
    }

    protected void makeVolleyCall(int mode, String url, Map<String, String> mParams, final ApiCallBack apiCallBack)
    {
        VolleySingleton.VolleyCallBack volleyCallBack =  new VolleySingleton.VolleyCallBack() {
            @Override
            public void result(String result)
            {
                if (apiCallBack != null)
                {
                    try
                    {
                        apiCallBack.result(result);
                    }
                    catch (Exception e)
                    {
                        ConsoleLog.e(e);
                        apiCallBack.error(ErrorCode.GENERAL);
                    }
                }
            }

            @Override
            public void error(ErrorCode errorCode) {
                if (apiCallBack != null) {
                    apiCallBack.error(errorCode);
                }
            }
        };

        Map<String, String> headers = new ArrayMap<>();
        //TODO headers.put(CommonConstant.HASHAUTHHEADER, FUserManager.getUserHash(CoreApp.getAppContext()));

        VolleySingleton.getInstance().makeStringRequest(mode, url, headers, mParams,volleyCallBack);
    }

    private static Map<String, String> getBasicAuthHeader(String username, String password)
    {
        Map<String, String> params = new ArrayMap<>();
        params.put("Authorization", String.format("Basic %s", Base64.encodeToString(String.format("%s:%s", username, password).getBytes(), Base64.DEFAULT)));
        return params;
    }

    protected <T> void asyncApiCall(final AsyncCallBack asyncCallBack, final boolean isBackgroundCall)
    {
        boolean isOnline = new NetworkManager(CoreApp.getAppContext(), /*new NetworkManager.NetworkHandler() {
            @Override
            public void onNetworkUpdate(boolean isOnline)
            {
                if(isBackgroundCall)
                {
                    call(asyncCallBack);
                }
            }
        }*/ null).isOnline();

        if (isOnline) {
            call(asyncCallBack);
        } else if (!isBackgroundCall) {
            ViewUtil.t(CoreApp.getAppContext(), "Please check the network and try again");

            if (asyncCallBack != null) {
                asyncCallBack.error(ErrorCode.NOT_CONNECTED);
            }
        }
    }

    protected void sendBroadCast(Intent intent)
    {
        if(CoreApp.getAppContext() != null)
        {
            LocalBroadcastManager.getInstance(CoreApp.getAppContext()).sendBroadcast(intent);
        }
        else
        {
            ConsoleLog.w(TAG, "context is null");
        }
    }


    private <T> void call(final AsyncCallBack asyncCallBack)
    {
        new AsyncTask<Void, Void, T>()
        {
            private ErrorCode errorCode = ErrorCode.GENERAL;
            private boolean isError;

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
            }

            @Override
            protected T doInBackground(Void... params)
            {
                try
                {
                    if (asyncCallBack != null)
                    {
                        return asyncCallBack.async();
                    }
                } catch (Exception e)
                {
                    ConsoleLog.e(e);

                    isError = true;
                    if (e instanceof IOException)
                    {
                        errorCode = ErrorCode.TIME_OUT;
                    }
                    else
                    {
                        errorCode = ErrorCode.GENERAL;
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(T t)
            {
                super.onPostExecute(t);

                if(asyncCallBack != null)
                {
                    if (isError)
                    {
                        asyncCallBack.error(errorCode);
                    } else {
                        asyncCallBack.result(t);
                    }
                }
            }
        }.execute();
    }

    public <T> void runAsync(final RunAsyncCallBack runAsyncCallBack)
    {
        new AsyncTask<Void, Void, T>()
        {

            @Override
            protected T doInBackground(Void... params)
            {
                if(runAsyncCallBack != null)
                {
                    try
                    {
                        return runAsyncCallBack.run();
                    }
                    catch (Exception e)
                    {
                        ConsoleLog.e(e);
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(T t)
            {
                super.onPostExecute(t);

                if(runAsyncCallBack != null)
                {
                    runAsyncCallBack.result(t);
                }
            }
        }.execute();
    }

    public interface AsyncCallBack
    {
        <T> T async() throws Exception;

        <T> void result(T t);

        void error(ErrorCode errorCode);
    }

    public interface ApiCallBack
    {
        void result(String result) throws Exception;

        void error(ErrorCode errorCode);
    }

    public interface RunAsyncCallBack
    {
        <T> T run() throws Exception;

        <T> void result(T t);
    }
}
