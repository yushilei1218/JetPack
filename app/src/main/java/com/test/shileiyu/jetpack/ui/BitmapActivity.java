package com.test.shileiyu.jetpack.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.widget.CircleStrokeCrop;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.BindView;

public class BitmapActivity extends BaseActivity {
    @BindView(R.id.bitmap_1)
    ImageView mView1;
    @BindView(R.id.bitmap_2)
    ImageView mView2;

    @Override
    protected void initView(Bundle savedInstanceState) {
        Bitmap result = getBitmap();
        mView1.setImageBitmap(result);
        compress(result);

        RequestOptions requestOptions = new RequestOptions();

        RequestOptions options = requestOptions.transform(new CircleStrokeCrop(Color.RED, 5)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);


        Glide.with(this).load(R.mipmap.header2).apply(options).into(mView2);
    }

    private void compress(Bitmap result) {
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "target.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (stream != null) {
            int byteCount = result.getByteCount();
            Log.d("ByteCount", "" + byteCount);
            result.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        }
    }

    @NonNull
    private Bitmap getBitmap() {
        Bitmap header = BitmapFactory.decodeResource(getResources(), R.mipmap.header);
        Bitmap result = Bitmap.createBitmap(header.getWidth(), header.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(result);
        result.setHasAlpha(true);

        Paint paint = new Paint(Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(null);
        float radius = header.getWidth() / 2f;
        RectF dst = new RectF(0, 0, header.getWidth(), header.getHeight());
        canvas.drawColor(Color.TRANSPARENT);
        int i = canvas.saveLayer(0, 0, header.getWidth(), header.getHeight(), paint, Canvas.ALL_SAVE_FLAG);

        canvas.drawCircle(radius, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(header, null, dst, paint);

        canvas.restoreToCount(i);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(radius, radius, radius, paint);
        return result;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bitmap;
    }
}
