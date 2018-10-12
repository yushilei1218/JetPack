package com.test.shileiyu.jetpack.common.permission;

/**
 * @author lanche.ysl
 * @date 2018/10/12 下午7:19
 */
public interface IRationale<T> {
    void showRationale(T data, RequestExecutor executor);
}
