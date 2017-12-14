package com.app.swishd.home.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseContainerFragment;
import com.app.swishd.home.search.fragment.SearchContainerFragment;
import com.app.swishd.home.profile.ProfileContainerFragment;
import com.app.swishd.home.send.fragment.SendContainerFragment;

public class HomeFragmentViewPagerAdapter extends FragmentStatePagerAdapter {

    private BaseContainerFragment[] mFragment;
    private Context mContext;

    public HomeFragmentViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mFragment = new BaseContainerFragment[3];
        mContext = context;

        mFragment[0] = new SearchContainerFragment();
        mFragment[1] = ProfileContainerFragment.newInstance();
        mFragment[2] = new SendContainerFragment();
    }


    @Override
    public Fragment getItem(int position) {
        return mFragment[position];
    }

    @Override
    public int getCount() {
        return mFragment.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getStringArray(R.array.title_viewpager)[position];
    }

    public BaseContainerFragment getContainer(int position){
        return mFragment[position];
    }
}
