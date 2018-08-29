package com.test.shileiyu.jetpack.common.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.test.shileiyu.jetpack.common.util.Util;

/**
 * @author shilei.yu
 * @date 2018/8/29
 */

public class DragDismissLayout extends FrameLayout {

    private ViewDragHelper mHelper;

    private DragDismissCallBack mCallBack;

    public void setCallBack(DragDismissCallBack callBack) {
        mCallBack = callBack;
    }

    public DragDismissLayout(@NonNull Context context) {
        super(context);
    }

    public DragDismissLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return true;
            }

            @Override
            public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
                ViewParent parent = capturedChild.getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                if (releasedChild.getTop() > getHeight() / 2) {
                    if (mCallBack != null) {
                        mCallBack.dismiss();
                    }
                    //消失
                    Util.showToast("消失");
                } else {
                    releasedChild.setScaleX(1.0F);
                    releasedChild.setScaleY(1.0F);
                    if (mHelper.settleCapturedViewAt(0, 0)) {
                        ViewCompat.postInvalidateOnAnimation(DragDismissLayout.this);
                    }
                }
            }

            @Override
            public int getViewVerticalDragRange(@NonNull View child) {
                return child.getHeight();
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                if (child.getTop() > 0) {
                    int height = getHeight();
                    float sx = (float) child.getTop() / height;
                    child.setScaleX(1 - sx);
                    child.setScaleY(1 - sx);
                } else {
                    child.setScaleX(1.0f);
                    child.setScaleY(1.0f);
                }
                return top;
            }

            @Override
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
                int top1 = changedView.getTop();
                if (top1 > 0) {
                    int height = getHeight();
                    float sx = (float) changedView.getTop() / height;
                    int alpha = (int) ((int) 250 * (1 - sx));
                    int argb = Color.argb(alpha,0,0,0);
                    setBackgroundColor(argb);

                } else {
                    setBackgroundColor(Color.BLACK);
                }
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

    @Override
    public void computeScroll() {
        if (mHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(DragDismissLayout.this);
        }
    }

    public interface DragDismissCallBack {
        boolean isCanDrag();

        void dismiss();
    }
}
