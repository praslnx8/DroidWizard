package com.prasilabs.droidwizardlib.services.crashReport;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.prasilabs.droidwizardlib.core.CoreApp;
import com.prasilabs.droidwizardlib.modules.SorryCrashActivity;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by prasi on 7/11/15.
 */
@SuppressLint("NewApi")
public class NoCrashHandler {

    //General constants
    private final static String TAG = NoCrashHandler.class.getSimpleName();
    private static final String EXTRA_RESTART_ACTIVITY_CLASS = "com.prasilabs.EXTRA_RESTART_ACTIVITY_CLASS";


    private static final String INTENT_ACTION_ERROR_ACTIVITY = "com.prasilabs.ERROR";
    private static final String INTENT_ACTION_RESTART_ACTIVITY = "com.prasilabs.RESTART";
    private static final String CAOC_HANDLER_PACKAGE_NAME = "com.prasilabs";
    private static final String DEFAULT_HANDLER_PACKAGE_NAME = "com.android.internal.os";

    //Internal variables
    private static Application application;
    private static WeakReference<Activity> lastActivityCreated = new WeakReference<>(null);
    private static boolean isInBackground = false;

    //Settable properties and their defaults
    private static boolean launchErrorActivityWhenInBackground = true;
    private static Class<? extends Activity> errorActivityClass = null;
    private static Class<? extends Activity> restartActivityClass = null;

    /**
     * Installs CustomActivityOnCrash on the application using the default error activity.
     *
     * @param context Context to use for obtaining the ApplicationContext. Must not be null.
     */
    public static void install(final Context context) {
        try {
            if (context == null) {
                Log.e(TAG, "Install failed: context is null!");
            } else {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    Log.w(TAG, "CustomActivityOnCrash will be installed, but may not be reliable in API lower than 14");
                }

                //INSTALL!
                Thread.UncaughtExceptionHandler oldHandler = Thread.getDefaultUncaughtExceptionHandler();

                if (oldHandler != null && oldHandler.getClass().getName().startsWith(CAOC_HANDLER_PACKAGE_NAME)) {
                    Log.e(TAG, "You have already installed CustomActivityOnCrash, doing nothing!");
                } else {
                    if (oldHandler != null && !oldHandler.getClass().getName().startsWith(DEFAULT_HANDLER_PACKAGE_NAME)) {
                        Log.e(TAG, "IMPORTANT WARNING! You already have an UncaughtExceptionHandler, are you sure this is correct? If you use ACRA, Crashlytics or similar libraries, you must initialize them AFTER CustomActivityOnCrash! Installing anyway, but your original handler will not be called.");
                    }

                    application = (Application) context.getApplicationContext();

                    //We define a default exception handler that does what we want so it can be called from Crashlytics/ACRA
                    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                        @Override
                        public void uncaughtException(Thread thread, final Throwable throwable) {

                            Log.i(TAG, "crashed");
                            if (CoreApp.appDebug) {
                                throwable.printStackTrace();
                            }
                            //call crash reporter;
                            //TODO sendExceptionReport(throwable);

                            if (errorActivityClass == null) {
                                errorActivityClass = guessErrorActivityClass(application);
                            }

                            if (isStackTraceLikelyConflictive(throwable, errorActivityClass)) {
                                Log.wtf(TAG, "Your application class or your error activity have crashed, the custom activity will not be launched!");
                            } else {
                                if (launchErrorActivityWhenInBackground || !isInBackground) {
                                    final Intent intent = new Intent(application, errorActivityClass);


                                    //We can set the restartActivityClass because the app will terminate right now,
                                    //and when relaunched, will be null again by default.
                                    restartActivityClass = guessRestartActivityClass(application);


                                    intent.putExtra(EXTRA_RESTART_ACTIVITY_CLASS, restartActivityClass);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    application.startActivity(intent);
                                }
                            }
                            final Activity lastActivity = lastActivityCreated.get();
                            if (lastActivity != null) {
                                //We finish the activity, this solves a bug which causes infinite recursion.
                                //This is unsolvable in API<14, so beware!
                                //See: https://github.com/ACRA/acra/issues/42
                                lastActivity.finish();
                                lastActivityCreated.clear();
                            }
                            killCurrentProcess();
                        }
                    });
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                            int currentlyStartedActivities = 0;

                            @Override
                            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                                if (activity.getClass() != errorActivityClass) {
                                    // Copied from ACRA:
                                    // Ignore activityClass because we want the last
                                    // application Activity that was started so that we can
                                    // explicitly kill it off.
                                    lastActivityCreated = new WeakReference<>(activity);
                                }
                            }

                            @Override
                            public void onActivityStarted(Activity activity) {
                                currentlyStartedActivities++;
                                isInBackground = (currentlyStartedActivities == 0);
                                //Do nothing
                            }

                            @Override
                            public void onActivityResumed(Activity activity) {
                                //Do nothing
                            }

                            @Override
                            public void onActivityPaused(Activity activity) {
                                //Do nothing
                            }

                            @Override
                            public void onActivityStopped(Activity activity) {
                                //Do nothing
                                currentlyStartedActivities--;
                                isInBackground = (currentlyStartedActivities == 0);
                            }

                            @Override
                            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                                //Do nothing
                            }

                            @Override
                            public void onActivityDestroyed(Activity activity) {
                                //Do nothing
                            }
                        });
                    }

                    Log.i(TAG, "CustomActivityOnCrash has been installed.");
                }

                //TODO CrashReportModelEngine.getInstance().reportCrashIfExist();
            }
        } catch (Throwable t) {

        }
    }

    public static void setCustomCrashActivity(Class<? extends Activity> activityClass) {
        errorActivityClass = activityClass;
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends Activity> getRestartActivityClassFromIntent(Intent intent) {
        Serializable serializedClass = intent.getSerializableExtra(NoCrashHandler.EXTRA_RESTART_ACTIVITY_CLASS);

        if (serializedClass != null && serializedClass instanceof Class) {
            return (Class<? extends Activity>) serializedClass;
        } else {
            return null;
        }
    }

    private static Class<? extends Activity> guessErrorActivityClass(Context context) {
        Class<? extends Activity> resolvedActivityClass;

        //If action is defined, use that
        resolvedActivityClass = NoCrashHandler.getErrorActivityClassWithIntentFilter(context);

        //Else, get the default launcher activity
        if (resolvedActivityClass == null) {
            resolvedActivityClass = SorryCrashActivity.class;
        }

        return resolvedActivityClass;
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends Activity> getErrorActivityClassWithIntentFilter(Context context) {
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(
                new Intent().setAction(INTENT_ACTION_ERROR_ACTIVITY),
                PackageManager.GET_RESOLVED_FILTER);

        if (resolveInfos != null && resolveInfos.size() > 0) {
            ResolveInfo resolveInfo = resolveInfos.get(0);
            try {
                return (Class<? extends Activity>) Class.forName(resolveInfo.activityInfo.name);
            } catch (ClassNotFoundException e) {
                //Should not happen, print it to the log!
            }
        }

        return null;
    }


    private static Class<? extends Activity> guessRestartActivityClass(Context context) {
        Class<? extends Activity> resolvedActivityClass;

        //If action is defined, use that
        resolvedActivityClass = NoCrashHandler.getRestartActivityClassWithIntentFilter(context);

        //Else, get the default launcher activity
        if (resolvedActivityClass == null) {
            resolvedActivityClass = getLauncherActivity(context);
        }

        return resolvedActivityClass;
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends Activity> getRestartActivityClassWithIntentFilter(Context context) {
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(
                new Intent().setAction(INTENT_ACTION_RESTART_ACTIVITY),
                PackageManager.GET_RESOLVED_FILTER);

        if (resolveInfos != null && resolveInfos.size() > 0) {
            ResolveInfo resolveInfo = resolveInfos.get(0);
            try {
                return (Class<? extends Activity>) Class.forName(resolveInfo.activityInfo.name);
            } catch (ClassNotFoundException e) {
                //Should not happen, print it to the log!
            }
        }

        return null;
    }


    /**
     * INTERNAL method used to get the default launcher activity for the app.
     * If there is no launchable activity, this returns null.
     *
     * @param context A valid context. Must not be null.
     * @return A valid activity class, or null if no suitable one is found
     */
    @SuppressWarnings("unchecked")
    private static Class<? extends Activity> getLauncherActivity(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (intent != null) {
            try {
                return (Class<? extends Activity>) Class.forName(intent.getComponent().getClassName());
            } catch (ClassNotFoundException e) {
                //Should not happen, print it to the log!
            }
        }

        return null;
    }

    private static boolean isStackTraceLikelyConflictive(Throwable throwable, Class<? extends Activity> activityClass) {
        do {
            StackTraceElement[] stackTrace = throwable.getStackTrace();
            for (StackTraceElement element : stackTrace) {
                if ((element.getClassName().equals("android.app.ActivityThread") && element.getMethodName().equals("handleBindApplication")) || element.getClassName().equals(activityClass.getName())) {
                    return true;
                }
            }
        } while ((throwable = throwable.getCause()) != null);
        return false;
    }


    public static void restartApplicationWithIntent(Activity activity, Intent intent) {
        final Class<? extends Activity> restartActivityClass = getRestartActivityClassFromIntent(intent);
        Intent restartIntent = new Intent(activity, restartActivityClass);

        restartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.finish();
        activity.startActivity(restartIntent);
        killCurrentProcess();
    }

    /**
     * Closes the app. Must only be used from your error activity.
     *
     * @param activity The current error activity. Must not be null.
     */
    public static void closeApplication(Activity activity) {
        activity.finish();
        killCurrentProcess();
    }

    /**
     * INTERNAL method that kills the current process.
     * It is used after restarting or killing the app.
     */
    private static void killCurrentProcess() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

    private static String getErrorMessage(Throwable throwable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        throwable.printStackTrace(printWriter);
        String stacktrace = result.toString();
        printWriter.close();

        return stacktrace;
    }


    private static String getErrorMessage(Exception e) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        String stacktrace = result.toString();
        printWriter.close();

        return stacktrace;
    }

    /*private static void sendExceptionReport(Throwable t)
    {
        String stackTrace = getErrorMessage(t);

        if (CoreApp.getAppContext() != null) {
            LocalPreference.saveAppDataInShared(CoreApp.getAppContext(), C.PreferenceConstant.ErrorReport.CRASH_REPORT, stackTrace);
        }

        //Will be done by SorryCrashActivity...  CrashReportModelEngine.getInstance().reportCrashIfExist();
    }*/
}