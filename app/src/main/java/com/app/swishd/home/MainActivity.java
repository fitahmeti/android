package com.app.swishd.home;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragmentActivity;
import com.app.swishd.databinding.ActMainBinding;
import com.app.swishd.home.fragment.HomeFragment;
import com.app.swishd.home.profile.ProfileContainerFragment;
import com.app.swishd.home.profile.ProfileFragment;
import com.google.firebase.iid.FirebaseInstanceId;

import static com.app.swishd.utility.EnumPreference.Authorization;

public class MainActivity extends BaseFragmentActivity {

    private static MainActivity mainActivity;
    private ActMainBinding mBinding;
    private boolean doubleTapExit;
    private HomeFragment homeFragment;

    @Override
    public int getLayout() {
        return R.layout.act_main;
    }

    @Override
    public void setBinding(ViewDataBinding bindingObject) {
        mBinding = (ActMainBinding) bindingObject;
    }

    @Override
    public void init() {
        mainActivity = this;
        if (!getPrefs().getStringData(Authorization).isEmpty() && FirebaseInstanceId.getInstance().getToken() != null)
            getApiTask().savePushToken();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        homeFragment = new HomeFragment();
        ft.replace(R.id.act_main_container, homeFragment, homeFragment.getClass().getSimpleName());
        ft.commit();
    }

    public static void finishSelf() {
        try {
            if (mainActivity != null)
                mainActivity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        boolean isStackEmpty = homeFragment.getCurrentContainer().popFragment();
        if (!isStackEmpty) {
            if (doubleTapExit) {
                super.onBackPressed();
            } else {
                doubleTapExit = true;
                Toast.makeText(this, R.string.confirmExit, Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleTapExit = false;
                    }
                }, 2000);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment homeFragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
        if (homeFragment != null && homeFragment instanceof HomeFragment) {
            Fragment currentContainer = ((HomeFragment) homeFragment).getCurrentContainer();
            if (currentContainer instanceof ProfileContainerFragment) {
                Fragment profileFragment = currentContainer.getChildFragmentManager().findFragmentByTag(ProfileFragment.class.getSimpleName());
                if (profileFragment != null && profileFragment instanceof ProfileFragment)
                    profileFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}