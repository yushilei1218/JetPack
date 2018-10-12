package com.test.shileiyu.jetpack.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.shileiyu.jetpack.common.permission.PermissionCallBack;

public class PermissionGetActivity extends AppCompatActivity {

    public static final String EXTRA = "EXTRA";
    public static final int PERMISSION_REQUEST_CODE = 0x1234;
    private static PermissionCallBack sPermissionCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] permissions = getIntent().getStringArrayExtra(EXTRA);
        if (permissions != null
                && permissions.length > 0
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        } else {
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (sPermissionCallBack != null) {
                sPermissionCallBack.onPermissionFinish(permissions, grantResults);
            }
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        sPermissionCallBack = null;
        super.onDestroy();
    }

    public static void requestPermission(
            Context context, String[] permissions, PermissionCallBack callBack) {
        sPermissionCallBack = callBack;
        Intent intent = new Intent(context, PermissionGetActivity.class);
        intent.putExtra(EXTRA, permissions);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
