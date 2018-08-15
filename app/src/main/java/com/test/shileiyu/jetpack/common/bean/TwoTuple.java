package com.test.shileiyu.jetpack.common.bean;

/**
 * @author shilei.yu
 * @date 2018/8/14
 */

public class TwoTuple<First, Second> {
    public final First first;
    public final Second second;

    public TwoTuple(First first, Second second) {
        this.first = first;
        this.second = second;
    }
}
