package com.app.swishd.retrofit;

import android.content.Context;

import com.app.swishd.retrofit.call.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApp {
    private static Retrofit retrofit;
    private static Context context;

    public static Context getContext() {
        return context;
    }

    /**
     * Initialize Retrofit in Appication
     *
     * @param applicationContext application context
     * @return Retrofit reference
     */
    public static Retrofit initRetrofit(Context applicationContext) {
        context = applicationContext;
        if (retrofit == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.readTimeout(1, TimeUnit.MINUTES);
            httpClient.connectTimeout(10, TimeUnit.SECONDS);
            httpClient.writeTimeout(1, TimeUnit.MINUTES);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder builder = original.newBuilder();
                    builder.header("Accept", "application/json");
                    builder.method(original.method(), original.body());

                    return chain.proceed(builder.build());
                }
            });

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor);

            OkHttpClient client = httpClient.build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return retrofit;
    }


    public static void resetRetrofitInstance() {
        retrofit = null;
    }

    /**
     * Retrofit reference
     *
     * @return Retrofit instance, if not initialized than returns null
     */
    public static Retrofit getInstance() {
        return retrofit;
    }
}
