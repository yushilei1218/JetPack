package com.test.shileiyu.jetpack.common.widget;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

/**
 * @author shilei.yu
 * @date 2018/8/30
 */

public class CircleStrokeCrop extends CircleCrop {
    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    @Override
    protected Bitmap transform(
            @NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return TransformationUtils.circleCrop(pool, toTransform, outWidth, outHeight);
    }

}
