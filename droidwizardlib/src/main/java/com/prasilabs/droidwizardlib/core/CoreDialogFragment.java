package com.prasilabs.droidwizardlib.core;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.prasilabs.droidwizardlib.R;
import com.prasilabs.droidwizardlib.utils.FragmentNavigator;


/**
 * Created by prasi on 8/2/16.
 */
public class CoreDialogFragment extends DialogFragment
{
    private static final String TAG = CoreDialogFragment.class.getSimpleName();
    private static final int TYPE_FULL_SCREEN = 1;
    private static final int TYPE_DIALOG = 2;
    private static final String LAYOUT_TYPE_STR = "type";


    private LinearLayout linearLayout;
    private CoreFragment coreFragment;

    private View view;
    private boolean isRenderNeeded = false;
    private int layoutType;


    public static CoreDialogFragment showFragmentAsFullScreen(CoreActivity coreActivity, CoreFragment coreFragment)
    {
        FragmentManager fm = coreActivity.getSupportFragmentManager();
        CoreDialogFragment coreDialogFragment = new CoreDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_TYPE_STR, TYPE_FULL_SCREEN);
        coreDialogFragment.setArguments(bundle);

        coreDialogFragment.setCoreFragment(coreFragment);
        coreDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.fullScreen_Dialog);
        coreDialogFragment.show(fm, coreDialogFragment.getClass().getSimpleName());

        return coreDialogFragment;
    }

    public static CoreDialogFragment showFragmentAsFullScreen(CoreFragment parentFragment, CoreFragment coreFragment) {
        FragmentManager fm = parentFragment.getChildFragmentManager();
        CoreDialogFragment coreDialogFragment = new CoreDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_TYPE_STR, TYPE_FULL_SCREEN);
        coreDialogFragment.setArguments(bundle);

        coreDialogFragment.setCoreFragment(coreFragment);
        coreDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.fullScreen_Dialog);
        coreDialogFragment.show(fm, coreDialogFragment.getClass().getSimpleName());

        return coreDialogFragment;
    }

    public static CoreDialogFragment showFragmentAsDialog(CoreActivity coreActivity, CoreFragment coreFragment) {
        FragmentManager fm = coreActivity.getSupportFragmentManager();
        CoreDialogFragment coreDialogFragment = new CoreDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_TYPE_STR, TYPE_DIALOG);
        coreDialogFragment.setArguments(bundle);

        coreDialogFragment.setCoreFragment(coreFragment);
        coreDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.centre_Dialog);
        coreDialogFragment.show(fm, coreDialogFragment.getClass().getSimpleName());

        return coreDialogFragment;
    }

    public static CoreDialogFragment showFragmentAsDialog(CoreFragment parentFragment, CoreFragment coreFragment) {
        FragmentManager fm = parentFragment.getChildFragmentManager();
        CoreDialogFragment coreDialogFragment = new CoreDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_TYPE_STR, TYPE_DIALOG);
        coreDialogFragment.setArguments(bundle);

        coreDialogFragment.setCoreFragment(coreFragment);
        coreDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.centre_Dialog);
        coreDialogFragment.show(fm, coreDialogFragment.getClass().getSimpleName());

        return coreDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {
            layoutType = getArguments().getInt(LAYOUT_TYPE_STR, 0);
        }

        if(coreFragment != null)
        {
            coreFragment.setDialogFragment(this);
        }
    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (layoutType == TYPE_FULL_SCREEN) {
            WindowManager.LayoutParams wmlp = getDialog().getWindow().getAttributes();
            wmlp.gravity = Gravity.FILL_HORIZONTAL | Gravity.FILL_VERTICAL;
        } else if (layoutType == TYPE_DIALOG) {
            WindowManager.LayoutParams wmlp = getDialog().getWindow().getAttributes();
            wmlp.gravity = Gravity.CENTER;
        }

        if(view == null)
        {
            isRenderNeeded = true;
            view = inflater.inflate(R.layout.dialog_linear_layout, container, false);

            linearLayout = (LinearLayout) view.findViewById(R.id.linear_layout);
        }

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        if(isRenderNeeded)
        {
            FragmentNavigator.placeFragment(this, coreFragment, linearLayout.getId());
        }

        this.getDialog().setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                {
                    return !coreFragment.onDialogBackPressed();
                }
                return false;
            }
        });
    }

    public void setCoreFragment(CoreFragment coreFragment)
    {
        this.coreFragment = coreFragment;
    }
}
