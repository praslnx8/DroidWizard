package com.prasilabs.droidwizardlib.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.prasilabs.droidwizardlib.R;
import com.prasilabs.droidwizardlib.core.views.CoreActivity;
import com.prasilabs.droidwizardlib.core.views.CoreFragment;

import java.util.List;


/**
 * Boilerplate code for fragment navigation in activity
 * @author Prasanna Anbazhagan <praslnx8@gmail.com>
 * @version 1.0
 */
public class FragmentNavigator
{
    public static void navigateToFragment(CoreActivity coreActivity, CoreFragment coreFragment, boolean addToBackStack, int viewId) {
        navigateToFragment(coreActivity, coreFragment, addToBackStack, viewId, false, false);
    }

    public static void navigateToFragment(CoreActivity coreActivity, CoreFragment coreFragment, boolean addToBackStack, int viewId, boolean isAnimate) {
        navigateToFragment(coreActivity, coreFragment, addToBackStack, viewId, false, isAnimate);
    }

    public static void navigateToFragment(CoreActivity coreActivity, CoreFragment coreFragment, boolean addToBackStack, int viewId, boolean isAdd, boolean isAnimate)
    {
        FragmentManager fragmentManager = coreActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(isAnimate)
        {
            fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left, R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        }

        if (!coreFragment.isAdded())
        {
            if (isAdd) {
                fragmentTransaction.add(viewId, coreFragment, coreFragment.getClass().getSimpleName());
            } else {
                fragmentTransaction.replace(viewId, coreFragment, coreFragment.getClass().getSimpleName());
            }
            if (addToBackStack)
            {
                fragmentTransaction.addToBackStack(null);
            }
        }
        else
        {
            fragmentTransaction.show(coreFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    public static void removeFragments(CoreActivity coreActivity)
    {
        FragmentManager fragmentManager = coreActivity.getSupportFragmentManager();
        List<Fragment> fragmentList = fragmentManager.getFragments();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        for(Fragment fragment : fragmentList)
        {
            fragmentTransaction.remove(fragment);
        }

        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    public static void removeFragments(CoreActivity coreActivity, int viewId) {
        coreActivity.getSupportFragmentManager().beginTransaction().remove(coreActivity.getSupportFragmentManager().findFragmentById(viewId)).commit();

    }

    public static void removeFragments(CoreFragment coreFragment, int viewId) {
        coreFragment.getChildFragmentManager().beginTransaction().remove(coreFragment.getChildFragmentManager().findFragmentById(viewId)).commit();

    }

    public static void placeFragment(Fragment parentFragment, CoreFragment coreFragment, int viewId)
    {
        FragmentManager fragmentManager = parentFragment.getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (!coreFragment.isAdded())
        {
            fragmentTransaction.replace(viewId, coreFragment, coreFragment.getClass().getSimpleName());
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    public static void placeFragment(CoreActivity coreActivity, CoreFragment coreFragment, int viewId)
    {
        FragmentManager fragmentManager = coreActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (!coreFragment.isAdded())
        {
            fragmentTransaction.replace(viewId, coreFragment, coreFragment.getClass().getSimpleName());
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

}
