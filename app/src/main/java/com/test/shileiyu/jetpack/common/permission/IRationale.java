package com.test.shileiyu.jetpack.common.permission;

import java.util.List;

/**
 * @author lanche.ysl
 * @date 2018/10/12 下午7:19
 */
public interface IRationale {
    /**
     * 当需要告知用户，我们为什么需求某些权限时，{@link MRequest}内部会回调这个方法
     *
     * @param data     需要解释的权限
     * @param executor 权限执行器
     * @param source   权限申请源
     */
    void showRationale(List<String> data, IRequestExecutor executor, RequestSource source);
}
