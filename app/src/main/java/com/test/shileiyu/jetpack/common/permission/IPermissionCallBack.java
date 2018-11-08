package com.test.shileiyu.jetpack.common.permission;

import android.support.annotation.NonNull;

/**
 * @author lanche.ysl
 * @date 2018/10/12 下午8:12
 */
public interface IPermissionCallBack {
    /**
     * 权限申请完毕结果回调方法
     *
     * @param permissions  本次申请的所有权限
     * @param grantResults 本次已经申请到的权限
     */
    void onPermissionFinish(@NonNull String[] permissions, @NonNull int[] grantResults);
}
