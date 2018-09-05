package com.test.shileiyu.jetpack.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;

import java.util.concurrent.ExecutionException;

import butterknife.BindView;

public class BitmapActivity extends BaseActivity {
    @BindView(R.id.bitmap_1)
    ImageView mView1;

    @Override
    protected void initView(Bundle savedInstanceState) {
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
        canvas.drawCircle(radius,radius,radius,paint);
        mView1.setImageBitmap(result);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bitmap;
    }
}
