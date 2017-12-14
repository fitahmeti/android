package com.app.swishd.home.search.fragment;


import android.app.DatePickerDialog;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgAdvancedSearchBinding;
import com.app.swishd.home.search.adapter.WeekDayAdapter;
import com.app.swishd.home.search.interfaces.OnAdvancedSearchCallback;
import com.app.swishd.home.search.model.FindJobModel;
import com.app.swishd.utility.Const;
import com.app.swishd.utility.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;

import static com.app.swishd.utility.Utility.LOG;

public class AdvancedSearchFragment extends BaseFragment
        implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private final String DISPLAY_DATE = "EEE dd MMM ";

    private FrgAdvancedSearchBinding mBinding;
    private WeekDayAdapter mAdapter;
    private FindJobModel mModel;
    private OnAdvancedSearchCallback mCallback;
    private boolean isEdited, isEveryDaySelected;
    private Calendar mSelectedDate;

    public static AdvancedSearchFragment getInstance(OnAdvancedSearchCallback callback,
                                                     FindJobModel model) {
        AdvancedSearchFragment mFragment = new AdvancedSearchFragment();
        mFragment.mCallback = callback;
        mFragment.mModel = model;
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mCallback == null) {
            toast("Please use static getInstance() method to create an object");
            getActivity().onBackPressed();
        }
    }

    @Override
    public int getLayout() {
        return R.layout.frg_advanced_search;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgAdvancedSearchBinding) binding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mModel == null)
            mModel = new FindJobModel();
        mSelectedDate = Calendar.getInstance();
        setJourneySelectionOff();
        setOnClickListener();
        setWeekDayAdapter();
        setOnSwitchCheckedChangeListener();
        setDefaultData();
    }

    private void setDefaultData() {
        if (mModel.getAnytime().toString().equals(Const.YES_NO.yes.toString()))
            mBinding.frgAdvancedSearchAnytime.performClick();
        else if (mModel.getSpecific_date() != null &&
                !mModel.getSpecific_date().isEmpty()) {
            setSelected(mBinding.frgAdvancedSearchSpecificDate);

            mSelectedDate = DateUtil.getCalender(mModel.getSpecific_date(),
                    DateUtil.SERVER_DATE);

            mBinding.frgAdvancedSearchSpecificDate.setText(
                    DateUtil.getFormatedDate(mModel.getSpecific_date(),
                            DateUtil.SERVER_DATE, DISPLAY_DATE)
            );
        } else if (mModel.getEveryday() != null && mModel.getEveryday().size() > 0) {
            mAdapter.addEveryDayValue(mModel.getEveryday());
            mBinding.frgAdvancedSearchEveryday.performClick();
        }
    }

    private void setOnSwitchCheckedChangeListener() {
        mBinding.frgAdvancedSearchSwissPoint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mModel.setSwishdoffice(
                        (isChecked) ? Const.YES_NO.yes.toString() : Const.YES_NO.no.toString());
            }
        });
    }

    private void setOnClickListener() {
        mBinding.frgAdvancedSearchAnytime.setOnClickListener(this);
        mBinding.frgAdvancedSearchEveryday.setOnClickListener(this);
        mBinding.frgAdvancedSearchSpecificDate.setOnClickListener(this);
        mBinding.frgAdvancedSearchDone.setOnClickListener(this);
    }

    public void setJourneySelectionOff() {
        mBinding.frgAdvancedSearchAnytime.setActivated(false);
        mBinding.frgAdvancedSearchEveryday.setActivated(false);
        mBinding.frgAdvancedSearchSpecificDate.setActivated(false);

        mBinding.frgAdvancedSearchAnytime.setTextColor(getContext().getResources()
                .getColor(R.color.gray_dark));
        mBinding.frgAdvancedSearchEveryday.setTextColor(getContext().getResources()
                .getColor(R.color.gray_dark));
        mBinding.frgAdvancedSearchSpecificDate.setTextColor(getContext().getResources()
                .getColor(R.color.gray_dark));
        mBinding.frgAdvancedWeekList.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        setJourneySelectionOff();
        switch (v.getId()) {
            case R.id.frg_advanced_search_anytime:
                isEveryDaySelected = false;
                isEdited = true;
                setSelected(mBinding.frgAdvancedSearchAnytime);
                mModel.setEveryday(null);
                mModel.setSpecific_date(null);
                mModel.setAnytime(Const.YES_NO.yes.toString());
                mSelectedDate = Calendar.getInstance();
                break;
            case R.id.frg_advanced_search_everyday:
                isEveryDaySelected = true;
                isEdited = true;
                setSelected(mBinding.frgAdvancedSearchEveryday);
                mBinding.frgAdvancedWeekList.setVisibility(View.VISIBLE);
                mModel.setAnytime(Const.YES_NO.no.toString());
                mModel.setSpecific_date(null);
                mSelectedDate = Calendar.getInstance();
                break;
            case R.id.frg_advanced_search_specific_date:
                DateUtil.showDatePickerDialog(getContext(), this, mSelectedDate, Calendar.getInstance());
                break;
            case R.id.frg_advanced_search_done:
                sendResult();
                break;
        }
    }

    private void sendResult() {
        if (mCallback != null && isEdited) {
            if (isEveryDaySelected) {
                mModel.setEveryday(null);
                ArrayList<Integer> selectedValues = mAdapter.getSelectedItem();
                for (int i = 0; i < selectedValues.size(); i++) {
                    mModel.getEveryday().add(Const.WEEK_DAY_NAME.getWeekName(selectedValues.get(i)));
                }
                mCallback.onAdvancedSearchCallback(mModel);
            } else {
                mCallback.onAdvancedSearchCallback(mModel);
            }
        }

        LOG(getClass().toString(), mModel.toString());
        getHomeActivity().onBackPressed();
    }

    private void setSelected(TextView frgAdvancedSearchAnytime) {
        frgAdvancedSearchAnytime.setActivated(true);
        frgAdvancedSearchAnytime.setTextColor(Color.WHITE);
    }

    public void setWeekDayAdapter() {
        mAdapter = new WeekDayAdapter(getContext(), true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBinding.frgAdvancedWeekList.setLayoutManager(manager);
        mBinding.frgAdvancedWeekList.setAdapter(mAdapter);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        isEveryDaySelected = false;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);

        mBinding.frgAdvancedSearchSpecificDate.setText(
                DateUtil.getFormatedDate(DateUtil.getSimpleDate(calendar), DateUtil.SIMPLE_DATE, DISPLAY_DATE)
        );
        String date = DateUtil.getSimpleDate(calendar);
        mModel.setSpecific_date(
                DateUtil.getFormatedDateInUTC(date, DateUtil.SIMPLE_DATE, DateUtil.SERVER_DATE)
        );

        isEdited = true;
        mModel.setEveryday(null);
        mModel.setAnytime(Const.YES_NO.no.toString());
        setSelected(mBinding.frgAdvancedSearchSpecificDate);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCallback = null;
        mModel = null;
        mBinding = null;
        mAdapter = null;
    }

}
