package com.app.swishd.home.send.fragment;


import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.PickLocationLayoutBinding;
import com.app.swishd.home.LocationFromMapActivity;
import com.app.swishd.home.send.interfaces.OnOfficeSelectCallback;
import com.app.swishd.home.send.model.ResponseOfficeList;
import com.app.swishd.utility.Utility;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

import static android.app.Activity.RESULT_OK;
import static com.app.swishd.home.LocationFromMapActivity.ADDRESS;
import static com.app.swishd.home.LocationFromMapActivity.LOCATION;

public abstract class BaseMapFragment extends BaseFragment
        implements View.OnClickListener, OnOfficeSelectCallback {

    private final int REQUEST_CHOOSE_LOCATION = 9966;
    protected MapView mMapView;
    protected GoogleMap mGoogleMap;

    public abstract MapView getMapView();

    public abstract void onMapReadyCallback();

    public abstract PickLocationLayoutBinding getSelectAddressView();

    public abstract boolean hasPickLocationView();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (hasPickLocationView()) {
            PickLocationLayoutBinding pickLocationBinding = getSelectAddressView();
            pickLocationBinding.pickLocationSwisshdPoint.setOnClickListener(this);
            pickLocationBinding.pickLocationUseAddress.setOnClickListener(this);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = getMapView();

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mGoogleMap = mMap;
                onMapReadyCallback();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pick_location_use_address) {
            Intent ivResult = new Intent(getActivity(), LocationFromMapActivity.class);
            startActivityForResult(ivResult, REQUEST_CHOOSE_LOCATION);
            getActivity().overridePendingTransition(R.anim.pull_in_from_right, R.anim.no_anim);
        } else if (v.getId() == R.id.pick_location_swisshd_point) {
            if (Utility.isNetworkAvailable(getContext()))
                ((SendFragment) getParentFragment()).addFragment(SwissPointListFragment.getInstance(this),
                        true);
            else
                toast(getResources().getString(R.string.e_no_internet));
        }
    }

    @Override
    public void onOfficeSelected(ResponseOfficeList.DetailItem item) {
        onOfficeSelectedCallback(item);
    }

    public abstract void onAddressSelected(Location location, String address);

    public abstract void onOfficeSelectedCallback(ResponseOfficeList.DetailItem item);

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHOOSE_LOCATION && resultCode == RESULT_OK) {
            onAddressSelected((Location) data.getParcelableExtra(LOCATION), data.getStringExtra(ADDRESS));
        }
    }
}
