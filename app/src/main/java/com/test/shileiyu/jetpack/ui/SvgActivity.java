package com.test.shileiyu.jetpack.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;

import butterknife.BindView;

public class SvgActivity extends BaseActivity {
    @BindView(R.id.test_svg)
    AppCompatImageView mView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        VectorDrawableCompat compat = VectorDrawableCompat.create(getResources(), R.drawable.ic_seat_lock, getTheme());
        DrawableCompat.setTint(compat, Color.BLUE);
        mView.setImageDrawable(compat);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_svg;
    }
}
