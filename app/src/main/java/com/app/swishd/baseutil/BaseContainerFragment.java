package com.app.swishd.baseutil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public abstract class BaseContainerFragment extends Fragment {

    private boolean mIsViewInited;
    private int container;

    public abstract int getLayout();

    public abstract int getContainerID();

    public abstract Fragment getFirstFragment();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        mIsViewInited = false;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mIsViewInited) {
            mIsViewInited = true;
            container = getContainerID();
            replaceFragment(getFirstFragment(), false);
        }
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (addToBackStack)
            transaction.addToBackStack(fragment.getClass().getSimpleName());

        transaction.replace(container, fragment, fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public void addFragment(Fragment fragment, boolean addToBackStack,
                            Fragment removeFragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        transaction.remove(removeFragment);
        if (addToBackStack)
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.add(container, fragment, fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public void addFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        if (addToBackStack)
            transaction.addToBackStack(fragment.getClass().getSimpleName());

        transaction.add(container, fragment, fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public boolean popFragment() {
        boolean isPop = false;
        try {
            if (getChildFragmentManager().getBackStackEntryCount() > 0) {
                isPop = true;
                getChildFragmentManager().popBackStack();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isPop;
    }
}