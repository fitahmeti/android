package com.app.swishd.home.send.fragment;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.databinding.ViewDataBinding;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.app.swishd.R;
import com.app.swishd.databinding.FrgPickLocationBinding;
import com.app.swishd.databinding.PickLocationLayoutBinding;
import com.app.swishd.home.send.model.CreateItemModel;
import com.app.swishd.home.send.model.ResponseOfficeList;
import com.app.swishd.utility.DateUtil;
import com.google.android.gms.maps.MapView;

import java.util.Calendar;

public class DropLocationFragment extends BaseMapFragment
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private CreateItemModel mModel;
    private FrgPickLocationBinding mBinding;
    private ResponseOfficeList.DetailItem mAddress;
    private Calendar mSelectedDateAndTime;

    @Override
    public int getLayout() {
        return R.layout.frg_pick_location;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgPickLocationBinding) binding;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.frgPickLocationAddressPickOrDrop.setText(getString(R.string.drop_location));
        mBinding.frgPickLocationDateTime.setText(getString(R.string.deliver_by_flexible));
    }

    @Override
    public void onAddressSelected(Location location, String address) {
        if (address != null || !address.isEmpty()) {
            mBinding.frgPickLocationAddress.setVisibility(View.VISIBLE);
            mBinding.frgPickLocationAddress.setText(address);
            mModel.setsDropAddress(address);
            mModel.setsDropLatitude(location.getLatitude());
            mModel.setsDropLongitude(location.getLongitude());
        } else {
            mBinding.frgPickLocationAddress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onOfficeSelectedCallback(ResponseOfficeList.DetailItem item) {
        mAddress = item;
        mModel.setsDropAddress(item.getSAddressLine1());
        mModel.setsDropOfficeId(item.getId());
        if (item.getOLoc() != null) {
            mModel.setsDropLatitude(item.getOLoc().get(1));
            mModel.setsDropLongitude(item.getOLoc().get(0));
        }
        setAddress();
    }

    private void setAddress() {
        if (mAddress != null) {
            mBinding.frgPickLocationAddress.setVisibility(View.VISIBLE);
            StringBuffer strAddress = new StringBuffer("");
            strAddress.append((mAddress.getSAddressLine1().isEmpty()) ? "" : mAddress.getSAddressLine1());
            strAddress.append((mAddress.getSAddressLine2().isEmpty()) ? "" : " ," + mAddress.getSAddressLine2());
            strAddress.append((mAddress.getSCity().isEmpty()) ? "" : " ," + mAddress.getSCity());
            strAddress.append((mAddress.getSCountry().isEmpty()) ? "" : "," + mAddress.getSCountry());
            strAddress.append((mAddress.getSZipCode().isEmpty()) ? "" : " ," + mAddress.getSZipCode());
            mBinding.frgPickLocationAddress.setText(
                    strAddress.toString());
        } else
            mBinding.frgPickLocationAddress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((SendFragment) getParentFragment()).setProgress(3);
        mModel = ((SendFragment) getParentFragment()).getModel();
        setOnClickListener();
    }

    private void setOnClickListener() {
        mBinding.frgPickLocationTimeSelection.setOnClickListener(this);
        mBinding.frgPickLocationContinue.setOnClickListener(this);
    }

    @Override
    public MapView getMapView() {
        return mBinding.frgPickLocationMapView;
    }

    @Override
    public void onMapReadyCallback() {

    }

    @Override
    public PickLocationLayoutBinding getSelectAddressView() {
        return mBinding.frgPickLocation;
    }

    @Override
    public boolean hasPickLocationView() {
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.frg_pick_location_time_selection:
                showDatePickerDialog();
                break;

            case R.id.frg_pick_location_continue:
                if (isValid())
                    ((SendFragment) getParentFragment()).addFragment(new RecommendedPriceFragment(),
                            true);
                break;

            default:
                super.onClick(v);
        }
    }

    private void showDatePickerDialog() {
        Calendar minCalender = Calendar.getInstance();
        minCalender.setTimeInMillis(mModel.getmPickDate());
        DateUtil.showDatePickerDialog(getContext(), this, Calendar.getInstance(), minCalender);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mSelectedDateAndTime = Calendar.getInstance();
        mSelectedDateAndTime.set(Calendar.YEAR, year);
        mSelectedDateAndTime.set(Calendar.MONTH, month);
        mSelectedDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        showTimerPickerDialog();
    }

    public void showTimerPickerDialog() {
        DateUtil.showTimerPickerDialog(getContext(), 00, 00, this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mSelectedDateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mSelectedDateAndTime.set(Calendar.MINUTE, minute);
        String date = DateUtil.getSimpleDate(mSelectedDateAndTime);
        mModel.setsDropDateTime(DateUtil.getFormatedDateInUTC(date, DateUtil.SIMPLE_DATE, DateUtil.SERVER_DATE));

        mModel.setmDropDate(mSelectedDateAndTime.getTimeInMillis());

        mBinding.frgPickLocationDateTime.setText(
                getString(R.string.deliver_by) + DateUtil.SPACE +
                        DateUtil.getFormatedDate(mModel.getsDropDateTime(),
                                DateUtil.SERVER_DATE, DateUtil.DISPLAY_DATE)
        );

        Log.i("MYTAG",mModel.toString());
    }

    private boolean isValid() {
        if (mModel.getsDropAddress() == null || mModel.getsDropAddress().isEmpty()) {
            showSnackBar(mBinding, getString(R.string.e_please_select_drop_point));
            return false;
        } else if (mModel.getmPickDate() != 0 && mModel.getmDropDate() != 0 &&
                mModel.getmPickDate() > mModel.getmDropDate()) {
            showSnackBar(mBinding, getString(R.string.e_please_select_valid_date_and_time));
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mModel != null) {
            mModel.setsDropDateTime(null);
            mModel.setmDropDate(0);
        }
    }
}
