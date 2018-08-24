package com.test.shileiyu.jetpack.ui;

import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.yalantis.ucrop.view.GestureCropImageView;

import java.io.File;

import butterknife.BindView;

public class CropActivity extends BaseActivity {
    @BindView(R.id.crop_view)
    GestureCropImageView mCropImageView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mCropImageView.setScaleEnabled(true);
        mCropImageView.setTargetAspectRatio(1.0f);
        mCropImageView.setRotateEnabled(false);
        Uri parse = Uri.parse("https://ss1.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=50fc711ddf1b0ef473e89e5eedc451a1/b151f8198618367a2e8a46ee23738bd4b31ce586.jpg");
        Uri outputUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "crop.jpg"));
        try {
            mCropImageView.setImageUri(parse, outputUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_crop;
    }
}
