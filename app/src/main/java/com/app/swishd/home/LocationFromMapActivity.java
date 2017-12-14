package com.app.swishd.home;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.databinding.ActivityLocationFromMapBinding;
import com.app.swishd.utility.Utility;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.vinay.utillib.UtilLib;
import com.vinay.utillib.locationmanage.OnLocationPickListener;

public class LocationFromMapActivity
        extends AppCompatActivity implements View.OnClickListener, GoogleMap.OnCameraChangeListener {

    public static final String LOCATION = "get_location";
    public static final String ADDRESS = "get_Address";

    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private ActivityLocationFromMapBinding mBinding;
    private Location mLocation;
    private Address address;
    private Thread thread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_location_from_map);

        mMapView = mBinding.activityLocationFromMapView;
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mGoogleMap = mMap;
                mGoogleMap.setOnCameraChangeListener(LocationFromMapActivity.this);
                UtilLib.getLocationManager(LocationFromMapActivity.this).getLocation(new OnLocationPickListener() {
                    @Override
                    public void getLastLocation(Location location) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        CameraUpdate cameraupdate = CameraUpdateFactory.newLatLngZoom(latLng, 10f);
                        mGoogleMap.animateCamera(cameraupdate);
                    }

                    @Override
                    public void onLocationChanged(Location location) {

                    }

                    @Override
                    public void onError(String s) {

                    }
                });
            }
        });
        setOnClickListener();
    }

    private void setOnClickListener() {
        mBinding.activityLocationFromMapDone.setOnClickListener(this);
        mBinding.activityLocationSearchView.setOnClickListener(this);
        mBinding.activityLocationFromMapBackPressed.setOnClickListener(this);
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
        mBinding = null;
        mLocation = null;
        address = null;
        thread = null;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void setResultOkAndFinish() {
        Intent ivResult = new Intent();
        ivResult.putExtra(LOCATION, mLocation);
        ivResult.putExtra(ADDRESS, mBinding.frgPickLocationAddress.getText().toString());
        if (!mBinding.frgPickLocationAddress.getText().toString().isEmpty())
            setResult(RESULT_OK, ivResult);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_location_from_map_done:
                setResultOkAndFinish();
                break;
            case R.id.activity_location_search_view:
                openGooglePlaceSearch();
                break;
            case R.id.activity_location_from_map_back_pressed:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        LatLng latLng = cameraPosition.target;
        mLocation = new Location("");
        mLocation.setLatitude(latLng.latitude);
        mLocation.setLongitude(latLng.longitude);
        if (mLocation != null && mLocation.getLatitude() != 0 && mLocation.getLongitude() != 0) {

            if (thread != null)
                thread = null;

            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    address = Utility.getAddress(LocationFromMapActivity.this, mLocation.getLatitude(), mLocation.getLongitude());
                    setText();
                }
            });

            thread.run();
        }
    }

    private void setText() {
        if (address == null) {
            mBinding.frgPickLocationAddress.setVisibility(View.INVISIBLE);
            return;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(address.getAddressLine(0));
        buffer.append((address.getAdminArea() == null) ? "" : " ," + address.getAdminArea());
        buffer.append((address.getSubLocality() == null) ? "" : " ," + address.getSubLocality());
        buffer.append((address.getCountryName() == null) ? "" : " ," + address.getCountryName());
        buffer.append((address.getPostalCode() == null) ? "" : " ," + address.getPostalCode());
        mBinding.frgPickLocationAddress.setVisibility(View.VISIBLE);
        mBinding.frgPickLocationAddress.setText(buffer);
    }


    public void openGooglePlaceSearch() {
        int var1 = -1;

        try {
            Intent var2 = (new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY))
//                    .setBoundsBias(this.zzblq)
//                    .setFilter(this.zzblr)
                    .zzdB(mBinding.activityLocationSearchView.getText().toString().trim())
                    .zzbn(1)
                    .build(this);
            mBinding.activityLocationSearchView.setClickable(false);
            this.startActivityForResult(var2, 30421);
        } catch (GooglePlayServicesRepairableException var3) {
            var1 = var3.getConnectionStatusCode();
            Log.e("Places", "Could not open autocomplete activity", var3);
        } catch (GooglePlayServicesNotAvailableException var4) {
            var1 = var4.errorCode;
            Log.e("Places", "Could not open autocomplete activity", var4);
        }

        if (var1 != -1) {
            GoogleApiAvailability.getInstance().showErrorDialogFragment(this, var1, 30422);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mBinding.activityLocationSearchView.setClickable(true);
        if (requestCode == 30421) {
            if (resultCode == -1) {
                Place selectedPlace = PlaceAutocomplete.getPlace(this, data);
                LatLng latLng = new LatLng(selectedPlace.getLatLng().latitude, selectedPlace.getLatLng().longitude);
                CameraUpdate cameraupdate = CameraUpdateFactory.newLatLngZoom(latLng, 10f);
                mGoogleMap.animateCamera(cameraupdate);
                mBinding.activityLocationSearchView.setText(selectedPlace.getName().toString());
            } else if (resultCode == 2) {
                Status var5 = PlaceAutocomplete.getStatus(this, data);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_anim, R.anim.push_out_to_right);
    }

}
