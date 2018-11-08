package com.test.shileiyu.jetpack.common.permission;


import android.content.Context;

import java.util.Arrays;
import java.util.List;

/**
 * @author lanche.ysl
 * @date 2018/10/12 下午7:44
 */
public class StandChecker implements IPermissionChecker {
    @Override
    public boolean hasPermission(Context context, String... p) {
        if (p != null) {
            return hasPermission(context, Arrays.asList(p));
        }
        return true;
    }

    @Override
    public boolean hasPermission(Context context, List<String> permissions) {
        return Util.hasPermission(context, permissions);
    }
}
