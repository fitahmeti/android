package com.app.swishd.home.send.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.swishd.R;
import com.app.swishd.databinding.RowSwissDetailsBinding;
import com.app.swishd.home.send.interfaces.OnOfficeSelectCallback;
import com.app.swishd.home.send.model.ResponseOfficeList;

public class SwisHdListAdapter extends RecyclerView.Adapter<SwisHdListAdapter.SwisViewHolder> {

    private ResponseOfficeList mList;
    private LayoutInflater mInflater;
    private OnOfficeSelectCallback mCallback;

    public SwisHdListAdapter(Context context, ResponseOfficeList list, OnOfficeSelectCallback callback) {
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
        this.mCallback = callback;
    }

    @Override
    public SwisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_swiss_details, parent, false);
        return new SwisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SwisViewHolder holder, int position) {
        holder.mBinding.rowSwissDetailsMain.setTag(position);
        holder.mBinding.setSwiss(mList.getDetail().get(position));
    }

    @Override
    public int getItemCount() {
        return mList.getDetail().size();
    }

    public class SwisViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RowSwissDetailsBinding mBinding;

        public SwisViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
            mBinding.rowSwissDetailsMain.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mCallback != null)
                mCallback.onOfficeSelected(
                        mList.getDetail().get((Integer) mBinding.rowSwissDetailsMain.getTag()));
        }
    }

}
