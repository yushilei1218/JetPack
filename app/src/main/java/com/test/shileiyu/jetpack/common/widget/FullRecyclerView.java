package com.test.shileiyu.jetpack.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class FullRecyclerView extends RecyclerView {
    public FullRecyclerView(Context context) {
        super(context);
    }

    public FullRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FullRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
    }
}
