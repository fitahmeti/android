package com.app.swishd.home.send.dialog;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.app.swishd.R;
import com.app.swishd.databinding.DialogSortByBinding;
import com.app.swishd.home.send.interfaces.OnSortByDialogCallback;
import com.app.swishd.home.send.interfaces.OnSortByDialogClickListener;
import com.app.swishd.utility.Const;
import com.app.swishd.utility.Utility;

public class SortByDialog extends DialogFragment implements OnSortByDialogClickListener, RadioGroup.OnCheckedChangeListener {

    private static SortByDialog dialog;
    private DialogSortByBinding mBinding;
    private String mSortBy = null;
    private OnSortByDialogCallback targetFragment;

    public static SortByDialog getInstance(OnSortByDialogCallback targetFragment) {
        if (dialog == null)
            dialog = new SortByDialog();
        dialog.targetFragment = targetFragment;
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (targetFragment == null)
            return;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_sort_by, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.setClick(this);
        mBinding.dialogSortByGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onOkClick() {
        if (mSortBy == null) {
            Utility.showToast(getContext(), R.string.e_please_select_any_one_sort_type);
            return;
        }
        targetFragment.onSortSelected(mSortBy);
        this.dismiss();
    }

    @Override
    public void onCancelClick() {
        this.dismiss();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (checkedId == R.id.dialog_sort_by_distance) {
            mSortBy = Const.SORT_BY.distance.toString();
        } else {
            mSortBy = Const.SORT_BY.open.toString();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        targetFragment = null;
    }
}
