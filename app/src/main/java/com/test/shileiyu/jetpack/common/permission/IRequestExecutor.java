package com.test.shileiyu.jetpack.common.permission;


/**
 * 权限执行器
 *
 * @author shilei.yu
 */
public interface IRequestExecutor {
    /**
     * 触发执行权限申请
     */
    void execute();

    /**
     * 中断权限申请
     */
    void cancel();
}
