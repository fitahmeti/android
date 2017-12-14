package com.app.swishd.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.app.swishd.R;
import com.app.swishd.databinding.SendrHeaderViewBinding;
import com.app.swishd.home.profile.model.Job;

public class SendrHeaderView extends FrameLayout {

    private SendrHeaderViewBinding mBinding;
    private Job job;

    public SendrHeaderView(Context context) {
        super(context);
        init();
    }

    public SendrHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SendrHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    private void init() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.sendr_header_view, this, true);
    }

    public void setJob(Job job) {
        this.job = job;
        if (job.getOrder() >= 1 && job.getOrder() <= 3) {
            mBinding.tvHeader.setBackgroundResource(JOB_HEADER_BACKGROUND[job.getOrder() - 1]);
            mBinding.tvHeader.setText(JOB_HEADERS[job.getOrder()-1]);
        }
    }

    private String[] JOB_HEADERS = new String[]{"Active", "Select SWISHR", "Awaiting SWISHR"};
    private Integer[] JOB_HEADER_BACKGROUND = new Integer[]{R.drawable.drw_round_corner_green, R.drawable.drw_round_corner_orange, R.drawable.drw_round_corner_red};
}
