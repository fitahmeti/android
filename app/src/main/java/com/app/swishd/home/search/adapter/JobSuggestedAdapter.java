package com.app.swishd.home.search.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.swishd.R;
import com.app.swishd.databinding.RowJobListBinding;
import com.app.swishd.home.search.model.job.DataItem;
import com.app.swishd.retrofit.call.Api;
import com.app.swishd.utility.DateUtil;
import com.app.swishd.utility.ImageUtil;
import com.app.swishd.widget.SelectedItemElevationEffect;

import java.util.List;

public class JobSuggestedAdapter
        extends RecyclerView.Adapter<JobSuggestedAdapter.SearchViewHolder> {

    private Context mContext;
    private List<DataItem> mItemList;
    private LayoutInflater mInflater;
    private View.OnClickListener mClickListner;

    public JobSuggestedAdapter(Context context, List<DataItem> mItemList,
                               View.OnClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.mItemList = mItemList;
        this.mContext = context;
        this.mClickListner = listener;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_job_list, parent, false);
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

    public DataItem getItem(int position) {
        return mItemList.get(position);
    }

    private String getFormattedDate(String date, String s) {
        return DateUtil.getFormatedDate(date, DateUtil.SERVER_DATE, s);
    }

    public class SearchViewHolder extends
            RecyclerView.ViewHolder {
        public RowJobListBinding mBinding;

        public SearchViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
            mBinding.rowJobListMain.setOnTouchListener(new SelectedItemElevationEffect());
            mBinding.setClick(mClickListner);
        }

        public void setData(int position) {
            mBinding.setItem(mItemList.get(position));
            mBinding.setPosition(position);

            ImageUtil.load(Api.BASE_URL_IMAGE + getItem(position).getJobSize().getSOriginalSizePicture(),
                           mBinding.rowJobListItemImage, R.drawable.ic_place_holder);

            if (getItem(position).getsPickDateTime() == null) {
                mBinding.rowJobListPickDate.setText(mContext.getString(R.string.flexible));
                mBinding.rowJobListPickTime.setText(mContext.getString(R.string.flexible));
            } else {
                mBinding.rowJobListPickDate.setText(getFormattedDate(getItem(position).getsPickDateTime(), "EEE dd MMM"));
                mBinding.rowJobListPickTime.setText(getFormattedDate(getItem(position).getsPickDateTime(), "hh:mm a"));
            }

            if (getItem(position).getsDropDateTime() == null) {
                mBinding.rowJobListDropDate.setText(mContext.getString(R.string.flexible));
                mBinding.rowJobListDropTime.setText(mContext.getString(R.string.flexible));
            } else {
                mBinding.rowJobListDropDate.setText(getFormattedDate(getItem(position).getsDropDateTime(), "EEE dd MMM"));
                mBinding.rowJobListDropTime.setText(getFormattedDate(getItem(position).getsDropDateTime(), "hh:mm a"));
            }
        }
    }

    public void removeItem(int position) {
        mItemList.remove(position);
        notifyItemRemoved(position);
    }

}
