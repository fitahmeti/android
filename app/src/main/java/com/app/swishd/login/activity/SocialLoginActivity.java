package com.app.swishd.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.app.swishd.BuildConfig;
import com.app.swishd.R;
import com.app.swishd.baseutil.BaseFragmentActivity;
import com.app.swishd.home.MainActivity;
import com.app.swishd.login.model.SocialLoginModel;
import com.app.swishd.retrofit.ErrorUtils;
import com.app.swishd.retrofit.ResponseError;
import com.app.swishd.retrofit.call.ApiTask;
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
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.swishd.utility.EnumPreference.Authorization;

public abstract class SocialLoginActivity extends BaseFragmentActivity {

    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 1011;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.btn_login_with_facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFbButtonClick(v);
            }
        });
        findViewById(R.id.btn_login_with_google).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGoogleButtonClick(v);
            }
        });

        initFB();
        initGoogle();
    }

    private void initFB() {
        if (BuildConfig.DEBUG)
            FacebookSdk.setIsDebugEnabled(true);

        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

        LoginButton loginButton = (LoginButton) findViewById(R.id.fb_button);
        loginButton.setReadPermissions("email");
        loginButton.setReadPermissions("public_profile");

        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Log.d("Tag", "Facebook Token: " + loginResult.getAccessToken().getToken());


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
                                                 String profilePicUrl = "";
                                                 if (data.has("picture")) {
                                                     profilePicUrl = data.getJSONObject("picture").getJSONObject("data").optString("url");
                                                 }
                                                 String email = data.optString("email");
                                                 String firstName = data.optString("first_name");
                                                 String lastName = data.optString("last_name");
                                                 doFacebookLogin(id, profilePicUrl, email, firstName, lastName);
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

    private void initGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        toast(connectionResult.getErrorMessage());
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public void onFbButtonClick(View view) {
        LoginManager.getInstance().logOut();
        findViewById(R.id.fb_button).performClick();
    }

    public void onGoogleButtonClick(View view) {
        try {
            mGoogleApiClient.clearDefaultAccountAndReconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleGoogleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String id = acct.getId();
            String personPhotoUrl = "";
            if (acct.getPhotoUrl() != null)
                personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();
            String personName = acct.getDisplayName();
            doGoogleLogin(id, personPhotoUrl, email, personName);
        } else {
            // Signed out, show unauthenticated UI.
        }
    }

    private void doFacebookLogin(String id, String profileImageUrl, String email, String first_name, String last_name) {
        Log.d("Tag", "Facebook id:" + id);
        Log.d("Tag", "Facebook image:" + profileImageUrl);
        Log.d("Tag", "Facebook email:" + email);
        Log.d("Tag", "Facebook first-name:" + first_name);
        Log.d("Tag", "Facebook last-name:" + last_name);

        showProgressDialog();
        ApiTask.getInstance().doFacebookLogin(id, profileImageUrl, email, first_name, last_name, callbackFbLogin);
    }

    private void doGoogleLogin(String id, String profileImageUrl, String email, String name) {
        Log.d("Tag", "Google id:" + id);
        Log.d("Tag", "Google image:" + profileImageUrl);
        Log.d("Tag", "Google email:" + email);
        Log.d("Tag", "Google name:" + name);

        showProgressDialog();
        ApiTask.getInstance().doGoogleLogin(id, profileImageUrl, email, callbackGoogleLogin);
    }

    private Callback<SocialLoginModel> callbackFbLogin = new Callback<SocialLoginModel>() {
        @Override
        public void onResponse(Call<SocialLoginModel> call, Response<SocialLoginModel> response) {
            hideProgressDialog();
            if (call.isCanceled())
                return;
            if (response.isSuccessful()) {
                getPrefs().putData(Authorization, response.body().getAuthorization());
                toast(response.body().getMessage());
                navigateActivity(SocialLoginActivity.this, MainActivity.class, true);
            } else {
                ResponseError error = ErrorUtils.parseError(response);
                toast(error.getError());
            }
        }

        @Override
        public void onFailure(Call<SocialLoginModel> call, Throwable t) {
            hideProgressDialog();
            if (call.isCanceled())
                return;
            toast(t.getMessage());
        }
    };

    private Callback<SocialLoginModel> callbackGoogleLogin = callbackFbLogin;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

}
