package com.bignerdranch.android.triplogger;

import android.support.v4.app.Fragment;

/**
 * Created by reece on 25/10/2016.
 */

public class TripListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new TripListFragment();
    }
}
