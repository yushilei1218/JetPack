package com.test.shileiyu.jetpack.common.widget;

/**
 * @author shilei.yu
 * @date 2018/8/16
 */

public interface ISeat {
    long getId();

    /**
     * 座位在场地中实际x坐标
     */
    int getXInArea();

    /**
     * 座位在场地实际y坐标
     */
    int getYInArea();

    /**
     * 是否被选中
     */
    boolean isSelect();

    /**
     * 是否已卖出
     */
    boolean isSold();

    void setSelect(boolean is);

    String getName();
}
