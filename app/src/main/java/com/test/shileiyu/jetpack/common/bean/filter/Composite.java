package com.test.shileiyu.jetpack.common.bean.filter;

import com.test.shileiyu.jetpack.common.util.Util;

import java.util.ArrayList;
import java.util.List;

public abstract class Composite implements Cloneable {
    public int id;
    public String name;
    public Composite parent;
    public boolean isSelect = false;
    public boolean isTag = false;
    public List<Composite> children;

    public boolean isLastNote() {
        return true;
    }

    public Composite(String name) {
        this.name = name;
    }

    public Composite() {
    }

    public void resetChildren() {
        if (!Util.isEmpty(children)) {
            int index = 0;
            for (Composite c : children) {
                c.isSelect = index == 0;
                index++;
            }
        }
    }

    public void addChild(Composite child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        child.parent = this;
        children.add(child);
    }
}
