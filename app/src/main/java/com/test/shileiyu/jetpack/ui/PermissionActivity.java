package com.test.shileiyu.jetpack.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.permission.Action;
import com.test.shileiyu.jetpack.common.permission.AskPermission;
import com.test.shileiyu.jetpack.common.permission.IRationale;
import com.test.shileiyu.jetpack.common.permission.RequestExecutor;
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
                AskPermission.with(PermissionActivity.this)
                        .createRequest()
                        .showRationale(new IRationale<List<String>>() {
                            @Override
                            public void showRationale(List<String> data, final RequestExecutor executor) {
                                String str = "需要告知用户请求权限的原因：";
                                StringBuilder format = format(data, str);
                                final String text = format.toString();
                                mTv.setText(text);
                                new AlertDialog.Builder(PermissionActivity.this)
                                        .setMessage(text)
                                        .setPositiveButton("继续申请", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                executor.execute();
                                            }
                                        })
                                        .setNegativeButton("放弃申请", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                executor.cancel();
                                            }
                                        })
                                        .create().show();
                            }
                        })
                        .permission(Manifest.permission.READ_PHONE_STATE
                                , Manifest.permission.CAMERA
                                , Manifest.permission.ACCESS_FINE_LOCATION
                                , Manifest.permission.ACCESS_COARSE_LOCATION)
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onCall(List<String> data) {
                                mTv.setText(format(data, "申请成功的权限"));
                                Util.showToast("全部申请成功啦");
                            }
                        }).onDenied(new Action<List<String>>() {
                    @Override
                    public void onCall(List<String> data) {
                        mTv.setText(format(data, "申请失败的权限："));
                    }
                }).start();
            }
        });
        mTv = (TextView) findViewById(R.id.tv_aaa);
    }

    private StringBuilder format(List<String> data, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str).append("\n");
        for (String p : data) {
            sb.append(p).append("\n");
        }
        return sb;
    }
}
