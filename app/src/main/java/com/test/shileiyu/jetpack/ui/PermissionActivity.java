package com.test.shileiyu.jetpack.ui;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.permission.Action;
import com.test.shileiyu.jetpack.common.permission.AskPermission;
import com.test.shileiyu.jetpack.common.permission.IRationale;
import com.test.shileiyu.jetpack.common.util.Util;


import java.util.List;

public class PermissionActivity extends AppCompatActivity {

    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);


        findViewById(R.id.btn_aaa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskPermission.width(PermissionActivity.this)
                        .createRequest()
                        .showRationale(new IRationale<List<String>>() {
                            @Override
                            public void showRationale(List<String> data) {
                                mTv.setText("应该告知的权限" + Util.toJson(data));
                            }
                        })
                        .permission(Manifest.permission.READ_PHONE_STATE
                                , Manifest.permission.CAMERA
                                , Manifest.permission.ACCESS_FINE_LOCATION
                                , Manifest.permission.ACCESS_COARSE_LOCATION)
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onCall(List<String> data) {
                                mTv.setText("申请成功" + Util.toJson(data));
                            }
                        }).onDenied(new Action<List<String>>() {
                    @Override
                    public void onCall(List<String> data) {
                        mTv.setText("申请失败" + Util.toJson(data));
                    }
                }).start();
            }
        });
        mTv = (TextView) findViewById(R.id.tv_aaa);


    }
}
