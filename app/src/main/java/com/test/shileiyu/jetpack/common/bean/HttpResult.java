package com.test.shileiyu.jetpack.common.bean;

/**
 * @author shilei.yu
 * @date 2018/8/8
 */

public class HttpResult<T> {


    public int ret;
    public int maxPageId;
    public int totalCount;
    public int pageId;
    public int pageSize;

    public T list;

    public String msg;
}
