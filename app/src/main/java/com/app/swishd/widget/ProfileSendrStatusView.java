package com.app.swishd.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.app.swishd.R;
import com.app.swishd.databinding.LayoutProfileSendrStatusBinding;

public class ProfileSendrStatusView extends FrameLayout {
    private String text;
    private TypeEnum type;
    private LayoutProfileSendrStatusBinding mBinding;

    public ProfileSendrStatusView(Context context) {
        super(context);
        init(null, 0);
    }

    public ProfileSendrStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ProfileSendrStatusView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }


    private void init(AttributeSet attrs, int defStyle) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_profile_sendr_status, this, true);

        //Read attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ProfileSendrStatusView, defStyle, 0);
        String textStr = a.getString(R.styleable.ProfileSendrStatusView_pssv_text);
        if (textStr != null)
            text = textStr;
        int typeValue = a.getInteger(R.styleable.ProfileSendrStatusView_pssv_type, TypeEnum.Date.getValue());
        type = TypeEnum.getEnum(typeValue);
        a.recycle();

        updateUI();
    }

    public void updateUI() {
        mBinding.imageView.setImageResource(type.getIcon());
        mBinding.textView.setText(text);
        invalidate();
        requestLayout();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        try {
            this.text = text;
            mBinding.textView.setText(text);
            invalidate();
            requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private enum TypeEnum {
        Date(1, R.drawable.ic_date),
        Time(2, R.drawable.ic_time),
        Address(3, R.drawable.ic_location);

        private int value;
        private int icon;

        TypeEnum(int value, int icon) {
            this.value = value;
            this.icon = icon;
        }

        public int getValue() {
            return value;
        }

        public int getIcon() {
            return icon;
        }

        public static TypeEnum getEnum(int typeValue) {
            switch (typeValue) {
                case 1:
                    return TypeEnum.Date;
                case 2:
                    return TypeEnum.Time;
                case 3:
                    return TypeEnum.Address;
            }
            return TypeEnum.Date;
        }
    }
}
