package com.test.shileiyu.jetpack.common.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

/**
 * 该代理Activity仅仅用于请求权限，么有界面
 * <p>
 * 入口{@link #requestPermission(Context, String[], IPermissionCallBack)}
 *
 * @author shilei.yu
 */
public class PermissionDelegateActivity extends AppCompatActivity {

    public static final String EXTRA = "EXTRA";
    public static final int PERMISSION_REQUEST_CODE = 0x1234;
    private static IPermissionCallBack sPermissionCallBack;

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
    public void finish() {
        sPermissionCallBack = null;
        super.finish();
        overridePendingTransition(-1, -1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void requestPermission(
            Context context, String[] permissions, IPermissionCallBack callBack) {
        if (context == null) {
            return;
        }
        sPermissionCallBack = callBack;
        Intent intent = new Intent(context, PermissionDelegateActivity.class);
        intent.putExtra(EXTRA, permissions);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(-1, -1);
        }
    }
}
