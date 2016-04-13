package com.javon.yunfu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.javon.yunfu.base.BaseActivity;

import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by javon on 16/4/8.
 */
public class TwoDimensionCodeActivity extends BaseActivity implements ZBarScannerView.ResultHandler {
    private static final String TAG = TwoDimensionCodeActivity.class.getSimpleName();
//    ZXingScannerView mScannerView;
    ZBarScannerView mScannerView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_dimension_code;
    }

    @Override
    protected void init(Bundle saveInstanceState) {
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);
//        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
//        setContentView(mScannerView);                // Set the scanner view as the content view
    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(me.dm7.barcodescanner.zbar.Result result) {
        Log.e(TAG, result.getContents()); // Prints scan results
        Log.e(TAG, result.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)
    }



    public static void newInstance(Context context){
        context.startActivity(new Intent(context, TwoDimensionCodeActivity.class));
    }

    @Override
    public void onClick(View v) {

    }


}
