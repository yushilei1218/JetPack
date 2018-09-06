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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.adapter.BeanAdapter;
import com.test.shileiyu.jetpack.common.adapter.BeanRAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.Bean;
import com.test.shileiyu.jetpack.common.widget.CircleStrokeCrop;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BitmapActivity extends BaseActivity {
    @BindView(R.id.bitmap_1)
    ImageView mView1;
    @BindView(R.id.bitmap_2)
    ImageView mView2;
    @BindView(R.id.child1)
    View mChild1;
    @BindView(R.id.child2)
    View mChild2;
    @BindView(R.id.parent)
    ViewGroup mParent;
    @BindView(R.id.lv1)
    RecyclerView mlv1;
    @BindView(R.id.lv2)
    RecyclerView mlv2;

    @Override
    protected void initView(Bundle savedInstanceState) {
        Bitmap result = getBitmap();
        mView1.setImageBitmap(result);
        compress(result);

        RequestOptions requestOptions = new RequestOptions();

        RequestOptions options = requestOptions.transform(new CircleStrokeCrop(Color.RED, 5)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);


        Glide.with(this).load(R.mipmap.header2).apply(options).into(mView2);

        BeanRAdapter adapter = new BeanRAdapter();
        BeanRAdapter adapter2 = new BeanRAdapter();
        List<Bean> data = new ArrayList<>(100);
        for (int i = 0; i < 15; i++) {
            data.add(new Bean("item " + i));
        }
        adapter.data = data;
        adapter2.data = data;
        mlv1.setAdapter(adapter);
        mlv2.setAdapter(adapter2);
    }

    @OnClick({R.id.child1, R.id.child2})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.child1:
                mParent.scrollTo(0, mChild1.getHeight());
                break;
            case R.id.child2:
                mParent.scrollTo(0, 0);
                break;
            default:
                break;
        }
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
