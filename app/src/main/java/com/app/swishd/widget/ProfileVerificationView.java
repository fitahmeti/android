package com.app.swishd.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.app.swishd.R;
import com.app.swishd.databinding.LayoutProfileVerificationBinding;

public class ProfileVerificationView extends FrameLayout {
    private String text, verifiedText;
    private boolean verified;
    private LayoutProfileVerificationBinding mBinding;

    public ProfileVerificationView(Context context) {
        super(context);
        init(null, 0);
    }

    public ProfileVerificationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ProfileVerificationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }


    private void init(AttributeSet attrs, int defStyle) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_profile_verification, this, true);

        //Read attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ProfileVerificationView, defStyle, 0);
        text = a.getString(R.styleable.ProfileVerificationView_text);
        verifiedText = a.getString(R.styleable.ProfileVerificationView_verified_text);
        verified = a.getBoolean(R.styleable.ProfileVerificationView_verified, false);
        a.recycle();

        updateUI();
    }

    public void updateUI() {
        mBinding.imgVerified.setVisibility(verified ? VISIBLE : GONE);
        mBinding.imgMore.setVisibility(verified ? GONE : VISIBLE);
        mBinding.tvTitle.setText(verified ? verifiedText : text);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        updateUI();
    }

    public String getVerifiedText() {
        return verifiedText;
    }

    public void setVerifiedText(String verifiedText) {
        this.verifiedText = verifiedText;
        updateUI();
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
        updateUI();
    }
}
