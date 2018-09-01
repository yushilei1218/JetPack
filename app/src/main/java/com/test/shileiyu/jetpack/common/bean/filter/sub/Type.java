package com.test.shileiyu.jetpack.common.bean.filter.sub;

import com.test.shileiyu.jetpack.common.bean.filter.Composite;
import com.test.shileiyu.jetpack.common.bean.filter.SingleSelComposite;
import com.test.shileiyu.jetpack.common.util.Util;

public class Type extends SingleSelComposite {
    public Type() {
    }

    @Override
    public boolean isLastNote() {
        return false;
    }

    public Type(String name) {
        super(name);
    }
}
