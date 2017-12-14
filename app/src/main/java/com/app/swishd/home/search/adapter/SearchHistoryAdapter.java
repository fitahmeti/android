package com.app.swishd.home.search.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.swishd.R;
import com.app.swishd.databinding.RowSearchHistoryBinding;
import com.app.swishd.home.search.model.DataItem;

import java.util.List;

public class SearchHistoryAdapter
        extends RecyclerView.Adapter<SearchHistoryAdapter.SearchViewHolder> {

    private final Context mContext;
    private List<DataItem> mItemList;
    private LayoutInflater mInflater;

    public SearchHistoryAdapter(Context context, List<DataItem> mItemList) {
        this.mInflater = LayoutInflater.from(context);
        this.mItemList = mItemList;
        this.mContext = context;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_search_history, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private RowSearchHistoryBinding mBinding;

        public SearchViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public void setData(int position) {
            mBinding.setPosition(position + 1);
            mBinding.setLocation(mItemList.get(position));

            if (mItemList.get(position).getEveryday() != null
                    && mItemList.get(position).getEveryday().size() > 0) {
                mBinding.rowDaysList.setVisibility(View.VISIBLE);
                WeekDayAdapter mAdapter = new WeekDayAdapter(mContext, false);
                LinearLayoutManager manager = new LinearLayoutManager(mContext);
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mBinding.rowDaysList.setLayoutManager(manager);
                mBinding.rowDaysList.setAdapter(mAdapter);
                mAdapter.addEveryDayValue(mItemList.get(position).getEveryday());
            } else {
                mBinding.rowDaysList.setVisibility(View.GONE);
            }
        }
    }
}
