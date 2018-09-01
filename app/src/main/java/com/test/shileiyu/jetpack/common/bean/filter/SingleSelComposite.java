package com.test.shileiyu.jetpack.common.bean.filter;

import com.test.shileiyu.jetpack.common.util.Util;

public class SingleSelComposite extends Composite implements ISingleSelect {
    public SingleSelComposite(String name) {
        super(name);
    }

    public SingleSelComposite() {
    }

    @Override
    public void singleSelect() {
        if (isSelect && parent != null) {
            parent.resetChildren();
        } else {
            if (parent != null && !Util.isEmpty(parent.children)) {
                for (Composite c : parent.children) {
                    c.isSelect = c.equals(this);
                }
            }
        }
    }
}
