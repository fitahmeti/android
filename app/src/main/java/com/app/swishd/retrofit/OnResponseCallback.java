package com.app.swishd.retrofit;


public interface OnResponseCallback<T> {

    void onResponseReceived(T model, int requestCode);

    void onResponseError(String message);

    void onResponseCompleted();

    void onTokenExpires();
}
