package com.app.swishd.baseutil;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.swishd.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public abstract class BaseLocationFragment extends BaseFragment {

    protected MapView mMapView;
    protected GoogleMap mGoogleMap;
    protected Location mPickUpLocation;
    protected OnMapReadyCallback mapReadyCallback;
    private Location mDropLocation;

    public abstract MapView getMapView();

    public abstract OnMapReadyCallback getMapReadyCallback();


    protected void setPickUpLocation(double lat, double lng) {
        mPickUpLocation = new Location("");
        mPickUpLocation.setLatitude(lat);
        mPickUpLocation.setLongitude(lng);
        setMapView();
    }

    protected void setDropLocation(double lat, double lng) {
        mDropLocation = new Location("");
        mDropLocation.setLatitude(lat);
        mDropLocation.setLongitude(lng);
    }

    private void setMapView() {
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mGoogleMap = mMap;
                LatLng latLng = new LatLng(mPickUpLocation.getLatitude(), mPickUpLocation.getLongitude());
                CameraUpdate cameraupdate = CameraUpdateFactory.newLatLngZoom(latLng, 10f);
                mGoogleMap.animateCamera(cameraupdate);
                mapReadyCallback.onMapReady(mGoogleMap);
            }
        });
    }

    public void addPickUpMarker() {
        if (mGoogleMap == null || mPickUpLocation == null) {
            getMapView().setVisibility(View.GONE);
            return;
        }
        MarkerOptions markerOptions = new MarkerOptions()
                .position(getLatLng(mPickUpLocation))
                .title(getString(R.string.pick_location))
                .snippet(getString(R.string.pick_location));
        mGoogleMap.addMarker(markerOptions);
        getMapView().setVisibility(View.VISIBLE);
    }

    public void addDropMarker() {
        if (mGoogleMap == null || mDropLocation == null) {
            return;
        }
        MarkerOptions markerOptions = new MarkerOptions()
                .position(getLatLng(mDropLocation))
                .title(getString(R.string.drop_location))
                .snippet(getString(R.string.drop_location));
        mGoogleMap.addMarker(markerOptions);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(getLatLng(mPickUpLocation));
        builder.include(getLatLng(mDropLocation));
        LatLngBounds bounds = builder.build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
    }

    private LatLng getLatLng(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    public void clearMap() {
        if (mGoogleMap != null)
            mGoogleMap.clear();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMapView = getMapView();
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately
        mapReadyCallback = getMapReadyCallback();

        try {
            MapsInitializer.initialize(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mMapView = null;
        mGoogleMap = null;
        mPickUpLocation = null;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
