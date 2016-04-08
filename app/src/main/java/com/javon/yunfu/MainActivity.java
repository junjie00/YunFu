package com.javon.yunfu;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.javon.yunfu.base.BaseActivity;
import com.javon.yunfu.model.BannerModel;
import com.javon.yunfu.utils.FrescoTool;
import com.javon.yunfu.wight.adwheel.BGABanner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {


    private DrawerLayout dlMain;
    private LinearLayout llRpContent;
    private BGABanner mCubeBanner;
    private List<SimpleDraweeView> mCubeViews;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle saveInstanceState) {
        setTitle("首页");
        dlMain = (DrawerLayout) findViewById(R.id.dl_main);
        llRpContent = (LinearLayout) findViewById(R.id.ll_rp_content);
        initCube();
    }

    private void initCube() {
        mCubeBanner = (BGABanner) findViewById(R.id.banner_main_cube);
        mCubeViews = getViews(6);
        mCubeBanner.setViews(mCubeViews);

        mEngine.sixItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                Log.e("MainActivity", "bannerModel size : " + bannerModel.imgs.size());
                Log.e("MainActivity", bannerModel.toString());
                for (int i = 0; i < mCubeViews.size(); i++) {
                    FrescoTool.loadImage(mCubeViews.get(i), bannerModel.imgs.get(i));
                }
                // 也可以不设置tips
//                mCubeBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
            }
        });
    }

    private List<SimpleDraweeView> getViews(int count) {
        List<SimpleDraweeView> views = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            views.add((SimpleDraweeView) getLayoutInflater().inflate(R.layout.banner_view_image_item, null));
        }
        return views;
    }







    @Override
    public void onBackPressed() {
        if (dlMain.isDrawerOpen(GravityCompat.START)) {
            dlMain.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            dlMain.openDrawer(Gravity.LEFT);
        }
    }


}
