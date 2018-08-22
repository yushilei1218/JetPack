package com.test.shileiyu.jetpack.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @date 2018/8/22
 */

public abstract class NineGridLayout extends ViewGroup {
    public String name = "";

    private static final String TAG = "NineGridLayout";

    private int childSpacing = 10;
    private int mChildWidth;
    private List<View> mShowViews = new ArrayList<>();
    private List<View> mViews = new ArrayList<>();

    public NineGridLayout(Context context) {
        this(context, null);
    }

    public NineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        for (int i = 0; i < 9; i++) {
            View child = newChild(this);
            mViews.add(child);
            child.setLayoutParams(new WindowManager.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            attachViewToParent(child, i, child.getLayoutParams());
        }
        detachAllViewsFromParent();
    }

    protected abstract View newChild(NineGridLayout nineGridLayout);

    public List<View> getShowViews() {
        return mShowViews;
    }

    public void showChildCount(int count) {
        detachAllViewsFromParent();
        mShowViews.clear();
        for (int i = 0; i < count; i++) {
            View view = mViews.get(i);
            mShowViews.add(view);
            attachViewToParent(view, i, view.getLayoutParams());
        }
        requestLayout();
    }

    private void log(String msg) {
        Log.d(TAG, name + " " + msg);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.UNSPECIFIED) {
            throw new RuntimeException("NineGridLayout 无法使用UNSPECIFIED");
        }
        int height;
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int paddingTop = getPaddingTop();

        int contentWidth = width - paddingLeft - paddingRight - 2 * childSpacing;

        mChildWidth = contentWidth / 3;

        int childCount = getChildCount();
        log("onMeasure childCount=" + childCount);

        switch (childCount) {
            case 0:
            case 1:
                height = contentWidth / 2 + paddingBottom + paddingTop;
                doMeasureChildren(mChildWidth, contentWidth / 2);
                break;
            case 2:
            case 3:
                height = mChildWidth + paddingBottom + paddingTop;
                doMeasureChildren(mChildWidth, mChildWidth);
                break;
            case 4:
            case 5:
            case 6:
                height = mChildWidth * 2 + childSpacing + paddingBottom + paddingTop;
                doMeasureChildren(mChildWidth, mChildWidth);
                break;
            case 7:
            case 8:
            case 9:
                height = mChildWidth * 3 + childSpacing * 2 + paddingBottom + paddingTop;
                doMeasureChildren(mChildWidth, mChildWidth);
                break;
            default:
                height = 0;
                break;
        }
        setMeasuredDimension(width, height);
    }

    private void doMeasureChildren(int childWidth, int childHeight) {

        int childCount = getChildCount();
        if (childCount < 0) {
            return;
        }
        if (childCount == 1) {
            int nw = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST);
            int nh = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChild(getChildAt(0), nw, nh);
            return;
        }

        int nw = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
        int nh = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            measureChild(childAt, nw, nh);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        log("onLayout count=" + count);
        if (count == 0) {
            return;
        }
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        if (count == 1) {
            View child = getChildAt(0);
            child.layout(paddingLeft, paddingTop, child.getMeasuredWidth(), b - getPaddingBottom());
            return;
        }
        if (count == 4) {
            layoutChildren(2);
            return;
        }

        layoutChildren(3);
    }

    private void layoutChildren(int columnNum) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int row = i / columnNum;
            int column = i % columnNum;
            int left = paddingLeft + column * (mChildWidth + childSpacing);
            int top = paddingTop + row * (childSpacing + mChildWidth);
            child.layout(left, top, left + mChildWidth, top + mChildWidth);
        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        log("onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        log("onDetachedFromWindow");
    }
}
