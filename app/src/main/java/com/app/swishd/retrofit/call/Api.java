package com.app.swishd.retrofit.call;

public class Api {

    //...Local
//    public static final String BASE_URL = "http://192.168.10.115:3000/api/v1/";
//    public static final String BASE_URL_IMAGE = "http://192.168.10.115:3000/public/uploads/";

    //...Live
    public static final String BASE_URL = "http://ec2-18-221-37-87.us-east-2.compute.amazonaws.com:3000/api/v1/";
    public static final String BASE_URL_IMAGE = "http://ec2-18-221-37-87.us-east-2.compute.amazonaws.com:3000/public/uploads/";


    public static final String LOGIN = "Login";
    public static final String SAVE_PUSH_TOKEN = "savepushtoken";
    public static final String LOGOUT = "logout";
    public static final String PROFILE = "profile";
    public static final String SOCIAL_CONNECT = "socialmedia";
    public static final String VERIFY_MOBILE = "mobile";
    public static final String UPLOAD_ID_PROOF = "uploadidproof";
    public static final String GET_ID_PROOF = "verifications";
    public static final String SENDERS = "senders";
    public static final String SWISHERS = "swishrs";
    public static final String SETTINGS = "settings";
    public static final String SETTING = "setting";
    public static final String JOB_ACTIVITY = "jobActivity/{JOB_ID}";
    public static final String MESSAGES = "messages";
    public static final String SEND_MESSAGE = "sendMessage";
    public static final String TRANSACTION_HISTORY = "history";
    public static final String BANK_ACCOUNTS = "bank";
    public static final String NOTIFICATION_LOG = "log";


    public static final String FORGOT_PASSWORD = "forgetpassword";
    public static final String REGISTER = "register";
    public static final String CHECK_USERNAME = "username";
    public static final String CHECK_EMAIL = "email";


    public static final String ITEM_SIZE_LIST = "sizes";
    public static final String OFFICES_LIST = "offices";
    public static final String ADD_OFFICE = "office";
    public static final String ADD_JOB = "job";
    public static final String VIEW_JOB = "jobs";
    public static final String HIDE_JOB = "jobhide/{JOB_ID}";


    public static final String SEARCHES_LIST = "searches";
    public static final String MOST_USED = "mostused";
    public static final String SEARCH_LOCATION_STATUS = "status";
    public static final String VIEW_SEARCH = "search/{SEARCH_ID}";
    public static final String EDIT_SEARCH = "search";


    public static final String QR_SCAN_CONFIRM = "scanconfirm";
    public static final String QR_SCAN = "scan";
    public static final String VIEW_JOB_DETAILS = "job/{JOB_ID}";
    public static final String OFFER_JOB = "offer";
    public static final String REMOVE_JOB = "offer/{JOB_ID}";

    public static final String INVITE_FRIEND = "invitefriend";

    public static final String OFFER_LIST = "offers";
    public static final String OFFER_RESPONSE = "offerResponse";
    public static final String RECIPIENT = "recipient";
}