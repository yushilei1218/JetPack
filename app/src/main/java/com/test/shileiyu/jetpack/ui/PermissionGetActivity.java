package com.test.shileiyu.jetpack.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.test.shileiyu.jetpack.common.permission.PermissionCallBack;

public class PermissionGetActivity extends Activity {

    public static final String EXTRA = "EXTRA";
    public static final int PERMISSION_REQUEST_CODE = 0x1234;
    private static PermissionCallBack sPermissionCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void requestPermission(
            Context context, String[] permissions, PermissionCallBack callBack) {
        if (context == null)
            return;
        sPermissionCallBack = callBack;
        Intent intent = new Intent(context, PermissionGetActivity.class);
        intent.putExtra(EXTRA, permissions);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
