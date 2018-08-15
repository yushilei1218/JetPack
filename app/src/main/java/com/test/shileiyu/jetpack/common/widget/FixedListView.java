package com.test.shileiyu.jetpack.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author shilei.yu
 * @date 2018/8/9
 */

public class FixedListView extends ListView {

    public FixedListView(Context context) {
        super(context);
    }

    public FixedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int newH = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, newH);
    }
}
