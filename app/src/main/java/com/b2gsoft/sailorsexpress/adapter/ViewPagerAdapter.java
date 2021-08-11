package com.b2gsoft.sailorsexpress.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.b2gsoft.sailorsexpress.fragment.AddressFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        if(position == 0) {

            fragment = new AddressFragment();
        }
        else if(position == 1) {

            //fragment = new PaymentMethodFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {

        return 2;
    }
}

