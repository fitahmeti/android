package com.app.swishd.home.profile.wallet;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgWalletBinding;
import com.app.swishd.home.profile.model.UserProfile;
import com.app.swishd.home.profile.wallet.model.BankAccount;
import com.app.swishd.utility.EnumPreference;

public class WalletFragment extends BaseFragment {
    private FrgWalletBinding mBinding;
    private UserProfile userData;
    private WalletAdapter walletAdapter;

    @Override
    public int getLayout() {
        return R.layout.frg_wallet;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgWalletBinding) binding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBinding.swipeRefreshLayout.setProgressViewOffset(false, getResources().getDimensionPixelSize(R.dimen._42sdp), getResources().getDimensionPixelSize(R.dimen._42sdp) + mBinding.swipeRefreshLayout.getProgressViewEndOffset());
        mBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBinding.swipeRefreshLayout.setRefreshing(false);
                walletAdapter.fragments[0].onRefresh();
                walletAdapter.fragments[1].onRefresh();
            }
        });

        mBinding.appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mBinding.swipeRefreshLayout.setEnabled(verticalOffset == 0);
            }
        });

        mBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragment().getChildFragmentManager().popBackStack();
            }
        });

        userData = UserProfile.fromString(getPrefs().getStringData(EnumPreference.MY_PROFILE));
        mBinding.tvBalance.setText("£" + userData.getIntWalletAmount());

        walletAdapter = new WalletAdapter(getChildFragmentManager());
        mBinding.viewPager.setAdapter(walletAdapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        mBinding.tabLayout.getTabAt(0).setText("PAYMENT DETAILS");
        mBinding.tabLayout.getTabAt(1).setText("TRANSACTION HISTORY");
    }

    private class WalletAdapter extends FragmentStatePagerAdapter {
        private BaseWalletPagerFragment[] fragments = new BaseWalletPagerFragment[2];

        public WalletAdapter(FragmentManager fm) {
            super(fm);
            fragments[0] = new WalletPaymentDetailsFragment();
            fragments[1] = new WalletHistoryFragment();
        }

        public BaseWalletPagerFragment getCurrentFragment() {
            return fragments[mBinding.viewPager.getCurrentItem()];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public void onBankAccountAdded(BankAccount bankAccount) {
        ((WalletPaymentDetailsFragment) walletAdapter.fragments[0]).onBankAccountAdded(bankAccount);
    }
}