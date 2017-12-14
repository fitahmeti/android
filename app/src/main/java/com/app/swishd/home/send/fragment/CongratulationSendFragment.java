package com.app.swishd.home.send.fragment;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgCongratulationsSendBinding;
import com.app.swishd.home.fragment.HomeFragment;

public class CongratulationSendFragment extends BaseFragment implements View.OnClickListener {

    private FrgCongratulationsSendBinding mBinding;

    @Override
    public int getLayout() {
        return R.layout.frg_congratulations_send;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        this.mBinding = (FrgCongratulationsSendBinding) binding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setOnClickListener();
    }

    private void setOnClickListener() {
        mBinding.frgCongratulationBackPressed.setOnClickListener(this);
        mBinding.frgCongratulationSendViewItem.setOnClickListener(this);
        mBinding.frgCongratulationSendShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frg_congratulation_back_pressed:
                ((SendContainerFragment) getParentFragment())
                        .replaceFragmentClearBackStack(new SendFragment(), true);
                break;
            case R.id.frg_congratulation_send_view_item:
                getParentFragment().getChildFragmentManager().popBackStack(SendFragment.class.getSimpleName(), 0);
                ((HomeFragment) getActivity().getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName())).setCurrentPage(1);
                break;
            case R.id.frg_congratulation_send_share:
                //TODO Navigate to share screen(Pending)
                break;
        }
    }
}
