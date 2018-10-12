package com.test.shileiyu.jetpack.common.permission;

/**
 * @author lanche.ysl
 * @date 2018/10/12 下午7:14
 */
public interface PermissionAction<T> {
    void onCall(T data);
}
