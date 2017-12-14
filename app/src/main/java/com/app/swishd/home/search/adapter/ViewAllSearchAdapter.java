package com.app.swishd.home.search.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.swishd.R;
import com.app.swishd.databinding.RowViewAllSearchBinding;
import com.app.swishd.home.search.model.DataItem;
import com.app.swishd.utility.Const;
import com.app.swishd.widget.SelectedItemElevationEffect;

import java.util.List;

public class ViewAllSearchAdapter
        extends RecyclerView.Adapter<ViewAllSearchAdapter.ViewAllSearchViewHolder> {

    private final String FILTER_STATUS = Const.SEARCH_STATUS.saved.toString();

    private final Context mContext;
    private List<DataItem> mItemList;
    private LayoutInflater mInflater;
    private View.OnClickListener onClickListener;

    public ViewAllSearchAdapter(Context context, List<DataItem> mItemList,
                                View.OnClickListener onClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mItemList = mItemList;
        this.mContext = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewAllSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_view_all_search, parent, false);
        return new ViewAllSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewAllSearchViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public boolean isItemSaved(int position) {
        return mItemList.get(position).getFilterStatus().equals(FILTER_STATUS);
    }

    public class ViewAllSearchViewHolder extends RecyclerView.ViewHolder {
        private RowViewAllSearchBinding mBinding;

        public ViewAllSearchViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
            mBinding.rowViewAllSearchMain.setOnTouchListener(new SelectedItemElevationEffect());
        }

        public void setData(int position) {
            mBinding.setClick(onClickListener);
            mBinding.setLocation(mItemList.get(position));
            mBinding.setPosition(position);
            mBinding.rowViewAllSearchItemFav.setActivated(isItemSaved(position));

            if (mItemList.get(position).getEveryday() != null
                    && mItemList.get(position).getEveryday().size() > 0) {
                mBinding.rowViewAllSearchWeekList.setVisibility(View.VISIBLE);
                WeekDayAdapter mAdapter = new WeekDayAdapter(mContext, false);
                LinearLayoutManager manager = new LinearLayoutManager(mContext);
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mBinding.rowViewAllSearchWeekList.setLayoutManager(manager);
                mBinding.rowViewAllSearchWeekList.setAdapter(mAdapter);
                mAdapter.addEveryDayValue(mItemList.get(position).getEveryday());
            } else {
                mBinding.rowViewAllSearchWeekList.setVisibility(View.GONE);
            }
        }

    }

}
