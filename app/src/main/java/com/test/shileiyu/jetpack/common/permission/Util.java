package com.test.shileiyu.jetpack.common.permission;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import com.yanzhenjie.permission.checker.PermissionChecker;
import com.yanzhenjie.permission.source.Source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lanche.ysl
 * @date 2018/10/12 下午7:27
 */
class Util {

    /**
     * Get denied permissions.
     */
    private static List<String> getDeniedPermissions(PermissionChecker checker, Source source, String... permissions) {
        List<String> deniedList = new ArrayList<>(1);
        for (String permission : permissions) {
            if (!checker.hasPermission(source.getContext(), permission)) {
                deniedList.add(permission);
            }
        }
        return deniedList;
    }

    /**
     * Get permissions to show rationale.
     */
    private static List<String> getRationalePermissions(Source source, String... permissions) {
        List<String> rationaleList = new ArrayList<>(1);
        for (String permission : permissions) {
            if (source.isShowRationalePermission(permission)) {
                rationaleList.add(permission);
            }
        }
        return rationaleList;
    }


    public boolean hasPermission(Context context, String... permissions) {
        return hasPermission(context, Arrays.asList(permissions));
    }


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

            if (opsManager == null) opsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            result = opsManager.checkOpNoThrow(op, android.os.Process.myUid(), context.getPackageName());
            if (result != AppOpsManager.MODE_ALLOWED && result != 4) {
                return false;
            }
        }
        return true;
    }
}
