package com.test.shileiyu.jetpack.common.permission;

import java.util.List;

/**
 * 权限事件回调
 *
 * @author lanche.ysl
 * @date 2018/10/12 下午7:14
 */
public interface IPermissionAction {
    /**
     * 权限请求回调
     *
     * @param source 权限申请源
     * @param permissions   权限
     */
    void onCall(RequestSource source, List<String> permissions);
}
