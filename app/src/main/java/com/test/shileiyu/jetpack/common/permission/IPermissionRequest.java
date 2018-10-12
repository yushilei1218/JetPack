package com.test.shileiyu.jetpack.common.permission;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * @author lanche.ysl
 * @date 2018/10/12 下午7:12
 */
public interface IPermissionRequest {

    /**
     * 设置权限
     *
     * @param permission 不可为null
     * @return 权限Request
     */
    IPermissionRequest permission(@NonNull String... permission);

    /**
     * 权限获取成功回调
     *
     * @param grantAction 成功回调处理对象
     * @return 权限Request
     */
    IPermissionRequest onGranted(Action<List<String>> grantAction);

    /**
     * 需求告知用户 请求权限的原因
     *
     * @param rationale 再次确认执行对象
     * @return 权限Request
     */
    IPermissionRequest showRationale(IRationale<List<String>> rationale);

    /**
     * 权限被拒绝回调
     *
     * @param deniedAction 拒绝回调处理对象
     * @return 权限Request
     */
    IPermissionRequest onDenied(Action<List<String>> deniedAction);


    void start();
}
