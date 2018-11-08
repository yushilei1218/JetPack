package com.test.shileiyu.jetpack.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;

import com.test.shileiyu.jetpack.common.util.Util;



import java.util.List;

public class PermissionActivity extends AppCompatActivity {

    private TextView mTv;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000) {
            Util.showToast("居然有onActivityResult");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        mTv = (TextView) findViewById(R.id.tv_aaa);
    }
}
