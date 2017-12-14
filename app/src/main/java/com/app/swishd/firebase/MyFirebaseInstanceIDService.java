package com.app.swishd.firebase;

import android.util.Log;

import com.app.swishd.retrofit.call.ApiTask;
import com.app.swishd.utility.MyPrefs;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Firebase Token", "Refreshed token: " + refreshedToken);

        if (MyPrefs.getInstance().isLogin()) {
            ApiTask.getInstance().savePushToken();
        }
    }
}