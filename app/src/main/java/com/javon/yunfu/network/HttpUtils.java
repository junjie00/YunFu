package com.javon.yunfu.network;


import com.javon.yunfu.BuildConfig;
import com.javon.yunfu.base.BaseApplication;
import com.javon.yunfu.utils.log.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by javon on 16/4/12.
 */
public class HttpUtils {
    private final String TAG = HttpUtils.class.getSimpleName();
    public static final String BASE_URL = "";
    private static final int DEFAULT_TIMEOUT = 5;
    private static int cacheSize = 30 * 1024 * 1024; // 30 MiB

    private static Retrofit retrofit;
    private static HttpUtils instance;

    private HttpUtils() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.json(TAG, message);
            }
        });
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY: HttpLoggingInterceptor.Level.NONE);


        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.cache(new Cache(BaseApplication.getContext().getExternalCacheDir(), cacheSize));
        okHttpBuilder.addInterceptor(logging);


        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    private static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                instance = new HttpUtils();
            }
        }
        return instance;
    }


}
