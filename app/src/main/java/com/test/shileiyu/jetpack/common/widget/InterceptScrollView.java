package com.test.shileiyu.jetpack.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @author lanche.ysl
 * @date 2018/10/9 下午5:41
 */
public class InterceptScrollView extends ScrollView {
    private OnPointTargetListener mListener;

    public InterceptScrollView(Context context) {
        super(context);
    }

    public InterceptScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mListener!=null){
            boolean on = mListener.on();

        }
        return super.onInterceptTouchEvent(ev);
    }

    public interface OnPointTargetListener{
        boolean on();
    }
}
