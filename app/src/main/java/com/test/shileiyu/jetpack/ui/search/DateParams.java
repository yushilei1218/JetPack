package com.test.shileiyu.jetpack.ui.search;

/**
 * @author shilei.yu
 * @date 2018/8/31
 */

public class DateParams implements Cloneable {
    public String name;
    public boolean isSelected = false;

    public DateParams(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public DateParams(String name) {
        this.name = name;
    }

    @Override
    public Object clone() {
        try {

            return super.clone();
        } catch (Exception e) {
            return null;
        }
    }
}
