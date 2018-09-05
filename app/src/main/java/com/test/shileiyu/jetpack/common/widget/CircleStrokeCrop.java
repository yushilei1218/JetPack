package com.test.shileiyu.jetpack.common.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

/**
 * @author shilei.yu
 * @date 2018/9/5
 */

public class CircleStrokeCrop extends CircleCrop {
    private int color;
    private int strokeWidth;

    public CircleStrokeCrop(int color, int strokeWidth) {
        this.color = color;
        this.strokeWidth = strokeWidth;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap = super.transform(pool, toTransform, outWidth, outHeight);
        if (bitmap != null && !bitmap.isRecycled() && bitmap.isMutable()) {
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            paint.setColor(color);
            paint.setStrokeWidth(strokeWidth);
            paint.setStyle(Paint.Style.STROKE);

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float radius = Math.min(width, height) / 2f - strokeWidth / 2f;
            if (radius > 0) {
                canvas.drawCircle(width / 2f, height / 2f, radius, paint);
            }
            canvas.setBitmap(null);
        }
        return bitmap;
    }
}
