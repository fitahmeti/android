package com.app.swishd.home.send.fragment;


import android.databinding.ViewDataBinding;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.HorizontalProgressViewBinding;

public class HorizontalProgressViewFragment extends BaseFragment {

    private HorizontalProgressViewBinding mBinding;

    @Override
    public int getLayout() {
        return R.layout.horizontal_progress_view;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (HorizontalProgressViewBinding) binding;
    }

    public void setProgress(int position) {
        mBinding.horizontalProgressView.setProgress(position);
    }
}
