package com.test.shileiyu.jetpack.common.permission;

import android.content.Context;

import java.util.List;

/**
 * 权限检测接口
 *
 * @author lanche.ysl
 * @date 2018/10/15 下午10:43
 */
public interface IPermissionChecker {
    /**
     * 检测是否具备P权限
     */
    boolean hasPermission(Context context, String... p);

    boolean hasPermission(Context context, List<String> ps);
}
