package com.test.carslist.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.test.carslist.R;
import com.test.carslist.fragments.CarsListFragment;
import com.test.carslist.fragments.CarsMapFragment;

public class TabAdapter extends FragmentPagerAdapter {

    public TabAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new CarsListFragment();
                return fragment;
            case 1:
                fragment = new CarsMapFragment();
                return fragment;
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "List";
        } else if (position == 1) {
            title = "Map";
        }
        return title;
    }
}
