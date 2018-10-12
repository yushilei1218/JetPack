package com.test.shileiyu.jetpack.common.permission;

import android.content.Context;

import java.util.List;

/**
 * @author lanche.ysl
 * @date 2018/10/12 下午7:43
 */
public interface PermissionChecker {
    boolean hasPermission(Context context, String... p);

    boolean hasPermission(Context context, List<String> ps);
}
