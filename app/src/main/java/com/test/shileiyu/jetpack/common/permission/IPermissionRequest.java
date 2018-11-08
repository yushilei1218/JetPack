package com.test.shileiyu.jetpack.common.permission;

import android.support.annotation.NonNull;

/**
 * 权限请求接口
 * <p>
 * 子类参见{@link MRequest} && {@link LRequest}
 * 分别代码系统API >=23的请求处理，和<23的请求处理
 *
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
    IPermissionRequest onGranted(IPermissionAction grantAction);

    /**
     * 需求告知用户 请求权限的原因
     *
     * @param rationale 再次确认执行对象
     * @return 权限Request
     */
    IPermissionRequest showRationale(IRationale rationale);

    /**
     * 权限被拒绝回调
     *
     * @param deniedAction 拒绝回调处理对象
     * @return 权限Request
     */
    IPermissionRequest onDenied(IPermissionAction deniedAction);

    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

    /**
     * 开始触发权限检测
     */
    void start();
}
