package com.bignerdranch.android.triplogger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

/**
 * Created by reece on 27/10/2016.
 */

public class TripPagerActivity extends FragmentActivity {
    public static final String EXTRA_TRIP_ID =
            "com.bignerdranch.android.triplogger.trip_id";

    private ViewPager mViewPager;
    private List<Trip> mTrips;

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, TripPagerActivity.class);
        intent.putExtra(EXTRA_TRIP_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_pager);

        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_TRIP_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_trip_pager_view_pager);

        mTrips = TripLab.get(this).getTrips();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Trip trip = mTrips.get(position);
                return TripFragment.newInstance(trip.getId());
            }

            @Override
            public int getCount() {
                return mTrips.size();
            }
        });

        for (int i = 0; i < mTrips.size(); i++) {
            if (mTrips.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
