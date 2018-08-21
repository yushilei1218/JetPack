package com.test.shileiyu.jetpack.common.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import in.srain.cube.views.ptr.PtrDefaultHandler;

/**
 * @author shilei.yu
 * @date 2018/8/20
 */

public class DragLinearLayout extends LinearLayout {
    private static final String TAG = "DragLinearLayout";

    private ViewDragHelper mHelper;

    public DragLinearLayout(Context context) {
        super(context);
    }

    public DragLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                if (child instanceof ListView) {
                    log("tryCaptureView");
                    return !PtrDefaultHandler.canChildScrollUp(child);
                }
                return false;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                scrollBy(0, -dy);
                log("clampViewPositionVertical" + child.getTop() + " x" + child.getX() + " tx" + child.getTranslationX());
                return super.clampViewPositionVertical(child, top, dy);
            }

            @Override
            public int getViewVerticalDragRange(@NonNull View child) {
                log("getViewVerticalDragRange");
                return Integer.MAX_VALUE;
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                log("onViewReleased");
                super.onViewReleased(releasedChild, xvel, yvel);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mHelper.shouldInterceptTouchEvent(ev);
    }

    private void log(String msg) {
        Log.d(TAG, msg);
    }
}
