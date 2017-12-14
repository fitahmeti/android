package com.app.swishd.home.profile.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.swishd.R;
import com.app.swishd.databinding.RowSelectSwishrBinding;
import com.app.swishd.home.profile.model.offer.DataItem;
import com.app.swishd.retrofit.call.Api;
import com.app.swishd.utility.ImageUtil;

import java.util.List;

public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.OfferListViewHolder> {

    private LayoutInflater mInflater;
    private List<DataItem> mSwishrList;
    private View.OnClickListener onClickListener;

    public OfferListAdapter(Context context, List<DataItem> list, View.OnClickListener listner) {
        mInflater = LayoutInflater.from(context);
        mSwishrList = list;
        onClickListener = listner;
    }

    @Override
    public OfferListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_select_swishr, parent, false);
        return new OfferListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(OfferListViewHolder holder, int position) {
        holder.mBinding.setSwishr(mSwishrList.get(position));
        holder.mBinding.setPosition(position);
        holder.mBinding.setClick(onClickListener);
        ImageUtil.load(Api.BASE_URL_IMAGE + mSwishrList.get(position).getProfileImage(),
                holder.mBinding.rowSelectSwishrImage);
    }

    @Override
    public int getItemCount() {
        return mSwishrList.size();
    }

    public class OfferListViewHolder extends RecyclerView.ViewHolder {

        private RowSelectSwishrBinding mBinding;

        public OfferListViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
