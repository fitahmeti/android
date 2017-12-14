package com.app.swishd.home.profile;

import android.support.v4.app.Fragment;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseContainerFragment;

public class ProfileContainerFragment extends BaseContainerFragment {

    private static ProfileContainerFragment fragment;

    public static ProfileContainerFragment getInstance() {
        if (fragment == null)
            fragment = new ProfileContainerFragment();
        return fragment;
    }

    public static ProfileContainerFragment newInstance() {
        fragment = new ProfileContainerFragment();
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.frg_profile_container;
    }

    @Override
    public int getContainerID() {
        return R.id.container_profile;
    }

    @Override
    public Fragment getFirstFragment() {
        return ProfileFragment.newInstance();
    }
}
