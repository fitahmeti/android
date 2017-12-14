package com.app.swishd.retrofit;


import com.app.swishd.utility.MyPrefs;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

import static com.app.swishd.retrofit.RetrofitApp.getInstance;


public class ErrorUtils {

    public static ResponseError parseError(Response<?> response) {
        Converter<ResponseBody, ResponseError> converter = getInstance().responseBodyConverter(ResponseError.class, new Annotation[0]);
        ResponseError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            error = new ResponseError();
        }

        if (response.code() == ResponseCode.Unauthorised.value()) {
            try {
                if (MyPrefs.getInstance().isLogin()) {
                    MyPrefs.getInstance().logout();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return error;
    }
}