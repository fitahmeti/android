package com.app.swishd.utility;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.app.swishd.home.MainActivity;
import com.app.swishd.login.activity.LoginActivity;

import static com.app.swishd.utility.EnumPreference.Authorization;

public class MyPrefs {

    private static MyPrefs mPrefs;
    private SharedPreferences myPrefs;
    private SharedPreferences.Editor prefEditor;
    private Context context;

    private MyPrefs(Context context) {
        this.context = context;
        myPrefs = context.getSharedPreferences("Swishd", 0);
        prefEditor = myPrefs.edit();
    }

    public static MyPrefs init(Context context) {
        mPrefs = new MyPrefs(context);
        return mPrefs;
    }

    public static MyPrefs getInstance() {
        if (mPrefs == null)
            throw new NullPointerException("MyPrefs not initialised, call MyPrefs.init() in Application");
        return mPrefs;
    }

    public void logout() {
        prefEditor.clear();
        prefEditor.commit();
        //clear notifications on logout
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();

        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        MainActivity.finishSelf();
    }

    public boolean isLogin() {
        return !getStringData(Authorization).isEmpty();
    }

    public void putData(EnumPreference key, String data) {
        prefEditor.putString(key.toString(), data).commit();
    }

    public void putData(EnumPreference key, boolean data) {
        prefEditor.putBoolean(key.toString(), data).commit();
    }

    public String getStringData(EnumPreference key) {
        return myPrefs.getString(key.toString(), "");
    }

    public boolean getBooleanData(EnumPreference key) {
        return myPrefs.getBoolean(key.toString(), false);
    }

    public void clearPrefrences() {
        prefEditor.clear();
        prefEditor.commit();
    }
}
