package com.app.swishd.home.profile.jobs;


import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseContainerFragment;
import com.app.swishd.baseutil.BaseLocationFragment;
import com.app.swishd.databinding.FrgActiveJobDetailsSwishrBinding;
import com.app.swishd.home.profile.ProfileContainerFragment;
import com.app.swishd.home.profile.activity.SenderActivityFragment;
import com.app.swishd.home.profile.activity.SwishrActivityFragment;
import com.app.swishd.home.profile.model.UserProfile;
import com.app.swishd.home.search.model.JobDetails.Data;
import com.app.swishd.home.search.model.JobDetails.QRCode;
import com.app.swishd.home.search.model.JobDetails.ResponseJobDetails;
import com.app.swishd.retrofit.OnResponseCallback;
import com.app.swishd.retrofit.RequestCode;
import com.app.swishd.retrofit.call.Api;
import com.app.swishd.utility.Const;
import com.app.swishd.utility.EnumPreference;
import com.app.swishd.utility.ImageUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.List;
import java.util.Locale;

public class ActiveJobSwisHrFragment
        extends BaseLocationFragment implements OnResponseCallback, View.OnClickListener, OnMapReadyCallback {

    private FrgActiveJobDetailsSwishrBinding mBinding;
    private String mJobId;
    private boolean isSwisher;
    private boolean isActive;
    private String mUrl, mCode;
    private Data mJobItem;

    public static ActiveJobSwisHrFragment getInstance(String id, boolean isSwisher, boolean isActive) {
        ActiveJobSwisHrFragment mDetailsFragment = new ActiveJobSwisHrFragment();
        mDetailsFragment.isSwisher = isSwisher;
        mDetailsFragment.mJobId = id;
        mDetailsFragment.isActive = isActive;
        return mDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mJobId == null) {
            getActivity().onBackPressed();
        }
    }

    @Override
    public int getLayout() {
        return R.layout.frg_active_job_details_swishr;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgActiveJobDetailsSwishrBinding) binding;
    }

    @Override
    public MapView getMapView() {
        return mBinding.frgMapBinding.frgMap;
    }

    @Override
    public OnMapReadyCallback getMapReadyCallback() {
        return this;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mJobId != null)
            doGetJobDetails();

        setOnClickListener();
    }

    private void setOnClickListener() {
        mBinding.frgActiveJobViewActivityBottomLayout.frgActiveJobViewActivity.setOnClickListener(this);
        mBinding.frgMapBinding.frgBarcodeImage.setOnClickListener(this);
        mBinding.btnBack.setOnClickListener(this);
    }

    public void doGetJobDetails() {
        showProgressDialog();
        getApiTask().doGetJobDetails(getContext(), mJobId, this);
    }

    @Override
    public void onResponseReceived(final Object model, int requestCode) {
        dismissProgressDialog();
        if (model != null && requestCode == RequestCode.REQUEST_JOB_DETAILS) {
            mBinding.frgActiveJobViewDetailsLayout.setJobDetails(((ResponseJobDetails) model).getData());
            if (mBinding != null) {
                mBinding.addOnRebindCallback(new OnRebindCallback() {
                    @Override
                    public void onBound(ViewDataBinding binding) {
                        super.onBound(binding);
                        setData(((ResponseJobDetails) model).getData());
                    }
                });
            }
        } else if (requestCode == RequestCode.REQUEST_REMOVE_JOB) {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onResponseError(String message) {
        dismissProgressDialog();
        showSnackBar(mBinding, message);
    }

    private void setData(Data data) {
        if (data.getEJobStatus() != null) {
            if (data.getEJobStatus().equals(Const.JOB_STATUS.active.toString()))
                isActive = true;
            else isActive = false;
        }
        if (data.getSender() != null && data.getSender().getId() != null) {
            if (data.getSender().getId().equals(UserProfile.fromString(getPrefs().getStringData(EnumPreference.MY_PROFILE)).get_id()))
                isSwisher = false;
            else isActive = true;
        }

        ImageUtil.load(Api.BASE_URL_IMAGE + data.getJobSize().getSOriginalSizePicture(),
                       mBinding.frgActiveJobViewDetailsLayout.frgSwissDetailsSwissImage);

        if (!isActive && data.getJobRequestCount() == 0) {
            mBinding.frgActiveJobViewActivityBottomLayout.frgActiveJobMain.setVisibility(View.VISIBLE);
            mBinding.frgActiveJobViewActivityBottomLayout.frgActiveJobNoDataFound.setVisibility(View.VISIBLE);
            mBinding.frgActiveJobViewActivityBottomLayout.frgActiveJobMainDataView.setVisibility(View.GONE);
            mBinding.frgActiveJobViewActivityBottomLayout.frgActiveJobViewActivity.setVisibility(View.GONE);
        } else if (!isActive && data.getJobRequestCount() > 0) {
            mBinding.frgActiveJobViewActivityBottomLayout.frgActiveJobSenderImage.setImageResource(R.drawable.ic_logo_blue);
            setTextToHelper(getTextFormatter(R.string.swishr_have_offered_to_swish_your_item, String.valueOf(data.getJobRequestCount())));
            mBinding.frgActiveJobViewActivityBottomLayout.frgActiveJobViewActivity.setText(getString(R.string.view_swishrs));
        } else if (!isSwisher && data.getSwishr() == null) {
            mBinding.frgActiveJobViewActivityBottomLayout.frgActiveJobMain.setVisibility(View.GONE);
        } else if (!isSwisher && data.getSwishr() != null) {
            ImageUtil.load(Api.BASE_URL_IMAGE + data.getSwishr().getProfileImage(), mBinding.frgActiveJobViewActivityBottomLayout.frgActiveJobSenderImage);
            setTextToHelper(getTextFormatter(R.string.your_swisher, data.getSwishr().getUsername()));
        } else if (data.getSender() != null) {
            ImageUtil.load(Api.BASE_URL_IMAGE + data.getSender().getProfileImage(), mBinding.frgActiveJobViewActivityBottomLayout.frgActiveJobSenderImage);
            setTextToHelper(getTextFormatter(R.string.you_are_helping, data.getSender().getUsername()));
        }

        if ((data.getEJobStatus() != null && data.getEJobStatus().equals("active")) || (data.getSender() != null && data.getSender().getId() != null && data.getSender().getId().equals(UserProfile.fromString(getPrefs().getStringData(EnumPreference.MY_PROFILE)).get_id()))) {
            String upperString = getString(R.string.status).substring(0, 1).toUpperCase() + getString(R.string.status).substring(1) + ":";
            mBinding.frgActiveJobViewDetailsLayout.frgSwissDetailsSenderFieldName.setText(upperString);
            mBinding.frgActiveJobViewDetailsLayout.frgSwissDetailsSwissSenderName.setText(data.getEJobStatus());
        } else {
            String upperString = getString(R.string.sender) + ":";
            mBinding.frgActiveJobViewDetailsLayout.frgSwissDetailsSenderFieldName.setText(upperString);
            if (data.getSender() != null)
                mBinding.frgActiveJobViewDetailsLayout.frgSwissDetailsSwissSenderName.setText(data.getSender().getUsername());
        }
        setQrCodeAndMap(data);

        //TODO: #11343 - replace with bellow ios code
//        if job.status == JobStatus.active{
//            if let _ = job.displayCode{
//                cellTypes.append(.qrCode)
//            }
//        }else if job.status == JobStatus.inProgress{
//            if let _ = job.displayCode{
//                cellTypes.append(.qrCode)
//            }
//        }else{
//            cellTypes.append(.map)
//        }

//        cellTypes.append(.size)
//        cellTypes.append(.timeCell)
//        cellTypes.append(.address)
//        if job.status != JobStatus.pending {
//            if job.status != .completed{
//                cellTypes.append(.userActivity)
//                cellTypes.append(.activity)
//            }
//        }else{
//            if job.isSentByMe{
//                if job.jobRequestCount > 0{
//                    cellTypes.append(.userReqCell)
//                    cellTypes.append(.viewSwishr)
//                }else{
//                    cellTypes.append(.noSwishr)
//                }
//            }else{
//                if job.isJobRequestSent{
//                    cellTypes.append(.removeOffer)
//                }else{
//                    cellTypes.append(.addOffer)
//                }
//            }
//        }

    }

    private String getTextFormatter(@StringRes int id, String name) {
        return String.format(Locale.getDefault(), getString(id),
                             name);
    }

    @NonNull
    private void setTextToHelper(String text) {
        mBinding.frgActiveJobViewActivityBottomLayout.frgActiveJobHelpingText.setText(text);
    }

    private void setQrCodeAndMap(Data data) {
        mJobItem = data;
        if (data.getQrcode() == null || data.getQrcode().size() <= 0) {
            mBinding.frgMapBinding.frgBarcodeImage.setVisibility(View.GONE);
            mBinding.frgMapBinding.frgBarcodeText.setVisibility(View.GONE);
            mBinding.frgMapBinding.frgMap.setVisibility(View.VISIBLE);
            setPickUpLocation(data.getOPickLoc().get(1), data.getOPickLoc().get(0));
            if (data.getODropLoc() != null)
                setDropLocation(data.getODropLoc().get(1), data.getODropLoc().get(0));
        } else if (data.getEJobStatus().equals(Const.JOB_STATUS.active.toString())) {
            mBinding.frgMapBinding.frgMap.setVisibility(View.GONE);
            if (data.getsPickupBy() == null || data.getsPickupBy().getSUserId() == null || data.getsRecievedBy() == null || data.getsRecievedBy().getSUserId() == null || !data.getsPickupBy().getSUserId().equals(data.getsRecievedBy().getSUserId())) {
                mBinding.frgMapBinding.frgBarcodeImage.setVisibility(View.GONE);
                mBinding.frgMapBinding.frgBarcodeText.setVisibility(View.GONE);
                mBinding.frgMapBinding.frgLayoutMain.setVisibility(View.GONE);
            } else {
                mBinding.frgMapBinding.frgBarcodeImage.setVisibility(View.VISIBLE);
                mBinding.frgMapBinding.frgBarcodeText.setVisibility(View.VISIBLE);

                if (!isSwisher) {
                    findQrCode(data.getQrcode(), Const.QR_CODE.pickup.toString());
                } else {
                    findQrCode(data.getQrcode(), Const.QR_CODE.pickup_office.toString());
                }
            }
        }
    }

    private void findQrCode(List<QRCode> qrCodeList, String text) {
        for (int i = 0; i < qrCodeList.size(); i++) {
            if (qrCodeList.get(i).getSType().equals(text)) {
                setQrImageAndText(qrCodeList.get(i).getSScanImage(),
                                  qrCodeList.get(i).getSScanCode());
                break;
            } else if (i == (qrCodeList.size() - 1))
                mBinding.frgMapBinding.frgLayoutMain.setVisibility(View.GONE);
        }
    }

    private void setQrImageAndText(String url, String code) {
        mUrl = url;
        mCode = code;
        ImageUtil.load(Api.BASE_URL_IMAGE + url,
                       mBinding.frgMapBinding.frgBarcodeImage, R.drawable.ic_place_holder);
        mBinding.frgMapBinding.frgBarcodeText.setText(code);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frg_active_job_view_activity:
                if (isActive) {
                    if (isSwisher)
                        ((BaseContainerFragment) getParentFragment())
                                .addFragment(new SwishrActivityFragment(mJobId), true);
                    else
                        ((BaseContainerFragment) getParentFragment())
                                .addFragment(new SenderActivityFragment(mJobId), true);
                    break;
                } else {
                    ((ProfileContainerFragment) getParentFragment())
                            .addFragment(SelectSwishrFragment.getInstance(mJobItem), true);
                    break;
                }
            case R.id.btn_back:
                getActivity().onBackPressed();
                break;
            case R.id.frg_barcode_image:
                if (mUrl != null || mCode != null) {
                    ((ProfileContainerFragment) getParentFragment()).addFragment(
                            QrDetailsFragment.getInstance(mUrl, mCode), true
                    );
                }
                break;
        }
    }

    private void doRemoveOffer() {
        getApiTask().doRemoveJob(getContext(), mJobId, this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        addPickUpMarker();
        addDropMarker();
    }

}