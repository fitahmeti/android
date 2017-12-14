package com.app.swishd.home.fragment;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseContainerFragment;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgHomeFragmentBinding;
import com.app.swishd.home.adapter.HomeFragmentViewPagerAdapter;
import com.app.swishd.home.interfaces.OnBottomMenuCallback;

public class HomeFragment extends BaseFragment
        implements OnBottomMenuCallback {

    private FrgHomeFragmentBinding mBinding;
    private HomeFragmentViewPagerAdapter pagerAdapter;

    @Override
    public int getLayout() {
        return R.layout.frg_home_fragment;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgHomeFragmentBinding) binding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setAdapter();
        mBinding.frgHomeBottomMenu.setClick(this);
    }

    private void setAdapter() {
        pagerAdapter = new HomeFragmentViewPagerAdapter(getChildFragmentManager(), getContext());
        mBinding.frgHomeViewPager.setOffscreenPageLimit(3);
        mBinding.frgHomeViewPager.setAdapter(pagerAdapter);
    }


    public void onSearchClick(View view) {
        mBinding.frgHomeViewPager.setCurrentItem(0);
    }

    public void onMeClick(View view) {
        mBinding.frgHomeViewPager.setCurrentItem(1);
    }

    public void onSenderClick(View view) {
        mBinding.frgHomeViewPager.setCurrentItem(2);
    }


    public BaseContainerFragment getCurrentContainer() {
        return pagerAdapter.getContainer(mBinding.frgHomeViewPager.getCurrentItem());
    }

    public void setCurrentPage(int position) {
        mBinding.frgHomeViewPager.setCurrentItem(position, true);
    }
}
