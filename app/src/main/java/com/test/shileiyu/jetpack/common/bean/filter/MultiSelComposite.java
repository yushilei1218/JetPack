package com.test.shileiyu.jetpack.common.bean.filter;

import com.test.shileiyu.jetpack.common.util.Util;

public class MultiSelComposite extends Composite implements IMultiSelect {
    public MultiSelComposite(String name) {
        super(name);
    }

    public MultiSelComposite() {
    }

    @Override
    public void multiSelect() {
        if (parent != null && !Util.isEmpty(parent.children)) {
            Composite first = parent.children.get(0);
            boolean isFirst = this.equals(first);
            if (isFirst && !isSelect) {
                parent.resetChildren();
            }
            if (!isFirst) {
                if (isSelect) {
                    int index = 0;
                    for (int i = 0; i < parent.children.size(); i++) {
                        if (i == 0) {
                            continue;
                        }
                        Composite child = parent.children.get(i);
                        if (!child.equals(this) && child.isSelect) {
                            index++;
                        }
                    }
                    if (index > 0) {
                        isSelect = false;
                    } else {
                        parent.resetChildren();
                    }
                } else {
                    isSelect = true;
                    first.isSelect = false;
                }
            }
        }
    }
}
