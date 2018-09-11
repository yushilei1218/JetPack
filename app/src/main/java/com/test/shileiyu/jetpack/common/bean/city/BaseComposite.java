package com.test.shileiyu.jetpack.common.bean.city;

import android.os.SystemClock;

import com.test.shileiyu.jetpack.common.util.ThreadPool;
import com.test.shileiyu.jetpack.common.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @date 2018/9/11
 */

public abstract class BaseComposite<Child extends BaseComposite> implements Serializable {
    public BaseComposite parent;
    public int id;
    public String name;
    public boolean isSelect;
    public List<Child> children;

    public BaseComposite(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addChild(List<Child> childList) {
        if (!Util.isEmpty(childList)) {
            children = new ArrayList<>(childList.size());
            for (int i = 0; i < childList.size(); i++) {
                BaseComposite composite = childList.get(i);
                composite.parent = this;
            }
            children.addAll(childList);
        }
    }

    public void resetChildren() {
        if (!Util.isEmpty(children)) {
            for (int i = 0; i < children.size(); i++) {
                children.get(i).isSelect = i == 0;
            }
        }
    }

    public void setChildSelect(Child child) {
        if (!Util.isEmpty(children)) {
            if (children.contains(child)) {
                for (Child c : children) {
                    c.isSelect = c.equals(child);
                }
            } else {
                resetChildren();
            }
        }
    }

    public Child getSelectChild() {
        Child t = null;
        if (!Util.isEmpty(children)) {
            for (int i = 0; i < children.size(); i++) {
                Child child = children.get(i);
                boolean isSelect = child.isSelect;
                if (isSelect) {
                    t = child;
                    break;
                }
            }
        }
        return t;
    }

    public void requestChildren(final SimpleCallBack<Boolean> callBack) {

    }
}
