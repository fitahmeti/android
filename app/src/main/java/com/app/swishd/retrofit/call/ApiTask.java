package com.app.swishd.retrofit.call;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.app.swishd.R;
import com.app.swishd.home.notification.model.NotificationModel;
import com.app.swishd.home.profile.activity.contact.model.ContactModel;
import com.app.swishd.home.profile.model.ActivityModel;
import com.app.swishd.home.profile.model.AddReceivedModel;
import com.app.swishd.home.profile.model.EditProfileModel;
import com.app.swishd.home.profile.model.GetNotificationSettingsModel;
import com.app.swishd.home.profile.model.JobListModel;
import com.app.swishd.home.profile.model.ProfileModel;
import com.app.swishd.home.profile.model.VerificationQRModel;
import com.app.swishd.home.profile.wallet.bank.AddAccountModel;
import com.app.swishd.home.profile.wallet.model.BankAccountModel;
import com.app.swishd.home.profile.wallet.model.HistoryModel;
import com.app.swishd.home.search.model.FindJobModel;
import com.app.swishd.home.send.model.CreateItemModel;
import com.app.swishd.login.model.SocialLoginModel;
import com.app.swishd.retrofit.ApiCallback;
import com.app.swishd.retrofit.OnResponseCallback;
import com.app.swishd.retrofit.RequestCode;
import com.app.swishd.retrofit.RetrofitApp;
import com.app.swishd.utility.MyPrefs;
import com.app.swishd.utility.Utility;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.app.swishd.retrofit.RequestCode.REQUEST_ACCEPT_JOB;
import static com.app.swishd.retrofit.RequestCode.REQUEST_CONFIRM_JOB;
import static com.app.swishd.retrofit.RequestCode.REQUEST_DELETE_SEARCH;
import static com.app.swishd.retrofit.RequestCode.REQUEST_GET_JOBS;
import static com.app.swishd.retrofit.RequestCode.REQUEST_JOB_DETAILS;
import static com.app.swishd.retrofit.RequestCode.REQUEST_REMOVE_JOB;
import static com.app.swishd.retrofit.RequestCode.REQUEST_SCAN_JOB;
import static com.app.swishd.utility.EnumPreference.Authorization;
import static com.app.swishd.utility.EnumPreference.PUSH_TOKEN;

public class ApiTask {

    private static ApiTask apiTask;
    private final ApiCall apiCall;
    private final Context context;

    private ApiTask() {
        Retrofit retrofit = RetrofitApp.getInstance();
        if (retrofit == null)
            Log.e("Tag", "Retrofit not initialized, please call RetrofitApp.initRetrofit() in your application's onCreate()");
        apiCall = retrofit.create(ApiCall.class);
        context = RetrofitApp.getContext();
    }

    public static ApiTask getInstance() {
        if (apiTask == null)
            apiTask = new ApiTask();
        return apiTask;
    }

    public Call<SocialLoginModel> doFacebookLogin(String id, String profile_image, String email, String first_name, String last_name, Callback<SocialLoginModel> callback) {
        Call<SocialLoginModel> call = apiCall.doFacebookLogin(id, profile_image, email, first_name, last_name);
        call.enqueue(callback);
        return call;
    }

    public Call<SocialLoginModel> doGoogleLogin(String id, String profile_image, String email, Callback<SocialLoginModel> callback) {
        Call<SocialLoginModel> call = apiCall.doGoogleLogin(id, profile_image, email);
        call.enqueue(callback);
        return call;
    }

    public Call<Object> savePushToken() {
        Call<Object> call = apiCall.savePushToken(FirebaseInstanceId.getInstance().getToken(), "Android", getAuthToken());
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!call.isCanceled() && response.isSuccessful())
                    MyPrefs.getInstance().putData(PUSH_TOKEN, true);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
            }
        });
        return call;
    }

    public Call<Object> logout(Callback<Object> callback) {
        Call<Object> call = apiCall.logout(getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<ProfileModel> getProfile(Callback<ProfileModel> callback) {
        Call<ProfileModel> call = apiCall.getProfile(getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<Object> connectEmailAddress(String googleId, Callback<Object> callback) {
        Call<Object> call = apiCall.connectEmail(googleId, getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<Object> connectFacebook(String facebookId, Callback<Object> callback) {
        Call<Object> call = apiCall.connectFacebook(facebookId, getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<Object> connectLinkedIn(String linkedInId, Callback<Object> callback) {
        Call<Object> call = apiCall.connectLinkedIn(linkedInId, getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<Object> verifyMobile(String mobile, String countryCode, Callback<Object> callback) {
        Call<Object> call = apiCall.verifyMobile(mobile, countryCode, getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<Object> uploadIDProof(String verify_id_proof, String verify_address_proof, Callback<Object> callback) {
        Call<Object> call = apiCall.uploadIDProof(getFile("verify_id_proof", new File(verify_id_proof)), getFile("verify_address_proof", new File(verify_address_proof)), getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<VerificationQRModel> getIDProof(Callback<VerificationQRModel> callback) {
        Call<VerificationQRModel> call = apiCall.getIDProof(getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<EditProfileModel> editProfile(String username, String first_name, String last_name, String email, String image, Callback<EditProfileModel> callback) {
        Call<EditProfileModel> call;
        if (image == null)
            call = apiCall.editProfile(username, first_name, last_name, email, getAuthToken());
        else
            call = apiCall.editProfile(getPart(username), getPart(first_name), getPart(last_name), getPart(email), getFile("profile_image", new File(image)), getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<JobListModel> getMySenders(int iStart, int iLimit, Callback<JobListModel> callback) {
        Call<JobListModel> call = apiCall.getMySenders(iStart, iLimit, "date", getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<JobListModel> getMySwishers(int iStart, int iLimit, Callback<JobListModel> callback) {
        Call<JobListModel> call = apiCall.getMySwishers(iStart, iLimit, "date", getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<GetNotificationSettingsModel> getNotificationSettings(Callback<GetNotificationSettingsModel> callback) {
        Call<GetNotificationSettingsModel> call = apiCall.getNotificationSettings(getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<Object> updateNotificationSetting(String sNotificationId, boolean eStatus, Callback<Object> callback) {
        Call<Object> call = apiCall.updateNotificationSetting(sNotificationId, eStatus, getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<ActivityModel> jobActivity(String jobID, Callback<ActivityModel> callback) {
        Call<ActivityModel> call = apiCall.jobActivity(jobID, getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<ContactModel> getSwishrMessages(Callback<ContactModel> callback) {
        Call<ContactModel> call = apiCall.getMessages("swishr", getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<ContactModel> getSenderMessages(Callback<ContactModel> callback) {
        Call<ContactModel> call = apiCall.getMessages("sender", getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<Object> sendMessage(String jobID, String message, Callback<Object> callback) {
        Call<Object> call = apiCall.sendMessage(jobID, message, getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<Object> sendPredefinedMessage(String jobID, String messageId, Callback<Object> callback) {
        Call<Object> call = apiCall.sendPredefinedMessage(jobID, messageId, getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<HistoryModel> getTransactionHistory(int start, int limit, Callback<HistoryModel> callback) {
        Call<HistoryModel> call = apiCall.getTransactionHistory(start, limit, getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<AddAccountModel> addBankAccount(String accountName, String accountNumber, String sortCode, Callback<AddAccountModel> callback) {
        Call<AddAccountModel> call = apiCall.addBankAccount(accountName, accountNumber, sortCode, getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<BankAccountModel> getBankAccounts(Callback<BankAccountModel> callback) {
        Call<BankAccountModel> call = apiCall.getBankAccounts(getAuthToken());
        call.enqueue(callback);
        return call;
    }

    public Call<NotificationModel> getNotificationLog(int start,int limit,Callback<NotificationModel> callback) {
        Call<NotificationModel> call = apiCall.getNotificationLog(start,limit,getAuthToken());
        call.enqueue(callback);
        return call;
    }

    private String getAuthToken() {
        return MyPrefs.getInstance().getStringData(Authorization);
    }


    public DisposableObserver doLogin(Context context,
                                      String email, String password, OnResponseCallback mCallback) {

        if (isInternetAvailable(context)) {
            DisposableObserver request = apiCall.doLogin(email, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver doSignUp(Context context, String username,
                                       String email, String password, String first_name,
                                       String last_name, String promoCode, OnResponseCallback mCallback) {

        if (isInternetAvailable(context)) {
            DisposableObserver request = apiCall.doRegister(username, email, password, first_name, last_name, promoCode)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback, RequestCode.REQUEST_SIGN_UP));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }


    public DisposableObserver<?> doForgotPassword(Context context,
                                                  String email, OnResponseCallback mCallback) {

        if (isInternetAvailable(context)) {
            DisposableObserver request = apiCall.doForgotPassword(email)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver doCheckEmail(Context context,
                                           String email, OnResponseCallback mCallback) {

        if (isInternetAvailable(context)) {
            DisposableObserver request = apiCall.doCheckEmail(email)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback, RequestCode.REQUEST_CHECK_EMAIL));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doCheckUsername(Context context,
                                                 String username, OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doCheckUsername(username)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doGetItemSizes(Context context,
                                                int iStart, int iLimit, OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doGetItemSizes(getAuthToken(), iStart, iLimit)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doGetOfficeList(Context context, String lat, String lng, String sort,
                                                 int iStart, int iLimit, OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doGetOfficeList(getAuthToken(), lat, lng, sort, iStart, iLimit)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doAddJob(Context context, CreateItemModel model, OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doAddJob(getAuthToken(), model)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doGetJobs(@NonNull Context context, FindJobModel model, OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doGetJobs(getAuthToken(), model)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback, REQUEST_GET_JOBS));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doGetSearchList(Context context, int start, int limit, OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doGetSearchList(getAuthToken(), start, limit)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback, RequestCode.REQUEST_SEARCH_LIST));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doGetMostUsedSearchList(Context context, OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doGetMostUsedSearch(getAuthToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback, RequestCode.REQUEST_GET_MOST_USED_SEARCH));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doChangedSearchStatus(Context context, String id, String status, OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doChangeSearchStatus(getAuthToken(), id, status)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback, RequestCode.REQUEST_CHANGE_SEARCH_STATUS));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doGetSearchDetails(Context context, String id, OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doGetSearchDetail(getAuthToken(), id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback, RequestCode.REQUEST_SEARCH_DETAILS));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doEditSearch(Context context, FindJobModel model,
                                              OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doEditSearch(getAuthToken(), model)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback, RequestCode.REQUEST_EDIT_SEARCH));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doDeleteSearch(@NonNull Context context, String id, OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doDeleteSearch(getAuthToken(), id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback, REQUEST_DELETE_SEARCH));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doHideJob(@NonNull Context context, String id, OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doHideJob(getAuthToken(), id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback, REQUEST_DELETE_SEARCH));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doScanConfirmJob(@NonNull Context context, String id, OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doScanConfirmJob(getAuthToken(), id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback, REQUEST_CONFIRM_JOB));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doScanJob(@NonNull Context context, String code,
                                           OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doScanJob(getAuthToken(), code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback, REQUEST_SCAN_JOB));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }


    public DisposableObserver<?> doGetJobDetails(@NonNull Context context, String jobId,
                                                 OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doGetJobDetails(getAuthToken(), jobId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback, REQUEST_JOB_DETAILS));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doAcceptJob(@NonNull Context context, String jobId,
                                             String date, OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doAcceptJob(getAuthToken(), jobId, date)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback, REQUEST_ACCEPT_JOB));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doRemoveJob(@NonNull Context context, String jobId,
                                             OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doRemoveJob(getAuthToken(), jobId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback, REQUEST_REMOVE_JOB));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doInviteFriend(@NonNull Context context, ArrayList<String> inviteList,
                                                OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doInviteFriend(getAuthToken(), inviteList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doGetOfferList(@NonNull Context context, String sJobId, String sortBy,
                                                OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doGetOfferList(getAuthToken(), sJobId, sortBy)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doAcceptRejectOffer(@NonNull Context context, String sJobId, String sUserId,
                                                     String eOfferStatus, OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doOfferResponse(getAuthToken(), sJobId, sUserId, eOfferStatus)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public DisposableObserver<?> doAddRecipientDetails(@NonNull Context context, AddReceivedModel model,
                                                       OnResponseCallback mCallback) {
        DisposableObserver<?> request;
        if (isInternetAvailable(context)) {
            request = apiCall.doAddRecipient(getAuthToken(), model)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ApiCallback<>(mCallback));
            return request;
        } else {
            mCallback.onResponseError(noInternetError(context));
            return null;
        }
    }

    public boolean isInternetAvailable(Context context) {
        return Utility.isNetworkAvailable(context);
    }

    public String noInternetError(Context context) {
        return context.getResources().getString(R.string.e_no_internet);
    }

    private MultipartBody.Part getFile(String fieldName, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(fieldName, file.getName(), requestFile);
        return body;
    }

    private RequestBody getPart(String value) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("application/json"), value);
        return requestFile;
    }

}