package com.prasilabs.droidwizardlib.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.prasilabs.droidwizardlib.R;
import com.prasilabs.droidwizardlib.core.views.CoreActivityView;
import com.prasilabs.droidwizardlib.core.views.CoreFragmentView;

import java.util.List;


/**
 * Boilerplate code for fragment navigation in activity
 * @author Prasanna Anbazhagan <praslnx8@gmail.com>
 * @version 1.0
 */
public class FragmentNavigator
{
    private FragmentNavigator(){}

    public static void navigateToFragment(CoreActivityView coreActivityView, CoreFragmentView coreFragmentView, boolean addToBackStack, int viewId) {
        navigateToFragment(coreActivityView, coreFragmentView, addToBackStack, viewId, false, false);
    }

    public static void navigateToFragment(CoreActivityView coreActivityView, CoreFragmentView coreFragmentView, boolean addToBackStack, int viewId, boolean isAnimate) {
        navigateToFragment(coreActivityView, coreFragmentView, addToBackStack, viewId, false, isAnimate);
    }

    public static void navigateToFragment(CoreActivityView coreActivityView, CoreFragmentView coreFragmentView, boolean addToBackStack, int viewId, boolean isAdd, boolean isAnimate)
    {
        FragmentManager fragmentManager = coreActivityView.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(isAnimate)
        {
            fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left, R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        }

        if (!coreFragmentView.isAdded())
        {
            if (isAdd) {
                fragmentTransaction.add(viewId, coreFragmentView, coreFragmentView.getClass().getSimpleName());
            } else {
                fragmentTransaction.replace(viewId, coreFragmentView, coreFragmentView.getClass().getSimpleName());
            }
            if (addToBackStack)
            {
                fragmentTransaction.addToBackStack(null);
            }
        }
        else
        {
            fragmentTransaction.show(coreFragmentView);
        }
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    public static void removeFragments(CoreActivityView coreActivityView)
    {
        FragmentManager fragmentManager = coreActivityView.getSupportFragmentManager();
        List<Fragment> fragmentList = fragmentManager.getFragments();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        for(Fragment fragment : fragmentList)
        {
            fragmentTransaction.remove(fragment);
        }

        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    public static void removeFragments(CoreActivityView coreActivityView, int viewId) {
        coreActivityView.getSupportFragmentManager().beginTransaction().remove(coreActivityView.getSupportFragmentManager().findFragmentById(viewId)).commit();

    }

    public static void removeFragments(CoreFragmentView coreFragmentView, int viewId) {
        coreFragmentView.getChildFragmentManager().beginTransaction().remove(coreFragmentView.getChildFragmentManager().findFragmentById(viewId)).commit();

    }

    public static void placeFragment(Fragment parentFragment, CoreFragmentView coreFragmentView, int viewId)
    {
        FragmentManager fragmentManager = parentFragment.getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (!coreFragmentView.isAdded())
        {
            fragmentTransaction.replace(viewId, coreFragmentView, coreFragmentView.getClass().getSimpleName());
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    public static void placeFragment(CoreActivityView coreActivityView, CoreFragmentView coreFragmentView, int viewId)
    {
        FragmentManager fragmentManager = coreActivityView.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (!coreFragmentView.isAdded())
        {
            fragmentTransaction.replace(viewId, coreFragmentView, coreFragmentView.getClass().getSimpleName());
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

}
