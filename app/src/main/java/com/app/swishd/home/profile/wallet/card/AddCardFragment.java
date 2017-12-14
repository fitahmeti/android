package com.app.swishd.home.profile.wallet.card;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgAddCardBinding;
import com.app.swishd.home.profile.model.UserProfile;
import com.stripe.android.CustomerSession;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Customer;
import com.stripe.android.model.Source;
import com.stripe.android.model.Token;

import org.json.JSONException;
import org.json.JSONObject;


public class AddCardFragment extends BaseFragment {
    private FrgAddCardBinding mBinding;

    @Override
    public int getLayout() {
        return R.layout.frg_add_card;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgAddCardBinding) binding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragment().getChildFragmentManager().popBackStack();
            }
        });

        mBinding.btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Card card = mBinding.cardInputWidget.getCard();
                if (card == null) {
                    showSnackBar("Invalid card info");
                    return;
                }

                mBinding.progressView.setVisibility(View.VISIBLE);
                Stripe stripe = new Stripe(getContext(), getString(R.string.stripe_key));
                stripe.createToken(
                        card,
                        new TokenCallback() {
                            public void onSuccess(Token token) {
                                Log.d("Tag", "Stripe Token: " + token.getId());
                                Log.d("Tag", "Stripe Customer ID: " + token.getCard().getCustomerId());


                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("source", token.getId());
                                    jsonObject.put("description", "This is test customer");

                                    Customer customer = Customer.fromJson(jsonObject);
                                    Log.d("Tag", "Card Customer ID: " + card.getCustomerId());
//                                    Log.d("Tag", "Customer ID: " + customer.getId());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    showSnackBar("Something went wrong..");
                                    mBinding.progressView.setVisibility(View.GONE);
                                }
                            }

                            public void onError(Exception error) {
                                showSnackBar(error.getMessage());
                                mBinding.progressView.setVisibility(View.GONE);
                            }
                        }
                );
            }
        });
    }
}
