package com.funsooyenuga.javadevs.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by FAB THE GREAT on 13/09/2017.
 */

public class ActivityUtils {

    /**
     * Add a fragment to an activity
     *
     * @param fm support fragment manager (getSupportFragmentManager() is fine)
     * @param resourceId the id of the layout where the fragment should be
     * @param fragment an instance of the fragment to be added
     * @param tag optional tag to find the fragment
     */
    public static void hostFragment(FragmentManager fm, int resourceId, Fragment fragment, String tag) {
        if (fragment != null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(resourceId, fragment, tag)
                    .commit();
        }
    }
}
