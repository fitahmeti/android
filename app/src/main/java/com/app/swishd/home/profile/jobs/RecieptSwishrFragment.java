package com.app.swishd.home.profile.jobs;


import android.animation.Animator;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgReceiptSwishrSelectedBinding;
import com.app.swishd.home.profile.model.AddReceivedModel;
import com.app.swishd.home.profile.model.RecieptModel;
import com.app.swishd.home.profile.model.UserProfile;
import com.app.swishd.utility.EnumPreference;
import com.app.swishd.utility.MyPrefs;

public class RecieptSwishrFragment extends BaseFragment implements View.OnClickListener {

    private FrgReceiptSwishrSelectedBinding mBinding;
    private RecieptModel mModel;
    private String mJobId;
    private String mSwisHrId;

    public static RecieptSwishrFragment getInstance(RecieptModel model, String jobId,
                                                    String swishrId) {
        RecieptSwishrFragment fragment = new RecieptSwishrFragment();
        fragment.mModel = model;
        fragment.mJobId = jobId;
        fragment.mSwisHrId = swishrId;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mModel == null)
            getActivity().onBackPressed();
    }

    @Override
    public int getLayout() {
        return R.layout.frg_receipt_swishr_selected;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgReceiptSwishrSelectedBinding) binding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setText();
        setOnRadioButtonClick();
        setOnClickListener();
    }

    private void setOnClickListener() {
        mBinding.recipentsAddDetailsDone.setOnClickListener(this);
    }

    private void setOnRadioButtonClick() {
        mBinding.frgRecipentsDetails.frgRecipentsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.frg_recipents_radio_me) {
                    viewAnimate(mBinding.frgRecipentsAddDetails, false, new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mBinding.frgRecipentsAddDetails.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                } else {
                    mBinding.frgRecipentsAddDetails.setVisibility(View.VISIBLE);
                    viewAnimate(mBinding.frgRecipentsAddDetails, true, null);
                }
            }
        });

        mBinding.frgRecipentsDeliveryDetails.frgRecipentsDeliveryRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.frg_recipents_delivery_radio_me) {
                    viewAnimate(mBinding.frgRecipentsDeliveryAddDetails, false, new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mBinding.frgRecipentsDeliveryAddDetails.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                } else {
                    mBinding.frgRecipentsDeliveryAddDetails.setVisibility(View.VISIBLE);
                    viewAnimate(mBinding.frgRecipentsDeliveryAddDetails, true, null);
                }
            }
        });
    }

    private void viewAnimate(final View view, final boolean isExpand,
                             final Animator.AnimatorListener listener) {
        view.post(new Runnable() {
            @Override
            public void run() {
                if (isExpand) {
                    view.animate().translationY(
                            -(view.getMeasuredHeight())).setDuration(0).setListener(listener).start();
                    view.animate().translationY(0).setDuration(300).setListener(listener).start();
                } else {
                    view.animate().translationY(-(view.getMeasuredHeight())).setDuration(300).setListener(listener).start();
                }
            }
        });
    }

    private void setText() {
        mBinding.frgRecipentsDetails.frgRecipentsFrom.setText(mModel.getFrom());
        mBinding.frgRecipentsDetails.frgRecipentsCollectFrom.setText(mModel.getCollectFrom());

        mBinding.frgRecipentsDeliveryDetails.frgRecipentsDeliveryTo.setText(mModel.getTo());
        mBinding.frgRecipentsDeliveryDetails.frgRecipentsDeliveryDeliveredBy.setText(mModel.getDeliveredBy());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recipents_add_details_done:
                setModel();
                break;
        }
    }

    private void setModel() {
        AddReceivedModel addReceivedModel = new AddReceivedModel();

        if (mBinding.frgRecipentsDetails.frgRecipentsRadioMe.isChecked()) {
            addReceivedModel.setsPickUserId(
                    UserProfile.fromString(MyPrefs.getInstance().getStringData(EnumPreference.MY_PROFILE)).get_id());
        } else {
            if (!getString(mBinding.frgRecipentsDetailsData.recipentsUsername).isEmpty()) {
                if (getString(mBinding.frgRecipentsDetailsData.recipentsSwishrEmail).isEmpty()) {
                    showSnackBar(mBinding, getString(R.string.e_please_enter_swishr_email_address));
                    return;
                } else {
                    addReceivedModel.setsPickSwishrUserName(getString(mBinding.frgRecipentsDetailsData.recipentsUsername));
                    addReceivedModel.setsPickEmail(getString(mBinding.frgRecipentsDetailsData.recipentsSwishrEmail));
                }
            } else if (!mBinding.frgRecipentsDetailsData.recipentsEmail.getText().toString().trim().isEmpty()) {

            } else {
                showSnackBar(mBinding, getString(R.string.e_please_provide_pickup_details));
                return;
            }
        }

        if (mBinding.frgRecipentsDeliveryDetails.frgRecipentsDeliveryRadioMe.isChecked()) {
            addReceivedModel.setsRecievedUserId(
                    UserProfile.fromString(MyPrefs.getInstance().getStringData(EnumPreference.MY_PROFILE)).get_id());
        }

        addReceivedModel.setsUserId(mSwisHrId);
        addReceivedModel.setsJobId(mJobId);

        doAcceptRequest(addReceivedModel);
    }

    private void doAcceptRequest(AddReceivedModel addReceivedModel) {
        Log.i("MYTAG", addReceivedModel.toString());
    }
}
