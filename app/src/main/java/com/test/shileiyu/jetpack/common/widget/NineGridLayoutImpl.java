package com.test.shileiyu.jetpack.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.test.shileiyu.jetpack.R;

/**
 * @author shilei.yu
 * @date 2018/8/22
 */

public class NineGridLayoutImpl extends NineGridLayout {
    public NineGridLayoutImpl(Context context) {
        super(context);
    }

    public NineGridLayoutImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View newChild(NineGridLayout nineGridLayout) {
        return new ImageView(nineGridLayout.getContext());
    }

}
