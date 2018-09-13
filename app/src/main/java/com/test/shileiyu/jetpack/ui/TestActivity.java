package com.test.shileiyu.jetpack.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.util.Util;

import butterknife.OnClick;

public class TestActivity extends BaseActivity {


    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @OnClick({
            R.id.zidingyi_da_dian,
            R.id.zidingyi_dizhijiexi
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zidingyi_da_dian:
                startBaiDuMapWithCustomPoint();
                break;
            case R.id.zidingyi_dizhijiexi:
                startBaiDuMap2ParseAddress();
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
