package com.app.swishd.home.profile;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import com.app.swishd.BuildConfig;
import com.app.swishd.R;
import com.app.swishd.baseutil.BaseContainerFragment;
import com.app.swishd.baseutil.BaseFragment;
import com.app.swishd.databinding.FrgProfileBinding;
import com.app.swishd.home.notification.NotificationFragment;
import com.app.swishd.home.profile.edit.EditProfileFragment;
import com.app.swishd.home.profile.jobs.BaseJobFragment;
import com.app.swishd.home.profile.jobs.SenderJobListFragment;
import com.app.swishd.home.profile.jobs.SwisherJobListFragment;
import com.app.swishd.home.profile.model.ProfileModel;
import com.app.swishd.home.profile.model.UserProfile;
import com.app.swishd.home.profile.more.MoreFragment;
import com.app.swishd.home.profile.verify_id.QrCodeFragment;
import com.app.swishd.home.profile.verify_id.UploadIDFragment;
import com.app.swishd.home.profile.verify_mobile.MobileVerificationActivity;
import com.app.swishd.home.profile.wallet.WalletFragment;
import com.app.swishd.retrofit.ErrorUtils;
import com.app.swishd.retrofit.ResponseError;
import com.app.swishd.retrofit.call.Api;
import com.app.swishd.retrofit.call.ApiTask;
import com.app.swishd.utility.EnumPreference;
import com.app.swishd.utility.ImageUtil;
import com.app.swishd.widget.ProfileVerificationView;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends BaseFragment {
    public static final int RC_UPLOAD_ID = 1230;
    public static final int RC_QR_CODE = 1231;
    public static final int RC_EDIT_PROFILE = 1232;
    private static final int RC_SIGN_IN = 1235;
    private static final int REQUEST_CODE_VERIFY_MOBILE = 1236;
    private static ProfileFragment profileFragment;
    private FrgProfileBinding mBinding;
    private UserProfile profileData;
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;
    private JobsAdapter jobsAdapter;
    private View.OnClickListener senderSwishrClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.isSelected()) //do nothing if already selected
                return;
            if (view == mBinding.btnSender) {
                mBinding.btnSender.setSelected(true);
                mBinding.btnSwisher.setSelected(false);
                mBinding.tvSendrCount.setSelected(true);
                mBinding.tvSwishrCount.setSelected(false);
                mBinding.jobViewPager.setCurrentItem(1);
                if (jobsAdapter != null)
                    jobsAdapter.getCurrentFragment().reloadJobListIfNeeded();
            } else {
                mBinding.btnSwisher.setSelected(true);
                mBinding.btnSender.setSelected(false);
                mBinding.tvSwishrCount.setSelected(true);
                mBinding.tvSendrCount.setSelected(false);
                mBinding.jobViewPager.setCurrentItem(0);
                if (jobsAdapter != null)
                    jobsAdapter.getCurrentFragment().reloadJobListIfNeeded();
            }
        }
    };
    private View.OnClickListener notificationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NotificationFragment fragment = new NotificationFragment();
            ((BaseContainerFragment) getParentFragment()).addFragment(fragment, true);
        }
    };
    private View.OnClickListener walletListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            WalletFragment fragment = new WalletFragment();
            ((BaseContainerFragment) getParentFragment()).addFragment(fragment, true);
        }
    };
    private View.OnClickListener editProfileListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditProfileFragment fragment = new EditProfileFragment();
            fragment.setTargetFragment(ProfileFragment.this, RC_EDIT_PROFILE);
            ((BaseContainerFragment) getParentFragment()).addFragment(fragment, true);
        }
    };
    private View.OnClickListener moreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((BaseContainerFragment) getParentFragment()).addFragment(new MoreFragment(), true);
        }
    };
    private Callback<Object> callbackConnectEmail = new Callback<Object>() {
        @Override
        public void onResponse(Call<Object> call, Response<Object> response) {
            dismissProgressDialog();
            if (call.isCanceled())
                return;
            if (response.isSuccessful()) {
                profileData.setEmailVerify(Long.valueOf(1));
                getPrefs().putData(EnumPreference.MY_PROFILE, profileData.toString());
                mBinding.verifyViewEmail.setVerified(true);
            } else {
                ResponseError error = ErrorUtils.parseError(response);
                toast(error.getError());
            }
        }

        @Override
        public void onFailure(Call<Object> call, Throwable t) {
            dismissProgressDialog();
            if (call.isCanceled())
                return;
            toast(t.getMessage());
        }
    };
    private Callback<Object> callbackConnectFacebook = new Callback<Object>() {
        @Override
        public void onResponse(Call<Object> call, Response<Object> response) {
            dismissProgressDialog();
            if (call.isCanceled())
                return;
            if (response.isSuccessful()) {
                profileData.setFacebookVerify(Long.valueOf(1));
                getPrefs().putData(EnumPreference.MY_PROFILE, profileData.toString());
                mBinding.verifyViewFacebook.setVerified(true);
            } else {
                ResponseError error = ErrorUtils.parseError(response);
                toast(error.getError());
            }
        }

        @Override
        public void onFailure(Call<Object> call, Throwable t) {
            dismissProgressDialog();
            if (call.isCanceled())
                return;
            toast(t.getMessage());
        }
    };
    private AuthListener callbackLinkedIn = new AuthListener() {
        @Override
        public void onAuthSuccess() {
            toast("LinkedIn Connect successful");
            Log.d("Tag", "LinkedIn token: " + LISessionManager.getInstance(getContext()).getSession().getAccessToken().getValue());
            APIHelper.getInstance(getContext()).getRequest(getContext(), "https://api.linkedin.com/v1/people/~?format=json", new ApiListener() {
                @Override
                public void onApiSuccess(ApiResponse apiResponse) {
                    try {
                        String userId = apiResponse.getResponseDataAsJson().getString("id");
                        Log.d("Tag", "LinkedIn User ID: " + userId);

                        showProgressDialog();
                        getApiTask().connectLinkedIn(userId, new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                dismissProgressDialog();
                                if (call.isCanceled())
                                    return;
                                if (response.isSuccessful()) {
                                    profileData.setLinkedinVerify(Long.valueOf(1));
                                    getPrefs().putData(EnumPreference.MY_PROFILE, profileData.toString());
                                    mBinding.verifyViewLinkedin.setVerified(true);
                                } else {
                                    ResponseError error = ErrorUtils.parseError(response);
                                    showSnackBar(error.getError());
                                }
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {
                                dismissProgressDialog();
                                if (call.isCanceled())
                                    return;
                                showSnackBar(t.getMessage());
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        showSnackBar("LinkedIn connect fail");
                    }
                }

                @Override
                public void onApiError(LIApiError LIApiError) {
                    showSnackBar(LIApiError.getMessage());
                }
            });
        }

        @Override
        public void onAuthError(LIAuthError error) {
            showSnackBar("LinkedIn connect fail");
            Log.d("Tag", error.toString());
        }
    };
    private View.OnClickListener verifyViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!((ProfileVerificationView) v).isVerified()) {
                if (v == mBinding.verifyViewEmail) {
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                } else if (v == mBinding.verifyViewFacebook) {
                    LoginManager.getInstance().logOut();
                    mBinding.fbButton.performClick();
                } else if (v == mBinding.verifyViewLinkedin) {
                    try {
                        LISessionManager.getInstance(getContext()).clearSession();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    LISessionManager.getInstance(getContext()).init(getActivity(), Scope.build(Scope.R_BASICPROFILE), callbackLinkedIn, false);
                } else if (v == mBinding.verifyViewMobile) {
                    startActivityForResult(new Intent(getContext(), MobileVerificationActivity.class), REQUEST_CODE_VERIFY_MOBILE);
                } else if (v == mBinding.verifyViewProof) {
                    if (profileData.getProofData() == null) {
                        UploadIDFragment fragment = new UploadIDFragment();
                        fragment.setTargetFragment(ProfileFragment.this, RC_UPLOAD_ID);
                        ((BaseContainerFragment) getParentFragment()).addFragment(fragment, true);
                    } else {
                        QrCodeFragment fragment = new QrCodeFragment();
                        fragment.setTargetFragment(ProfileFragment.this, RC_QR_CODE);
                        ((BaseContainerFragment) getParentFragment()).addFragment(fragment, true);
                    }
                }
            }
        }
    };
    private Callback<ProfileModel> callbackProfile = new Callback<ProfileModel>() {
        @Override
        public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
            dismissProgressDialog();
            if (call.isCanceled())
                return;
            if (response.isSuccessful()) {
                profileData = response.body().getUserProfile();
                getPrefs().putData(EnumPreference.MY_PROFILE, profileData.toString());
                displayProfile();
            } else {
                ResponseError error = ErrorUtils.parseError(response);
                toast(error.getError());
            }
        }

        @Override
        public void onFailure(Call<ProfileModel> call, Throwable t) {
            dismissProgressDialog();
            if (call.isCanceled())
                return;
            toast(t.getMessage());
        }
    };

    public static ProfileFragment getInstance() {
        if (profileFragment == null)
            profileFragment = new ProfileFragment();
        return profileFragment;
    }

    public static ProfileFragment newInstance() {
        profileFragment = new ProfileFragment();
        return profileFragment;
    }

    @Override
    public int getLayout() {
        return R.layout.frg_profile;
    }

    @Override
    public void setBinding(ViewDataBinding binding) {
        mBinding = (FrgProfileBinding) binding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBinding.swipeRefreshLayout.setProgressViewOffset(false, getResources().getDimensionPixelSize(R.dimen._42sdp), getResources().getDimensionPixelSize(R.dimen._42sdp) + mBinding.swipeRefreshLayout.getProgressViewEndOffset());
        mBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBinding.swipeRefreshLayout.setRefreshing(false);
                loadProfile();
            }
        });

        mBinding.appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mBinding.swipeRefreshLayout.setEnabled(verticalOffset == 0);
            }
        });

        mBinding.tvSendrCount.setVisibility(View.VISIBLE);
        mBinding.tvSendrCount.setAlpha(0);
        mBinding.tvSendrCount.setSelected(false);
        mBinding.btnSender.setSelected(false);
        mBinding.btnSender.setOnClickListener(senderSwishrClickListener);
        mBinding.tvSwishrCount.setVisibility(View.VISIBLE);
        mBinding.tvSwishrCount.setAlpha(0);
        mBinding.tvSwishrCount.setSelected(true);
        mBinding.btnSwisher.setSelected(true);
        mBinding.btnSwisher.setOnClickListener(senderSwishrClickListener);

        mBinding.btnNotification.setOnClickListener(notificationClickListener);
        mBinding.btnWallet.setOnClickListener(walletListener);
        mBinding.btnEditProfile.setOnClickListener(editProfileListener);
        mBinding.btnMore.setOnClickListener(moreClickListener);

        mBinding.verifyViewEmail.setOnClickListener(verifyViewClickListener);
        mBinding.verifyViewFacebook.setOnClickListener(verifyViewClickListener);
        mBinding.verifyViewLinkedin.setOnClickListener(verifyViewClickListener);
        mBinding.verifyViewMobile.setOnClickListener(verifyViewClickListener);
        mBinding.verifyViewProof.setOnClickListener(verifyViewClickListener);

        initGoogle();
        initFB();

        if (profileData == null)
            loadProfile();
        else
            displayProfile();
    }

    private void loadProfile() {
        showProgressDialog();
        getApiTask().getProfile(callbackProfile);
    }

    private void initGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        toast(connectionResult.getErrorMessage());
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void initFB() {
        if (BuildConfig.DEBUG)
            FacebookSdk.setIsDebugEnabled(true);

        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

        mBinding.fbButton.setReadPermissions("email");
        mBinding.fbButton.setReadPermissions("public_profile");

        callbackManager = CallbackManager.Factory.create();
        mBinding.fbButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Get profile data
                Bundle params = new Bundle();
                params.putString("fields", "id,picture.type(large),email,name,first_name,last_name");
                new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                                 new GraphRequest.Callback() {
                                     @Override
                                     public void onCompleted(GraphResponse response) {
                                         if (response != null) {
                                             try {
                                                 JSONObject data = response.getJSONObject();
                                                 String id = data.optString("id");
                                                 //facebookEmail = data.optString("email");
                                                 showProgressDialog();
                                                 ApiTask.getInstance().connectFacebook(id, callbackConnectFacebook);
                                             } catch (Exception e) {
                                                 e.printStackTrace();
                                             }
                                         }
                                     }
                                 }).executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
                toast(exception.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
        } else if (requestCode == LISessionManager.LI_SDK_AUTH_REQUEST_CODE) {
            LISessionManager.getInstance(getContext()).onActivityResult(getActivity(), requestCode, resultCode, data);
        } else if (requestCode == REQUEST_CODE_VERIFY_MOBILE) {
            if (resultCode == Activity.RESULT_OK) {
                profileData = UserProfile.fromString(getPrefs().getStringData(EnumPreference.MY_PROFILE));
                mBinding.verifyViewMobile.setVerified(true);
            }
        } else if (requestCode == RC_UPLOAD_ID || requestCode == RC_QR_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                profileData = UserProfile.fromString(getPrefs().getStringData(EnumPreference.MY_PROFILE));
            }
        } else if (requestCode == RC_EDIT_PROFILE) {
            if (resultCode == Activity.RESULT_OK) {
                profileData = UserProfile.fromString(getPrefs().getStringData(EnumPreference.MY_PROFILE));
                displayProfile();
            }
        } else if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleGoogleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String id = acct.getId();
            //verifiedEmailAddress = acct.getEmail();

            showProgressDialog();
            ApiTask.getInstance().connectEmailAddress(id, callbackConnectEmail);
        } else {
            // Signed out, show unauthenticated UI.
        }
    }

    private void displayProfile() {
        mBinding.setProfile(profileData);
        if (profileData.getProfileImage() != null && !profileData.getProfileImage().isEmpty())
            ImageUtil.load(Api.BASE_URL_IMAGE + profileData.getProfileImage(), mBinding.imgProfile, R.drawable.ic_person);

        if (mBinding.jobViewPager.getAdapter() == null) {
            jobsAdapter = new JobsAdapter(getChildFragmentManager());
            mBinding.jobViewPager.setAdapter(jobsAdapter);
        } else {
            jobsAdapter.fragments[0].reloadJobList();
            jobsAdapter.fragments[1].reloadJobList();
        }
    }

    public void setSenderCount(int count) {
        if (count > 0) {
            mBinding.tvSendrCount.setText(String.valueOf(count));
            mBinding.tvSendrCount.setAlpha(1);
        } else {
            mBinding.tvSendrCount.setAlpha(0);
        }
    }

    public void setSwishrCount(int count) {
        if (count > 0) {
            mBinding.tvSwishrCount.setText(String.valueOf(count));
            mBinding.tvSwishrCount.setAlpha(1);
        } else {
            mBinding.tvSwishrCount.setAlpha(0);
        }
    }

    private class JobsAdapter extends FragmentStatePagerAdapter {
        private BaseJobFragment[] fragments = new BaseJobFragment[2];

        public JobsAdapter(FragmentManager fm) {
            super(fm);
            fragments[0] = new SwisherJobListFragment();
            fragments[1] = new SenderJobListFragment();
        }

        public BaseJobFragment getCurrentFragment() {
            return fragments[mBinding.jobViewPager.getCurrentItem()];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}