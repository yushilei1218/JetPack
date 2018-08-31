package com.test.shileiyu.jetpack.ui.search;

/**
 * @author shilei.yu
 * @date 2018/8/31
 */

public class SortParams {
    public String name;
    public boolean isSelect = false;

    public SortParams(String name, boolean isSelect) {
        this.name = name;
        this.isSelect = isSelect;
    }

    public SortParams(String name) {
        this.name = name;
    }
}
