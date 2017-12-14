package com.app.swishd.home.send.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.swishd.R;
import com.app.swishd.databinding.RowSelectItemSizeBinding;
import com.app.swishd.home.send.model.ResponseItemSize;

import java.util.List;

public class SelectItemSizeAdapter extends
        RecyclerView.Adapter<SelectItemSizeAdapter.SelectItemSizeViewHolder> {

    private LayoutInflater mInflater;
    private List<ResponseItemSize.ItemsItem> mItems;
    private int mCurrentSelectedItem = 0;

    public SelectItemSizeAdapter(@NonNull Context context,@NonNull List<ResponseItemSize.ItemsItem> items) {
        mInflater = LayoutInflater.from(context);
        this.mItems = items;
    }

    @Override
    public SelectItemSizeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_select_item_size, parent, false);
        return new SelectItemSizeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectItemSizeViewHolder holder, int position) {
        if (mItems != null) {
            holder.setData(position);
        }
    }

    private ResponseItemSize.ItemsItem getObject(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public ResponseItemSize.ItemsItem getSelectedItem() {
        return mItems.get(mCurrentSelectedItem);
    }

    public class SelectItemSizeViewHolder
            extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RowSelectItemSizeBinding mBinding;

        public SelectItemSizeViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
            mBinding.rowSelectItemState.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (mCurrentSelectedItem == (int) v.getTag())
                return;

            mCurrentSelectedItem = (int) v.getTag();
            notifyDataSetChanged();
        }

        public void setData(int position) {
            mBinding.setItem(getObject(position));
            mBinding.rowSelectItemState.setTag(position);

            if (position == mCurrentSelectedItem) {
                mBinding.rowSelectItemState.setActivated(true);
            } else {
                mBinding.rowSelectItemState.setActivated(false);
            }
        }

    }
}
