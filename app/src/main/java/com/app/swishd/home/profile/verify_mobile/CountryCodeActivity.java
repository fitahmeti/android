package com.app.swishd.home.profile.verify_mobile;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragmentActivity;
import com.app.swishd.databinding.ActCountryCodeBinding;
import com.app.swishd.databinding.RowCountryCodeBinding;
import com.app.swishd.home.profile.model.CountryCode;
import com.app.swishd.home.profile.model.CountryCodeModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CountryCodeActivity extends BaseFragmentActivity {

    public static final String INTENT_EXTRA_COUNTRY_CODE = "EXTRA_COUNTRY_CODE";
    public static final String INTENT_EXTRA_COUNTRY_NAME = "EXTRA_COUNTRY_NAME";
    private ActCountryCodeBinding mBinding;
    private static List<CountryCode> countryCodes;

    @Override
    public int getLayout() {
        return R.layout.act_country_code;
    }

    @Override
    public void setBinding(ViewDataBinding bindingObject) {
        mBinding = (ActCountryCodeBinding) bindingObject;
    }

    @Override
    public void init() {
        mBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (countryCodes == null)
            countryCodes = getCountryCodes();

        DividerItemDecoration dividerDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        mBinding.rvCodes.addItemDecoration(dividerDecoration);
        mBinding.rvCodes.setAdapter(new CountryCodeAdapter(this));
    }

    private List<CountryCode> getCountryCodes() {
        String json = null;
        try {
            InputStream is = getAssets().open("countries.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (json != null) {
            CountryCodeModel countryModel = new Gson().fromJson(json, CountryCodeModel.class);
            if (countryModel != null)
                return countryModel.getList();
        }
        return new ArrayList<>();
    }

    private class CountryCodeAdapter extends RecyclerView.Adapter<CountryCodeAdapter.ViewHolder> {
        private final LayoutInflater inflater;
        private Context context;

        public CountryCodeAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RowCountryCodeBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.row_country_code, parent, false);
            return new ViewHolder(mBinding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.mBinding.setCountry(countryCodes.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(INTENT_EXTRA_COUNTRY_CODE, countryCodes.get(position).getDialCode());
                    intent.putExtra(INTENT_EXTRA_COUNTRY_NAME, countryCodes.get(position).getName());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return countryCodes.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private final RowCountryCodeBinding mBinding;

            public ViewHolder(RowCountryCodeBinding mBinding) {
                super(mBinding.getRoot());
                this.mBinding = mBinding;
            }
        }
    }
}
