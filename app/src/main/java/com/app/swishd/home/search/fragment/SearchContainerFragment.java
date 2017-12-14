package com.app.swishd.home.search.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseContainerFragment;

public class SearchContainerFragment extends BaseContainerFragment {


    @Override
    public int getLayout() {
        return R.layout.frg_search_container;
    }

    @Override
    public int getContainerID() {
        return R.id.container_search;
    }

    @Override
    public Fragment getFirstFragment() {
        return new SearchFragment();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
