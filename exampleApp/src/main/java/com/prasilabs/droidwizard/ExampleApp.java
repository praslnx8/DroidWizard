package com.prasilabs.droidwizard;

import com.prasilabs.droidwizardlib.core.CoreApp;
import com.prasilabs.droidwizardlib.debug.ConsoleLog;

/**
 * Created by Contus team on 10/6/17.
 */

public class ExampleApp extends CoreApp {

    @Override
    public void onCreate() {
        super.onCreate();
        ConsoleLog.setDebugMode(true);
    }
}
