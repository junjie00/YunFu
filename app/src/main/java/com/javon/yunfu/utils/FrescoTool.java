package com.javon.yunfu.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;


public class FrescoTool {

    private static final String TAG = FrescoTool.class.getSimpleName();


    public static void loadImage(SimpleDraweeView simpleDraweeView, String url) {
        if (TextUtils.isEmpty(url)) {
            Log.e(TAG, "url is null");
            return;
        }
        Uri uri = Uri.parse(url);
        Log.e("Image", "Image = " + uri.toString());
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(controller);
    }

}
