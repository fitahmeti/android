package com.app.swishd.retrofit;

import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ApiCallback<T> extends DisposableObserver<T> {

    private OnResponseCallback mCallback;
    private int mRequestCode = -1;

    public ApiCallback(OnResponseCallback callback) {
        this.mCallback = callback;
    }

    public ApiCallback(OnResponseCallback callback, int requestCode) {
        this.mCallback = callback;
        this.mRequestCode = requestCode;
    }

    @Override
    public void onNext(T o) {
        if (((Response) o).code() == ResponseCode.Unauthorised.value()) {
            mCallback.onTokenExpires();
        } else if (((Response) o).isSuccessful())
            mCallback.onResponseReceived(((Response) o).body(), mRequestCode);
        else {
            try {
                ResponseBody errorBody = ((Response) o).errorBody();
                Gson gson = new Gson();
                ResponseError error = gson.fromJson(errorBody.string(), ResponseError.class);
                mCallback.onResponseError(error.getError());
            } catch (IOException e) {
                e.printStackTrace();
                if (((Response) o).message() != null)
                    mCallback.onResponseError(((Response) o).message());
                else
                    mCallback.onResponseError("server not available");
            }
        }
    }

    @Override
    public void onError(Throwable t) {
        if (t instanceof java.net.ConnectException)
            mCallback.onResponseError("No internet connection found.");
        else if (t.getMessage() != null)
            mCallback.onResponseError(t.getMessage());
        else
            mCallback.onResponseError("server not available");
    }

    @Override
    public void onComplete() {
        mCallback.onResponseCompleted();
    }

}
