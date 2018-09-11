package com.test.shileiyu.jetpack.common.bean.city;

import com.test.shileiyu.jetpack.common.util.Util;

import java.io.Serializable;
import java.util.List;

/**
 * @author shilei.yu
 * @date 2018/9/11
 */

public abstract class BaseComposite implements Serializable{
    public BaseComposite parent;
    public int id;
    public String name;
    public BaseComposite[] children;

    public BaseComposite(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addChild(List<BaseComposite> childList) {
        if (!Util.isEmpty(childList)) {
            children = new BaseComposite[childList.size()];
            for (int i = 0; i < childList.size(); i++) {
                children[i] = childList.get(i);
            }
        }
    }

    public void requestChildren() {

    }
}
