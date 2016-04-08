package com.javon.yunfu.base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by javon on 16/4/8.
 */
public class BaseApplication extends Application {

    private static BaseApplication sInstance;
    private Engine mEngine;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        mEngine = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/bingoogolapple/BGABanner-Android/server/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Engine.class);
        initFresco();
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }

    public Engine getEngine() {
        return mEngine;
    }

    private void initFresco() {
        //fresco
        Fresco.initialize(this);
    }
}
