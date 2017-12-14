package com.app.swishd.home.search.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.swishd.R;
import com.app.swishd.databinding.RowWeekDayBinding;
import com.app.swishd.utility.Const;

import java.util.ArrayList;
import java.util.List;


public class WeekDayAdapter extends RecyclerView.Adapter<WeekDayAdapter.WeekDayViewHolder> {

    private CharSequence[] mWeekDayName = {"M", "T", "W", "T", "F", "S", "S"};
    private LayoutInflater mInflater;
    private ArrayList<Integer> mSelectedPosition;
    private boolean isWeekDayClickable;

    public WeekDayAdapter(Context context, boolean isWeekDayClickable) {
        this.mInflater = LayoutInflater.from(context);
        this.mSelectedPosition = new ArrayList<>();
        this.isWeekDayClickable = isWeekDayClickable;
    }

    @Override
    public WeekDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_week_day, parent, false);
        return new WeekDayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeekDayViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mWeekDayName.length;
    }


    public ArrayList<Integer> getSelectedItem() {
        return mSelectedPosition;
    }

    public void addEveryDayValue(List<String> everyday) {
        for (String value : everyday) {
            mSelectedPosition.add(Const.WEEK_DAY_NAME.getValueByName(value));
        }
        notifyDataSetChanged();
    }

    public class WeekDayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowWeekDayBinding mBinding;

        public WeekDayViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
            if (isWeekDayClickable)
                mBinding.rowWeekDay.setOnClickListener(this);
        }

        public void setData(int position) {
            mBinding.rowWeekDay.setText(mWeekDayName[position].toString());
            mBinding.rowWeekDay.setTag(position);

            if (mSelectedPosition.contains(position))
                mBinding.rowWeekDay.setActivated(true);
            else
                mBinding.rowWeekDay.setActivated(false);
        }

        @Override
        public void onClick(View v) {
            if (mSelectedPosition.contains((Integer) v.getTag())) {
                mSelectedPosition.remove((Integer) v.getTag());
            } else {
                mSelectedPosition.add((Integer) v.getTag());
            }
            notifyDataSetChanged();
        }
    }
}
