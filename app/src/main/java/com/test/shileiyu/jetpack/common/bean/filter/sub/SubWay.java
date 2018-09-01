package com.test.shileiyu.jetpack.common.bean.filter.sub;

import com.test.shileiyu.jetpack.common.bean.filter.SingleSelComposite;

public class SubWay extends SingleSelComposite {
    public SubWay(String name) {
        super(name);
    }

    @Override
    public boolean isLastNote() {
        return false;
    }

    public SubWay() {
    }
}
