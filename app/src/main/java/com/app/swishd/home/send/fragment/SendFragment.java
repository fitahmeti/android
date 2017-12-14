package com.app.swishd.home.send.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseContainerFragment;
import com.app.swishd.databinding.FrgSendBinding;
import com.app.swishd.home.send.dialog.SortByDialog;
import com.app.swishd.home.send.interfaces.OnSortByDialogCallback;
import com.app.swishd.home.send.model.CreateItemModel;

public class SendFragment extends BaseContainerFragment
        implements View.OnClickListener {


    private FrgSendBinding mBinding;
    private Fragment mHorizontalProgressViewFragment;
    private CreateItemModel mModel;
    private OnSortByDialogCallback mOnSortByDialogClickListener;

    @Override
    public int getLayout() {
        return R.layout.frg_send;
    }

    @Override
    public int getContainerID() {
        return R.id.frg_send_container;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding = DataBindingUtil.bind(view);
    }

    @Override
    public Fragment getFirstFragment() {
        return new SendCreateItemFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addOnBackStackChangeList();
        setOnClickListener();
        mHorizontalProgressViewFragment =
                getChildFragmentManager().findFragmentById(R.id.frg_send_progress_view);
    }

    private void setOnClickListener() {
        mBinding.frgSendBackPressed.setOnClickListener(this);
        mBinding.frgSendSortBy.setOnClickListener(this);
    }

    private void addOnBackStackChangeList() {
        getChildFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getChildFragmentManager().getBackStackEntryCount() > 0) {
                    mBinding.frgSendBackPressed.setVisibility(View.VISIBLE);
                } else {
                    mBinding.frgSendBackPressed.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void setProgress(int position) {
        ((HorizontalProgressViewFragment) mHorizontalProgressViewFragment).setProgress(position);
    }

    public void addFragment(Fragment fragment, boolean addToBackStack,
                            boolean isRemovedProgressFragment) {
        if (isRemovedProgressFragment)
            ((BaseContainerFragment) getParentFragment()).addFragment(fragment, addToBackStack,
                    mHorizontalProgressViewFragment);
        else
            ((BaseContainerFragment) getParentFragment()).addFragment(fragment, addToBackStack);
    }

    public boolean popFragment() {
        boolean isPop = false;
        try {
            if (getChildFragmentManager().getBackStackEntryCount() > 0) {
                isPop = true;
                getChildFragmentManager().popBackStack();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isPop;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frg_send_back_pressed:
                getActivity().onBackPressed();
                break;
            case R.id.frg_send_sort_by:
                if (mOnSortByDialogClickListener != null)
                    SortByDialog.getInstance(mOnSortByDialogClickListener)
                            .show(getChildFragmentManager(), getString(R.string.sortby));
                break;
        }
    }

    public CreateItemModel getModel() {
        if (mModel == null)
            mModel = new CreateItemModel();
        return mModel;
    }

    public void setModel(CreateItemModel mModel) {
        this.mModel = mModel;
    }

    public void isVisibleSortByMenu(@SortByMenuVisibility int isVisible) {
        mBinding.frgSendSortBy.setVisibility(isVisible);
    }

    public void isVisibleProgressMenu(@SortByMenuVisibility int isVisible) {
        mBinding.frgSendProgressViewContainer.setVisibility(isVisible);
    }

    public void setSortByDialogClickListener(OnSortByDialogCallback listener) {
        this.mOnSortByDialogClickListener = listener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mOnSortByDialogClickListener = null;
    }

    @IntDef({View.INVISIBLE, View.VISIBLE})
    public @interface SortByMenuVisibility {
    }


}
