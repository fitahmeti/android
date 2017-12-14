package com.app.swishd.home.send.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgConfirmSwishBinding;
import com.app.swishd.home.send.model.CreateItemModel;
import com.app.swishd.retrofit.OnResponseCallback;
import com.app.swishd.utility.DateUtil;

import io.reactivex.disposables.Disposable;

public class ConfirmSwishFragment extends
        BaseFragment implements View.OnClickListener, OnResponseCallback {

    private FrgConfirmSwishBinding mBinding;
    private CreateItemModel mModel;
    private Disposable mDisposable;

    @Override
    public int getLayout() {
        return R.layout.frg_confirm_swish;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgConfirmSwishBinding) binding;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((SendFragment) getParentFragment()).isVisibleProgressMenu(View.INVISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mModel = ((SendFragment) getParentFragment()).getModel();
        mBinding.setItem(mModel);
        setOnClickListener();

        if (mModel.isDateTimeSelected(mModel.getsDropDateTime())) {
            mBinding.frgConfirmSwishDropDate.setText(getString(R.string.flexible));
        } else {
            mBinding.frgConfirmSwishDropDate.setText(
                    DateUtil.getFormatedDate(mModel.getsDropDateTime(),
                            DateUtil.SERVER_DATE,
                            DateUtil.DISPLAY_DATE)
            );
        }

        if (mModel.isDateTimeSelected(mModel.getsPickDateTime())) {
            mBinding.frgConfirmSwishPickUpDate.setText(getString(R.string.flexible));
        } else {
            mBinding.frgConfirmSwishPickUpDate.setText(
                    DateUtil.getFormatedDate(mModel.getsPickDateTime(),
                            DateUtil.SERVER_DATE,
                            DateUtil.DISPLAY_DATE)
            );
        }
    }

    private void setOnClickListener() {
        mBinding.frgConfirmSwish.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((SendFragment) getParentFragment()).isVisibleProgressMenu(View.VISIBLE);
        mBinding = null;
        if (mDisposable != null)
            mDisposable.dispose();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frg_confirm_swish:
                doAddJob();
                break;
        }
    }

    private void doAddJob() {
        showProgressDialog();
        mDisposable = getApiTask().doAddJob(getContext(), mModel, this);
    }

    @Override
    public void onResponseReceived(Object model, int requestCode) {
        dismissProgressDialog();
        ((SendFragment) getParentFragment()).addFragment(
                new CongratulationSendFragment(), true, true);
    }

    @Override
    public void onResponseError(String message) {
        dismissProgressDialog();
        showSnackBar(mBinding, message);
    }

}
