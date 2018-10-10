package com.test.shileiyu.jetpack.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.Bean;
import com.test.shileiyu.jetpack.common.util.Util;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;

public class TestActivity extends BaseActivity {

    @BindView(R.id.left_5)
    View mBTN;
    @BindView(R.id.request)
    View mRequest;
    @BindView(R.id.left_6)
    View mTx;
    @BindView(R.id.left_7)
    View mTx7;
    @BindView(R.id.left_8)
    View mTx8;
    @BindView(R.id.left_9)
    View mTx9;

    @Override
    protected void initView(Bundle savedInstanceState) {
        obtainExtraFromIntent();
    }

    private void obtainExtraFromIntent() {
        Intent intent = getIntent();
        String extra = intent.getStringExtra("extrame");
        Bean obj = (Bean) intent.getSerializableExtra("obj");
        Uri data = intent.getData();
        String value = null;
        if (data != null) {
            value = data.getQueryParameter("key");
        }

    }

    @OnClick({
            R.id.zidingyi_da_dian,
            R.id.left_9,
            R.id.left_6,
            R.id.left_7,
            R.id.zidingyi_dizhijiexi,
            R.id.left_8,
            R.id.left_5,
            R.id.request
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.request:
                mRequest.requestLayout();
                break;
            case R.id.left_5:
                int i = mBTN.getLeft() + 50;
                mBTN.setLeft(i);
                break;
            case R.id.left_6:
                mTx.setTranslationX(mTx.getTranslationX() + 50);
                break;
            case R.id.left_7:
                mTx7.setTranslationX(mTx7.getTranslationX() + 50);
                break;
            case R.id.zidingyi_da_dian:
                startBaiDuMapWithCustomPoint();
                break;
            case R.id.zidingyi_dizhijiexi:
                startBaiDuMap2ParseAddress();
                break;
            case R.id.left_8:
                mTx8.offsetLeftAndRight(50);
                break;
            case R.id.left_9:
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTx9.getLayoutParams();
                lp.leftMargin += 50;
                mTx9.requestLayout();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int left = mTx9.getLeft();
                        int right = mTx9.getRight();
                        int top = mTx9.getTop();
                        int bottom = mTx9.getBottom();
                        String msg = "left=" + left + " right=" + right + " top=" + top + " bottom=" + bottom;
                        Util.showToast(msg);
                        Log.d("MiButton", msg);
                    }
                }, 200);
                break;
            default:
                break;
        }
    }

    private void startBaiDuMap2ParseAddress() {

        Intent i1 = new Intent();
        // 地址解析
        i1.setData(Uri.parse("baidumap://map/geocoder?src=andr.baidu.openAPIdemo&address=北京市海淀区上地信息路9号奎科科技大厦"));

        PackageManager pm = getPackageManager();
        ResolveInfo info = pm.resolveActivity(i1, PackageManager.MATCH_DEFAULT_ONLY);
        if (info != null) {
            Log.d("IntentFilter", info.toString());
            startActivity(i1);
        } else {
            Util.showToast("未安装百度地图");
        }
    }

    private void startBaiDuMapWithCustomPoint() {
        Intent intent = new Intent();
        intent.setData(Uri.parse(
                "baidumap://map/marker?location=40.057406655722,116.2964407172&title=Marker&content=makeamarker&traffic=on&src=andr.baidu.openAPIdemo"));
        PackageManager pm = getPackageManager();
        ResolveInfo info = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (info != null) {
            Log.d("IntentFilter", info.toString());
            startActivity(intent);
        } else {
            Util.showToast("未安装百度地图");
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }
}
