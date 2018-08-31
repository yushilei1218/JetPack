package com.test.shileiyu.jetpack.common.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @date 2018/8/20
 */

public class Bean implements Cloneable{
    public String name;

    public List<String> urls = new ArrayList<>();

    public Bean(String name) {
        this.name = name;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
