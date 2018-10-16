package com.test.shileiyu.jetpack.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.test.shileiyu.jetpack.R;

/**
 * @author lanche.ysl
 * @date 2018/10/16 下午6:25
 */
public class Linear2Layout extends LinearLayout {
    public Linear2Layout(Context context) {
        this(context, null);
    }

    public Linear2Layout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public Linear2Layout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View header = LayoutInflater.from(context).inflate(R.layout.header_aaa, this, false);
        addView(header, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child = getChildAt(0);
        measureChild(child, widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST));
        View content = getChildAt(1);
        if (content != null) {
            measureChild(content, widthMeasureSpec, heightMeasureSpec);
        }
        ((LayoutParams) child.getLayoutParams()).setMargins(0, -child.getMeasuredHeight(), 0, 0);
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    private float mY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                offset(y-mY);
                break;

        }
        mY = y;
        return true;
    }

    private void offset(float delta){
        for (int g = 0; g < getChildCount(); g++) {
            getChildAt(g).offsetTopAndBottom((int) delta);
        }
    }
}
