package com.test.shileiyu.jetpack.common.permission;


import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author lanche.ysl
 * @date 2018/10/12 下午7:44
 */
public class StandChecker implements PermissionChecker {
    @Override
    public boolean hasPermission(Context context, String... p) {
        if (p != null) {
            return hasPermission(context, Arrays.asList(p));
        }
        return true;
    }

    @Override
    public boolean hasPermission(Context context, List<String> permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        AppOpsManager opsManager = null;
        for (String permission : permissions) {
            int result = context.checkPermission(permission, android.os.Process.myPid(), android.os.Process.myUid());
            if (result == PackageManager.PERMISSION_DENIED) {
                return false;
            }

            String op = AppOpsManager.permissionToOp(permission);
            if (TextUtils.isEmpty(op)) {
                continue;
            }

            if (opsManager == null)
                opsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            result = opsManager.checkOpNoThrow(op, android.os.Process.myUid(), context.getPackageName());
            if (result != AppOpsManager.MODE_ALLOWED && result != 4) {
                return false;
            }
        }
        return true;
    }
}
