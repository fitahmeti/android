package com.app.swishd.home.search.fragment;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgSearchBinding;
import com.app.swishd.home.profile.model.UserProfile;
import com.app.swishd.home.search.adapter.JobSuggestedAdapter;
import com.app.swishd.home.search.adapter.SearchHistoryAdapter;
import com.app.swishd.home.search.dialog.AcceptJobDialog;
import com.app.swishd.home.search.interfaces.OnSearchLocationPickerCallback;
import com.app.swishd.home.search.interfaces.OnViewAllSearchCallback;
import com.app.swishd.home.search.model.FindJobModel;
import com.app.swishd.home.search.model.ResponseSearchList;
import com.app.swishd.home.search.model.job.ResponseJobList;
import com.app.swishd.home.search.model.qrScan.ResponseQRScan;
import com.app.swishd.retrofit.OnResponseCallback;
import com.app.swishd.retrofit.RequestCode;
import com.app.swishd.utility.EnumPreference;
import com.app.swishd.utility.Utility;
import com.app.swishd.widget.EndlessRecyclerOnScrollListener;
import com.app.swishd.widget.EndlessRecyclerOnScrollListenerWithLastItemCount;
import com.app.swishd.widget.RecyclerItemTouchHelper;
import com.vinay.utillib.UtilLib;
import com.vinay.utillib.locationmanage.OnLocationPickListener;
import com.vinay.utillib.permissionutils.PermissionResponse;
import com.vinay.utillib.permissionutils.PermissionResultCallback;

import io.reactivex.disposables.Disposable;
import me.ydcool.lib.qrmodule.activity.QrScannerActivity;

import static android.app.Activity.RESULT_OK;
import static com.app.swishd.retrofit.RequestCode.REQUEST_GET_JOBS;
import static com.app.swishd.utility.PermissionConst.CAMERA;
import static com.app.swishd.utility.PermissionConst.VIBRATE;

public class SearchFragment
        extends BaseFragment implements View.OnClickListener,
        OnResponseCallback, OnViewAllSearchCallback, OnSearchLocationPickerCallback,
        SwipeRefreshLayout.OnRefreshListener, AppBarLayout.OnOffsetChangedListener,
        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private FrgSearchBinding mBinding;
    private ResponseSearchList mSearchList;
    private ResponseJobList mJobList;
    private SearchHistoryAdapter mAdapterSearchHistory;
    private JobSuggestedAdapter mJobSuggestedAdapter;
    private Disposable mSearchCall, mJobCall;
    private FindJobModel mFindJobModel;
    private Location mLocation;

    private int iSearchStart;
    private int mRemoveItem;
    private int iLimit = 15;
    private boolean isExpanded;
    private boolean isWsCalled;

    @Override
    public int getLayout() {
        return R.layout.frg_search;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgSearchBinding) binding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        iSearchStart = 0;
        mRemoveItem = 0;
        isWsCalled = false;
        mFindJobModel = new FindJobModel();
        setOnClickListener();
        getSearchHistory();
        UtilLib.getLocationManager(getContext()).getLocation(new OnLocationPickListener() {
            @Override
            public void getLastLocation(Location location) {
                if (!isWsCalled) {
//                mLocation = location;
                    getSuggestedJobs();
                    isWsCalled = true;
                }
            }

            @Override
            public void onLocationChanged(Location location) {
                if (!isWsCalled) {
//                mLocation = location;
                    getSuggestedJobs();
                    isWsCalled = true;
                }
            }

            @Override
            public void onError(String s) {

            }
        });
    }

    private void setOnClickListener() {
        mBinding.frgSearchLocationPicker.frgSearchPickLocation.setOnClickListener(this);
        mBinding.frgSearchLocationPicker.frgSearchDropLocation.setOnClickListener(this);
        mBinding.frgSearchAdvancedSearch.setOnClickListener(this);
        mBinding.frgSearchSearch.setOnClickListener(this);
        mBinding.frgSearchViewAllSearch.setOnClickListener(this);
        mBinding.frgSearchRefresh.setOnRefreshListener(this);
        mBinding.frgSearchMainTitle.setOnClickListener(this);
        mBinding.frgSearchAppBarLayout.addOnOffsetChangedListener(this);

        mBinding.frgSearchQrScanner.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frg_search_pick_location:
                startLocationPickerFragment(true);
                break;
            case R.id.frg_search_drop_location:
                startLocationPickerFragment(false);
                break;
            case R.id.frg_search_search:
                startLocationPickerFragment(true);
                break;
            case R.id.frg_search_advanced_search:
                break;
            case R.id.frg_search_view_all_search:
                if (Utility.isNetworkAvailable(getContext())) {
                    ((SearchContainerFragment) getParentFragment()).addFragment(
                            ViewAllSearchFragment.getInstance(this, mSearchList)
                            , true);
                } else {
                    showSnackBar(mBinding, getString(R.string.e_no_internet));
                }
                break;
            case R.id.frg_search_main_title:
                if (isExpanded) {
                    mBinding.frgSearchAppBarLayout.setExpanded(false, true);
                    mBinding.frgSearchIndicatorIcon.setRotation(180);
                } else {
                    mBinding.frgSearchAppBarLayout.setExpanded(true, true);
                    mBinding.frgSearchIndicatorIcon.setRotation(0);
                }

                break;
            case R.id.frg_search_qr_Scanner:
                startQrScanner();
                break;
            case R.id.row_job_list_main:

                if (Utility.isNetworkAvailable(getContext())) {
                    ((SearchContainerFragment) getParentFragment()).addFragment(
                            SwissJobDetailsFragment.getInstance(mJobList.getData().get((Integer) v.getTag()).getId())
                            , true
                    );
                } else {
                    showSnackBar(mBinding, getString(R.string.e_no_internet));
                }
                break;
        }
    }

    private void startQrScanner() {
        UtilLib.getPermission(getContext(), new String[]{CAMERA, VIBRATE})
                .enqueue(new PermissionResultCallback() {
                    @Override
                    public void onComplete(PermissionResponse permissionResponse) {
                        if (permissionResponse.isAllGranted()) {
                            Intent intent = new Intent(getContext(), QrScannerActivity.class);
                            startActivityForResult(intent, QrScannerActivity.QR_REQUEST_CODE);
                        }
                    }
                });
    }

    /**
     * @param isPickLocationRequest = true for pickLocation AND false for DropLocation
     */
    private void startLocationPickerFragment(boolean isPickLocationRequest) {
        if (Utility.isNetworkAvailable(getContext())) {
            ((SearchContainerFragment) getParentFragment()).addFragment(
                    SearchLocationPickerFragment.getInstance(isPickLocationRequest, this),
                    true);
        } else {
            showSnackBar(mBinding, getString(R.string.e_no_internet));
        }
    }

    private void getSearchHistory() {
        mSearchCall = getApiTask().doGetSearchList(getContext(),
                iSearchStart, iLimit, this);
    }

    private void getSuggestedJobs() {
        if (mLocation != null) {
            mFindJobModel.setSource_latitude(mLocation.getLatitude());
            mFindJobModel.setSource_longitude(mLocation.getLongitude());
        }

        if (getContext() != null) {
            showProgressDialog();
            mJobCall = getApiTask().doGetJobs(getContext(), mFindJobModel, this);
        }
    }

    @Override
    public void onResponseReceived(Object model, int requestCode) {
        if (requestCode == RequestCode.REQUEST_SEARCH_LIST) {
            if (mAdapterSearchHistory == null) {
                mSearchList = (ResponseSearchList) model;
                setHistorySearchAdapter();
            } else {
                ResponseSearchList tempList = (ResponseSearchList) model;
                mSearchList.getData().addAll(tempList.getData());
                mAdapterSearchHistory.notifyDataSetChanged();
            }
            setSearchNoDataView();
        } else if (requestCode == REQUEST_GET_JOBS) {
            if (mJobSuggestedAdapter == null) {
                mJobList = (ResponseJobList) model;
                setJobAdapter();
            } else {
                ResponseJobList temp = (ResponseJobList) model;
                mJobList.getData().addAll(temp.getData());
                mJobSuggestedAdapter.notifyDataSetChanged();
            }
            setJobNoDataView();
            dismissProgressDialog();
        } else if (requestCode == RequestCode.REQUEST_SCAN_JOB) {
            ResponseQRScan scanModel = (ResponseQRScan) model;
            boolean isMyJob = UserProfile.fromString(getPrefs().getStringData(EnumPreference.MY_PROFILE))
                    .get_id().equals(scanModel.getData().getJob().getSender().getId());
            if (scanModel.getData().getJob().getQrcode() != null &&
                    scanModel.getData().getJob().getQrcode().size() > 0)
                showDialog(scanModel.getData().getJob().getQrcode().get(0).getId(), isMyJob);
            dismissProgressDialog();
        }
    }

    private void setJobNoDataView() {
        if (mJobList != null && mJobList.getData().size() > 0) {
            mBinding.frgSearchListView.recyclerHelperList.setVisibility(View.VISIBLE);
            mBinding.frgSearchListView.recyclerHelperNoData.setVisibility(View.INVISIBLE);
        } else {
            mBinding.frgSearchListView.recyclerHelperList.setVisibility(View.INVISIBLE);
            mBinding.frgSearchListView.recyclerHelperNoData.setVisibility(View.VISIBLE);
        }
    }

    private void setJobAdapter() {
        if (getContext() == null)
            return;
        mJobSuggestedAdapter = new JobSuggestedAdapter(getContext(), mJobList.getData(), this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mBinding.frgSearchListView.recyclerHelperList.setLayoutManager(manager);
        mBinding.frgSearchListView.recyclerHelperList.setAdapter(mJobSuggestedAdapter);
        mBinding.frgSearchListView.recyclerHelperList.addOnScrollListener(new
                EndlessRecyclerOnScrollListenerWithLastItemCount(manager,
                mBinding.frgSearchHistoryList, new EndlessRecyclerOnScrollListenerWithLastItemCount.onLastItem() {
            @Override
            public void onLastItem() {
                mFindJobModel.setStart(mJobList.getData().size() + mRemoveItem);
                if (mFindJobModel.getStart() < mJobList.getTotal()) {
                    getSuggestedJobs();
                }
            }
        }));

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);

        new ItemTouchHelper(itemTouchHelperCallback)
                .attachToRecyclerView(mBinding.frgSearchListView.recyclerHelperList);

        mBinding.frgSearchListView.recyclerHelperList.scheduleLayoutAnimation();
    }

    private void setSearchNoDataView() {
        if (mSearchList != null && mSearchList.getData().size() > 0) {
            mBinding.frgSearchNoSearchView.setVisibility(View.GONE);
            mBinding.frgSearchSearchHistoryView.setVisibility(View.VISIBLE);
        } else {
            mBinding.frgSearchNoSearchView.setVisibility(View.VISIBLE);
            mBinding.frgSearchSearchHistoryView.setVisibility(View.GONE);
        }
    }

    private void setHistorySearchAdapter() {
        mAdapterSearchHistory = new SearchHistoryAdapter(getContext(), mSearchList.getData());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBinding.frgSearchHistoryList.setLayoutManager(manager);
        mBinding.frgSearchHistoryList.setAdapter(mAdapterSearchHistory);
        mBinding.frgSearchHistoryList.addOnScrollListener(new EndlessRecyclerOnScrollListener(manager,
                mBinding.frgSearchHistoryList, new EndlessRecyclerOnScrollListener.onLastItem() {
            @Override
            public void onLastItem() {
                iSearchStart = mSearchList.getData().size();
                if (iSearchStart < mSearchList.getTotal())
                    getSearchHistory();
            }
        }));
    }

    @Override
    public void onResponseError(String message) {
        dismissProgressDialog();
        showSnackBar(mBinding, message);
    }

    @Override
    public void onUpdateList(ResponseSearchList list) {
        mSearchList = list;
        mAdapterSearchHistory.notifyDataSetChanged();
        setSearchNoDataView();
    }

    @Override
    public void onSearchLocationCallback() {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mBinding.frgSearchRefresh.setRefreshing(false);
        if (mSearchList != null && mAdapterSearchHistory != null) {
            mSearchList.getData().clear();
            mAdapterSearchHistory.notifyDataSetChanged();
        }
        if (mJobList != null && mJobSuggestedAdapter != null) {
            mJobList.getData().clear();
            mJobSuggestedAdapter.notifyDataSetChanged();
        }
        initData();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            isExpanded = true;
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            isExpanded = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QrScannerActivity.QR_REQUEST_CODE && resultCode == RESULT_OK) {
            doScanJob(data.getExtras().getString(QrScannerActivity.QR_RESULT_STR));
        }
    }

    private void showDialog(String id, boolean isMyJob) {
        AcceptJobDialog.getInstance(id, isMyJob).show(getFragmentManager(), getString(R.string.job));
    }

    public void doScanJob(String id) {
        showProgressDialog();
        getApiTask().doScanJob(getContext(), id, this);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        doHideJob(mJobList.getData().get(position).getId());
        mJobSuggestedAdapter.removeItem(position);
        mRemoveItem++;
    }

    public void doHideJob(String position) {
        getApiTask().doHideJob(getContext(), position, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
        mAdapterSearchHistory = null;
        if (mSearchCall != null)
            mSearchCall.dispose();
        if (mJobCall != null)
            mJobCall.dispose();
        mLocation = null;
        mSearchCall = null;
        mJobCall = null;
    }
}
