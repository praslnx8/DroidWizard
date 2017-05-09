package com.prasilabs.droidwizardlib.modules;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.prasilabs.droidwizardlib.R;


/**
 * Created by prasi on 10/9/16.
 */
public class SorryCrashActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crash);
    }
}
