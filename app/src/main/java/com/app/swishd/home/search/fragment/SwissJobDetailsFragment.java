package com.app.swishd.home.search.fragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseLocationFragment;
import com.app.swishd.databinding.FrgSwissDetailsMainBinding;
import com.app.swishd.home.search.dialog.AcceptJobOfferCallback;
import com.app.swishd.home.search.dialog.AcceptJobOfferDialog;
import com.app.swishd.home.search.model.JobDetails.Data;
import com.app.swishd.home.search.model.JobDetails.ResponseJobDetails;
import com.app.swishd.retrofit.OnResponseCallback;
import com.app.swishd.retrofit.RequestCode;
import com.app.swishd.retrofit.call.Api;
import com.app.swishd.utility.DateUtil;
import com.app.swishd.utility.ImageUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.Calendar;

import static com.app.swishd.retrofit.RequestCode.REQUEST_ACCEPT_JOB;
import static com.app.swishd.retrofit.RequestCode.REQUEST_REMOVE_JOB;

public class SwissJobDetailsFragment extends BaseLocationFragment
        implements OnResponseCallback, OnMapReadyCallback,
        View.OnClickListener, DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, AcceptJobOfferCallback {

    private FrgSwissDetailsMainBinding mBinding;
    private String mJobId;
    private Calendar mDate;
    private String jobCreatorName;

    public static SwissJobDetailsFragment getInstance(String id) {
        SwissJobDetailsFragment mDetailsFragment = new SwissJobDetailsFragment();
        mDetailsFragment.mJobId = id;
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
        return R.layout.frg_swiss_details_main;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgSwissDetailsMainBinding) binding;
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
        mBinding.frgDetailsButtonBinding.frgSwissDetailsDone.setOnClickListener(this);
        mBinding.frgDetailsButtonBinding.frgSwissDetailsProposeDate.setOnClickListener(this);
        mBinding.frgDetailsButtonBinding.frgSwissDetailsProposeRemove.setOnClickListener(this);
    }

    public void doGetJobDetails() {
        showProgressDialog();
        getApiTask().doGetJobDetails(getContext(), mJobId, this);
    }

    @Override
    public void onResponseReceived(Object model, int requestCode) {
        dismissProgressDialog();
        if (model != null && requestCode == RequestCode.REQUEST_JOB_DETAILS) {
            mBinding.frgDetailsBinding.setJobDetails(((ResponseJobDetails) model).getData());
            setData(((ResponseJobDetails) model).getData());
            if (((ResponseJobDetails) model).getData().getSender()!=null)
                jobCreatorName = ((ResponseJobDetails) model).getData().getSender().getUsername();
        } else if (model != null && requestCode == REQUEST_ACCEPT_JOB) {
            AcceptJobOfferDialog.getInstance(jobCreatorName, this)
                    .show(getFragmentManager(), getString(R.string.accept_job));
        } else if (requestCode == REQUEST_REMOVE_JOB) {
            setRemoveButtonGone();
        }
    }

    private void setData(Data data) {
        ImageUtil.load(Api.BASE_URL_IMAGE + data.getJobSize().getSOriginalSizePicture(),
                mBinding.frgDetailsBinding.frgSwissDetailsSwissImage);

        if (data.isJobRequested())
            setRemoveButtonVisible();
        else
            setRemoveButtonGone();

        if (data.getQrcode() == null || data.getQrcode().size() <= 0) {
            mBinding.frgMapBinding.frgBarcodeImage.setVisibility(View.GONE);
            mBinding.frgMapBinding.frgBarcodeText.setVisibility(View.GONE);
            mBinding.frgMapBinding.frgMap.setVisibility(View.VISIBLE);
            setPickUpLocation(data.getOPickLoc().get(1), data.getOPickLoc().get(0));
        } else {
            mBinding.frgMapBinding.frgMap.setVisibility(View.GONE);
            mBinding.frgMapBinding.frgBarcodeImage.setVisibility(View.VISIBLE);
            mBinding.frgMapBinding.frgBarcodeText.setVisibility(View.VISIBLE);
            ImageUtil.load(Api.BASE_URL_IMAGE + data.getQrcode().get(0).getSScanImage(),
                    mBinding.frgMapBinding.frgBarcodeImage, R.drawable.ic_place_holder);
            mBinding.frgMapBinding.frgBarcodeText.setText(data.getQrcode().get(0).getSScanCode());
        }
    }

    @Override
    public void onResponseError(String message) {
        dismissProgressDialog();
        showSnackBar(mBinding, message);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        addPickUpMarker();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frg_swiss_details_done:
                doAcceptJob(null);
                break;
            case R.id.frg_swiss_details_propose_date:
                DateUtil.showDatePickerDialog(getContext(), this, Calendar.getInstance(), Calendar.getInstance());
                break;
            case R.id.frg_swiss_details_propose_remove:
                doRemoveJob();
                break;
        }
    }

    private void doRemoveJob() {
        showProgressDialog();
        getApiTask().doRemoveJob(getContext(), mJobId, this);
    }

    public void doAcceptJob(String date) {
        if (date != null && !isValid())
            return;
        showProgressDialog();
        getApiTask().doAcceptJob(getContext(), mJobId, date, this);
    }

    private boolean isValid() {
        if (mDate.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
            showSnackBar(mBinding, getString(R.string.e_please_select_valid_date_and_time));
            return false;
        }

        return true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mDate = Calendar.getInstance();
        mDate.set(Calendar.YEAR, year);
        mDate.set(Calendar.MONTH, month);
        mDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        DateUtil.showTimerPickerDialog(getContext(), mDate.get(Calendar.HOUR_OF_DAY),
                mDate.get(Calendar.MINUTE), this);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mDate.set(Calendar.MINUTE, minute);
        mDate.set(Calendar.SECOND, 00);
        doAcceptJob(DateUtil.getFormatedDateInUTC(
                DateUtil.getSimpleDate(mDate)
                , DateUtil.SIMPLE_DATE, DateUtil.SERVER_DATE
        ));
    }

    @Override
    public void onAcceptJobOk() {
        setRemoveButtonVisible();
    }

    private void setRemoveButtonVisible() {
        mBinding.frgDetailsButtonBinding.frgSwissDetailsDone.setVisibility(View.GONE);
        mBinding.frgDetailsButtonBinding.frgSwissDetailsProposeDate.setVisibility(View.GONE);
        mBinding.frgDetailsButtonBinding.frgSwissDetailsProposeRemove.setVisibility(View.VISIBLE);
    }

    private void setRemoveButtonGone() {
        mBinding.frgDetailsButtonBinding.frgSwissDetailsDone.setVisibility(View.VISIBLE);
        mBinding.frgDetailsButtonBinding.frgSwissDetailsProposeDate.setVisibility(View.VISIBLE);
        mBinding.frgDetailsButtonBinding.frgSwissDetailsProposeRemove.setVisibility(View.GONE);
    }
}
