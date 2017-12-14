package com.app.swishd.retrofit.call;

import com.app.swishd.home.notification.model.NotificationModel;
import com.app.swishd.home.profile.activity.contact.model.ContactModel;
import com.app.swishd.home.profile.model.ActivityModel;
import com.app.swishd.home.profile.model.AddReceivedModel;
import com.app.swishd.home.profile.model.EditProfileModel;
import com.app.swishd.home.profile.model.GetNotificationSettingsModel;
import com.app.swishd.home.profile.model.JobListModel;
import com.app.swishd.home.profile.model.ProfileModel;
import com.app.swishd.home.profile.model.VerificationQRModel;
import com.app.swishd.home.profile.model.offer.ResponseOfferList;
import com.app.swishd.home.profile.wallet.bank.AddAccountModel;
import com.app.swishd.home.profile.wallet.model.BankAccountModel;
import com.app.swishd.home.profile.wallet.model.HistoryModel;
import com.app.swishd.home.search.model.FindJobModel;
import com.app.swishd.home.search.model.JobDetails.ResponseJobDetails;
import com.app.swishd.home.search.model.ResponseSearchList;
import com.app.swishd.home.search.model.job.ResponseJobList;
import com.app.swishd.home.search.model.qrScan.ResponseQRScan;
import com.app.swishd.home.search.model.searchDetails.ResponseSearchDetail;
import com.app.swishd.home.send.model.CreateItemModel;
import com.app.swishd.home.send.model.ResponseItemSize;
import com.app.swishd.home.send.model.ResponseOfficeList;
import com.app.swishd.login.model.ResponseLogin;
import com.app.swishd.login.model.ResponseSuccess;
import com.app.swishd.login.model.SocialLoginModel;
import com.app.swishd.retrofit.ResponseError;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiCall {

    @FormUrlEncoded
    @POST(Api.LOGIN)
    Call<SocialLoginModel> doFacebookLogin(@Field("facebook_id") String id, @Field("profile_image") String profile_image, @Field("email") String email, @Field("first_name") String first_name, @Field("last_name") String last_name);

    @FormUrlEncoded
    @POST(Api.LOGIN)
    Call<SocialLoginModel> doGoogleLogin(@Field("google_id") String id, @Field("profile_image") String profile_image, @Field("email") String email);

    @FormUrlEncoded
    @POST(Api.SAVE_PUSH_TOKEN)
    Call<Object> savePushToken(@Field("sPushToken") String sPushToken, @Field("sDeviceType") String sDeviceType, @Header("Authorization") String authorization);

    @GET(Api.LOGOUT)
    Call<Object> logout(@Header("Authorization") String authorization);

    @GET(Api.PROFILE)
    Call<ProfileModel> getProfile(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST(Api.SOCIAL_CONNECT)
    Call<Object> connectEmail(@Field("google_id") String emailAddress, @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST(Api.SOCIAL_CONNECT)
    Call<Object> connectFacebook(@Field("facebook_id") String emailAddress, @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST(Api.SOCIAL_CONNECT)
    Call<Object> connectLinkedIn(@Field("linkedin_id") String emailAddress, @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST(Api.VERIFY_MOBILE)
    Call<Object> verifyMobile(@Field("mobile") String mobile, @Field("countryCode") String countryCode, @Header("Authorization") String authorization);

    @Multipart
    @POST(Api.UPLOAD_ID_PROOF)
    Call<Object> uploadIDProof(@Part MultipartBody.Part verify_id_proof, @Part MultipartBody.Part verify_address_proof, @Header("Authorization") String authorization);

    @GET(Api.GET_ID_PROOF)
    Call<VerificationQRModel> getIDProof(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST(Api.PROFILE)
    Call<EditProfileModel> editProfile(@Field("username") String username, @Field("first_name") String first_name, @Field("last_name") String last_name, @Field("email") String email, @Header("Authorization") String authorization);

    @Multipart
    @POST(Api.PROFILE)
    Call<EditProfileModel> editProfile(@Part("username") RequestBody username, @Part("first_name") RequestBody first_name, @Part("last_name") RequestBody last_name, @Part("email") RequestBody email, @Part MultipartBody.Part profile_image, @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST(Api.SENDERS)
    Call<JobListModel> getMySenders(@Field("iStart") int iStart, @Field("iLimit") int iLimit, @Field("sortBy") String sortBy, @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST(Api.SWISHERS)
    Call<JobListModel> getMySwishers(@Field("iStart") int iStart, @Field("iLimit") int iLimit, @Field("sortBy") String sortBy, @Header("Authorization") String authorization);

    @GET(Api.SETTINGS)
    Call<GetNotificationSettingsModel> getNotificationSettings(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @PUT(Api.SETTING)
    Call<Object> updateNotificationSetting(@Field("sNotificationId") String sNotificationId, @Field("eStatus") boolean eStatus, @Header("Authorization") String authorization);

    @GET(Api.JOB_ACTIVITY)
    Call<ActivityModel> jobActivity(@Path("JOB_ID") String jobID, @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST(Api.MESSAGES)
    Call<ContactModel> getMessages(@Field("sMessageFor") String sMessageFor, @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST(Api.SEND_MESSAGE)
    Call<Object> sendMessage(@Field("sJobId") String sJobId, @Field("sMessage") String sMessage, @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST(Api.SEND_MESSAGE)
    Call<Object> sendPredefinedMessage(@Field("sJobId") String sJobId, @Field("sMessageId") String sMessageId, @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST(Api.TRANSACTION_HISTORY)
    Call<HistoryModel> getTransactionHistory(@Field("start") int start, @Field("limit") int limit, @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST(Api.BANK_ACCOUNTS)
    Call<AddAccountModel> addBankAccount(@Field("sAccountName") String sAccountName, @Field("sAccountNumber") String sAccountNumber, @Field("sSortCode") String sSortCode, @Header("Authorization") String authorization);

    @GET(Api.BANK_ACCOUNTS)
    Call<BankAccountModel> getBankAccounts(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST(Api.NOTIFICATION_LOG)
    Call<NotificationModel> getNotificationLog(@Field("start") int start, @Field("limit") int limit, @Header("Authorization") String authorization);


    @FormUrlEncoded
    @POST(Api.LOGIN)
    Observable<Response<ResponseLogin>> doLogin(
            @Field("email") String mEmail,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST(Api.FORGOT_PASSWORD)
    Observable<Response<ResponseSuccess>> doForgotPassword(
            @Field("email") String mEmail
    );

    @FormUrlEncoded
    @POST(Api.REGISTER)
    Observable<Response<ResponseSuccess>> doRegister(
            @Field("username") String mUsername,
            @Field("email") String mEmail,
            @Field("password") String mPassword,
            @Field("first_name") String mFirst_name,
            @Field("last_name") String mLast_name,
            @Field("promocode") String mPromoCode
    );

    @FormUrlEncoded
    @POST(Api.CHECK_EMAIL)
    Observable<Response<ResponseSuccess>> doCheckEmail(
            @Field("email") String mEmail
    );

    @FormUrlEncoded
    @POST(Api.CHECK_USERNAME)
    Observable<Response<ResponseSuccess>> doCheckUsername(
            @Field("username") String mUsername
    );

    @FormUrlEncoded
    @POST(Api.ITEM_SIZE_LIST)
    Observable<Response<ResponseItemSize>> doGetItemSizes(
            @Header("Authorization") String Authorization,
            @Field("iStart") int iStart,
            @Field("iLimit") int iLimit
    );

    @FormUrlEncoded
    @POST(Api.OFFICES_LIST)
    Observable<Response<ResponseOfficeList>> doGetOfficeList(
            @Header("Authorization") String Authorization,
            @Field("sLatitude") String sLatitude,
            @Field("sLongitude") String sLongitude,
            @Field("sortBy") String sortBy,
            @Field("iStart") int iStart,
            @Field("iLimit") int iLimit
    );

    @POST(Api.ADD_JOB)
    Observable<Response<ResponseOfficeList>> doAddJob(
            @Header("Authorization") String Authorization,
            @Body CreateItemModel mCreateItemModel
    );

    @FormUrlEncoded
    @POST(Api.SEARCHES_LIST)
    Observable<Response<ResponseSearchList>> doGetSearchList(
            @Header("Authorization") String Authorization,
            @Field("start") int start,
            @Field("limit") int limit
    );

    @POST(Api.VIEW_JOB)
    Observable<Response<ResponseJobList>> doGetJobs(
            @Header("Authorization") String Authorization,
            @Body FindJobModel mFindJobModel
    );

    @POST(Api.MOST_USED)
    Observable<Response<ResponseSearchList>> doGetMostUsedSearch(
            @Header("Authorization") String Authorization
    );

    @FormUrlEncoded
    @PUT(Api.SEARCH_LOCATION_STATUS)
    Observable<Response<ResponseError>> doChangeSearchStatus(
            @Header("Authorization") String Authorization,
            @Field("search_id") String search_id,
            @Field("status") String status
    );

    @GET(Api.VIEW_SEARCH)
    Observable<Response<ResponseSearchDetail>> doGetSearchDetail(
            @Header("Authorization") String Authorization,
            @Path("SEARCH_ID") String search_id
    );

    @PUT(Api.EDIT_SEARCH)
    Observable<Response<ResponseError>> doEditSearch(
            @Header("Authorization") String Authorization,
            @Body FindJobModel mFindJobModel
    );

    @DELETE(Api.VIEW_SEARCH)
    Observable<Response<ResponseError>> doDeleteSearch(
            @Header("Authorization") String Authorization,
            @Path("SEARCH_ID") String search_id
    );

    @PUT(Api.HIDE_JOB)
    Observable<Response<ResponseError>> doHideJob(
            @Header("Authorization") String Authorization,
            @Path("JOB_ID") String job_id
    );

    @FormUrlEncoded
    @POST(Api.QR_SCAN_CONFIRM)
    Observable<Response<ResponseError>> doScanConfirmJob(
            @Header("Authorization") String Authorization,
            @Field("iScanId") String iScanId
    );

    @FormUrlEncoded
    @POST(Api.QR_SCAN)
    Observable<Response<ResponseQRScan>> doScanJob(
            @Header("Authorization") String Authorization,
            @Field("code") String code
    );


    @GET(Api.VIEW_JOB_DETAILS)
    Observable<Response<ResponseJobDetails>> doGetJobDetails(
            @Header("Authorization") String Authorization,
            @Path("JOB_ID") String job_id
    );

    @FormUrlEncoded
    @POST(Api.OFFER_JOB)
    Observable<Response<ResponseError>> doAcceptJob(
            @Header("Authorization") String Authorization,
            @Field("sJobId") String sJobId,
            @Field("dProposeDateTime") String dProposeDateTime
    );


    @DELETE(Api.REMOVE_JOB)
    Observable<Response<ResponseError>> doRemoveJob(
            @Header("Authorization") String Authorization,
            @Path("JOB_ID") String JOB_ID
    );

    @FormUrlEncoded
    @POST(Api.INVITE_FRIEND)
    Observable<Response<ResponseError>> doInviteFriend(
            @Header("Authorization") String Authorization,
            @Field("invite") List<String> inviteList
    );

    @FormUrlEncoded
    @POST(Api.OFFER_LIST)
    Observable<Response<ResponseOfferList>> doGetOfferList(
            @Header("Authorization") String Authorization,
            @Field("sJobId") String sJobId,
            @Field("sortBy") String sortBy
    );

    @FormUrlEncoded
    @POST(Api.OFFER_RESPONSE)
    Observable<Response<ResponseOfferList>> doOfferResponse(
            @Header("Authorization") String Authorization,
            @Field("sJobId") String sJobId,
            @Field("sUserId") String sUserId,
            @Field("eOfferStatus") String eOfferStatus
    );

    @POST(Api.OFFER_RESPONSE)
    Observable<Response<ResponseOfferList>> doAddRecipient(
            @Header("Authorization") String Authorization,
            @Body AddReceivedModel model
    );

}