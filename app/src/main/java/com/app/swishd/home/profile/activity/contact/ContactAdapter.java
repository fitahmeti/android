package com.app.swishd.home.profile.activity.contact;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.swishd.R;
import com.app.swishd.databinding.RowContactBinding;
import com.app.swishd.home.profile.activity.contact.model.Message;

import java.util.List;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Message> messageList;
    private MessageClickListener messageClickListener;

    public ContactAdapter(Context context, MessageClickListener messageClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.messageClickListener = messageClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowContactBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.row_contact, parent, false);
        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (messageList == null || position == messageList.size())
            holder.mBinding.tvMessage.setText("Other");
        else
            holder.mBinding.tvMessage.setText(messageList.get(position).getSMessage());

        holder.mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageClickListener != null)
                    messageClickListener.onMessageClick(position == getItemCount() - 1 ? null : messageList.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (messageList == null)
            return 1;
        else
            return messageList.size() + 1;
    }

    public void setData(List<Message> activityList) {
        this.messageList = activityList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RowContactBinding mBinding;

        public ViewHolder(RowContactBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }

    public interface MessageClickListener {
        void onMessageClick(Message message, int position);
    }
}
