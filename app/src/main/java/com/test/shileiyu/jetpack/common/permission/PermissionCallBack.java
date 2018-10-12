package com.test.shileiyu.jetpack.common.permission;

import android.support.annotation.NonNull;

/**
 * @author lanche.ysl
 * @date 2018/10/12 下午8:12
 */
public interface PermissionCallBack {
    void onPermissionFinish(@NonNull String[] permissions, @NonNull int[] grantResults);
}
