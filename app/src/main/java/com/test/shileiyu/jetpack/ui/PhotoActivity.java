package com.test.shileiyu.jetpack.ui;


import android.os.Bundle;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;

import butterknife.BindView;

public class PhotoActivity extends BaseActivity {

    @BindView(R.id.photo_view)
    PhotoView mPhotoView;
    @BindView(R.id.photo_btn)
    Button mBtn;

    @Override
    protected void initView(Bundle savedInstanceState) {
        Glide.with(this).load(R.mipmap.big_pic).into(mPhotoView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo;
    }
}
