package com.test.shileiyu.jetpack.common.permission;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;

import java.util.List;


/**
 * @author lanche.ysl
 * @date 2018/11/5 下午8:52
 */
public class Util {
    private Util() {
    }

    public static String getPermissionMsg(List<String> ps) {
        String[] names = PermissionsGroup.getPermissionNames(ps);
        StringBuilder sb = new StringBuilder();
        sb.append("需要打开");
        for (String name : names) {
            sb.append(name).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("权限，才能获取基础服务");
        return sb.toString();
    }

    /**
     * 获取权限解释前缀文案
     *
     * @param ps 权限集合
     * @return eg:需要打开xx权限
     */
    public static String getPermissionMsgPrefix(List<String> ps) {
        String[] names = PermissionsGroup.getPermissionNames(ps);
        StringBuilder sb = new StringBuilder();
        sb.append("需要打开");
        for (String name : names) {
            sb.append(name).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("权限");
        return sb.toString();
    }

    /**
     * 检查APP必要权限，如果检查到必要权限未开启，则会销毁所有Activity 并重新启动首页
     *
     * @param context
     */
    public static void restartIfLackPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context != null) {
                boolean hasPermission = Util.hasPermission(context, PermissionsGroup.getNecessaryPermissions());
                if (!hasPermission) {
                   // AppManager.getAppManager().finishAllActivity();
                    try {
                        Class<?> aClass = Class.forName("cn.damai.launcher.splash.SplashMainActivity");
                        Intent intent = new Intent(context, aClass);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    public static boolean hasPermission(Context context, List<String> permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        AppOpsManager opsManager = null;
        for (String permission : permissions) {
            int result = PermissionChecker.checkSelfPermission(context, permission);
            if (result == PackageManager.PERMISSION_DENIED) {
                return false;
            }
            String op = AppOpsManager.permissionToOp(permission);
            if (TextUtils.isEmpty(op)) {
                continue;
            }
            if (opsManager == null) {
                opsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            }
            if (opsManager != null) {
                result = opsManager.checkOpNoThrow(op, android.os.Process.myUid(), context.getPackageName());
            }
            if (result != AppOpsManager.MODE_ALLOWED && result != 4) {
                return false;
            }
        }
        return true;
    }
}
