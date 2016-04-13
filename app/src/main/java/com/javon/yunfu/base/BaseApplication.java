package com.javon.yunfu.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.javon.yunfu.BuildConfig;
import com.javon.yunfu.utils.log.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by javon on 16/4/8.
 */
public class BaseApplication extends Application {

    private final String TAG = BaseApplication.class.getSimpleName();
    private static BaseApplication sInstance;
    private static Context context;
    private Engine mEngine;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        sInstance = this;
        mEngine = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/bingoogolapple/BGABanner-Android/server/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Engine.class);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.json(TAG, message);
            }
        });
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY: HttpLoggingInterceptor.Level.NONE);


        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(logging);



        mEngine = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                .baseUrl("https://raw.githubusercontent.com/bingoogolapple/BGABanner-Android/server/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Engine.class);





        initLogger();
        initFresco();
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }

    public Engine getEngine() {
        return mEngine;
    }


    private void initLogger(){
        Log.init(true);
    }

    private void initFresco() {
        //fresco
        Fresco.initialize(this);
    }

    public static Context getContext() {
        return context;
    }
}
