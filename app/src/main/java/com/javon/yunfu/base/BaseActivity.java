package com.javon.yunfu.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.javon.yunfu.R;

/**
 * Created by javon on 16/4/8.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private TextView tvBarTitle;
    private TextView tvBarRight;
    protected Engine mEngine;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mEngine = BaseApplication.getInstance().getEngine();
        setContentView(getLayoutId());
        initTitle();
        init(savedInstanceState);


    }

    protected abstract int getLayoutId();

    protected abstract void init(Bundle saveInstanceState);

    protected  void initBarTitle(String title){
        tvBarTitle.setText(title);
    }


    private void initTitle(){
         Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        tvBarTitle = (TextView) toolbar.findViewById(R.id.tv_main_toolbar_title);
        tvBarRight = (TextView) toolbar.findViewById(R.id.tv_main_toolbar_right);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowTitleEnabled(false);
    }


    protected void showToast(int resId){
        showToast(getString(resId));
    }


    protected void showToast(String tip){
        Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
