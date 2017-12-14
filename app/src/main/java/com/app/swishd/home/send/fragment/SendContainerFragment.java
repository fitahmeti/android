package com.app.swishd.home.send.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseContainerFragment;

public class SendContainerFragment extends BaseContainerFragment {

    private SendFragment sendFragment;

    @Override
    public int getLayout() {
        return R.layout.frg_send_container;
    }

    @Override
    public int getContainerID() {
        return R.id.container_send;
    }

    @Override
    public Fragment getFirstFragment() {
        if (sendFragment == null)
            sendFragment = new SendFragment();
        return sendFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public boolean popFragment() {
        boolean isPop = false;
        try {
            if (sendFragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
                sendFragment.getChildFragmentManager().popBackStack();
                isPop = true;
            } else {
                isPop = super.popFragment();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isPop;
    }

    public void replaceFragmentClearBackStack(Fragment fragment, boolean addToBackStack) {
        getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (addToBackStack)
            transaction.addToBackStack(fragment.getClass().getSimpleName());

        transaction.replace(getContainerID(), fragment, fragment.getClass().getSimpleName());
        transaction.commit();
    }
}
