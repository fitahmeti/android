package com.app.swishd.home.profile.more;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.app.swishd.R;
import com.app.swishd.databinding.RowReferFriendBinding;
import com.app.swishd.utility.Utility;

import java.util.ArrayList;

public class ReferFriendAdapter
        extends RecyclerView.Adapter<ReferFriendAdapter.ReferFriendViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private ArrayList<String> mInviteEmail = new ArrayList<>();
    private ArrayList<Boolean> mInviteEmailValid = new ArrayList<>();

    public ReferFriendAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);

        mInviteEmail.add("");
        mInviteEmailValid.add(false);
    }

    @Override
    public ReferFriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_refer_friend, parent, false);
        return new ReferFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReferFriendViewHolder holder, int position) {
        holder.mBinding.rowReferFriendAddEmail.setTag(position);
        holder.mListener.updatePosition(position);
        holder.mBinding.rowReferFriendEmail.setText(mInviteEmail.get(position));
        if (position == (mInviteEmail.size() - 1))
            holder.mBinding.rowReferFriendAddEmail.setVisibility(View.VISIBLE);
        else
            holder.mBinding.rowReferFriendAddEmail.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mInviteEmail.size();
    }

    public boolean checkValidEmail() {
        for (int i = 0; i < mInviteEmailValid.size(); i++) {
            if (mInviteEmailValid.get(i) == false) {
                return false;
            } else if (i == mInviteEmailValid.size() - 1)
                return true;
        }
        return false;
    }

    public ArrayList<String> getEmailAddress() {
        return mInviteEmail;
    }

    public class ReferFriendViewHolder extends
            RecyclerView.ViewHolder implements View.OnClickListener {

        private RowReferFriendBinding mBinding;
        private EditTextListener mListener;

        public ReferFriendViewHolder(View itemView) {
            super(itemView);
            mListener = new EditTextListener();
            mBinding = DataBindingUtil.bind(itemView);
            mBinding.rowReferFriendAddEmail.setOnClickListener(this);
            mBinding.rowReferFriendEmail.addTextChangedListener(mListener);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.row_refer_friend_add_email) {
                hideKeyboard();
                checkEmailValid(checkValidEmail());
            }
        }

        private void checkEmailValid(boolean isValid) {
            if (isValid == false)
                Utility.showToast(mContext, R.string.e_please_enter_valid_email_address);
            else
                addDataNotify();
        }

        private void addDataNotify() {
            mInviteEmail.add("");
            mInviteEmailValid.add(false);
            notifyDataSetChanged();
        }

        private void hideKeyboard() {
            InputMethodManager inputManager = (InputMethodManager)
                    mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(
                    mBinding.rowReferFriendEmail.getWindowToken(),0);
        }
    }

    private class EditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i2, int i3) {
            mInviteEmailValid.set(position, Utility.isEmailValid(s.toString()));
            mInviteEmail.set(position, s.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

}
