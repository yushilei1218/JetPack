package com.test.shileiyu.jetpack.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import com.test.shileiyu.jetpack.common.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @date 2018/9/11
 */

public class ItemPickerView extends View {

    private List<Child> mItems = new ArrayList<>();

    private GestureDetector mDetector;

    private int itemHeight = 150;

    private int showCount = 5;

    private TextPaint mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private RectF mCenter = new RectF();
    VelocityTracker mTracker;
    Scroller mScroller;

    OnItemSelectListener mListener;

    public void setListener(OnItemSelectListener listener) {
        mListener = listener;
    }

    public ItemPickerView(Context context) {
        this(context, null);
    }

    public ItemPickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                offsetChildren(-distanceY);
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return true;
            }
        });
        mTracker = VelocityTracker.obtain();
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(36f);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mScroller = new Scroller(context);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.LTGRAY);
    }

    private void resetUI() {
        Child child = getCenterNearestChild();
        if (child == null) {
            return;
        }
        float cy = getHeight() / 2f;
        float distanceY = cy - child.mLocation.y;
        if (Math.abs(distanceY) >= itemHeight) {

            class Update implements ValueAnimator.AnimatorUpdateListener {
                private final float total;
                private float past = 0;

                Update(float total) {
                    this.total = total;
                }

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Float animatedValue = (Float) animation.getAnimatedValue();
                    float now = animatedValue * total;
                    float delta = now - past;
                    offsetChildren(delta);
                    past = now;
                }
            }
            //动画
            ValueAnimator va = ValueAnimator.ofFloat(0f, 1f);
            va.setDuration(200);
            va.setInterpolator(new DecelerateInterpolator());
            va.addUpdateListener(new Update(distanceY));
            va.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    checkSelected();
                }
            });
            va.start();
        } else {
            offsetChildren(distanceY);
            checkSelected();
        }
    }

    private Child getCenterNearestChild() {
        if (Util.isEmpty(mItems)) {
            return null;
        }
        float cy = getHeight() / 2f;
        Child target = null;
        float dis = Float.MAX_VALUE;
        for (Child child : mItems) {
            float nowDis = Math.abs(cy - child.mLocation.y);
            if (nowDis <= dis) {
                dis = nowDis;
                target = child;
            }
        }
        return target;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int newHMS = MeasureSpec.makeMeasureSpec(showCount * itemHeight, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, newHMS);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float cy = h / 2f;
        float per = itemHeight / 2f;
        mCenter.set(0, cy - per, w, cy + per);
        setUpInitLocation();
        offset2SelectLocation();
    }

    private void offset2SelectLocation() {
        if (!Util.isEmpty(mItems)) {
            Child first = null;
            for (Child c : mItems) {
                if (c.isSelect) {
                    first = c;
                    break;
                }
            }
            if (first != null) {
                offsetChildren(getCenterY() - first.mLocation.y);
            }
        }
    }

    private float getCenterY() {
        return getHeight() / 2f;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTracker.addMovement(event);
        mDetector.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mTracker.computeCurrentVelocity(1000);
            float yVelocity = mTracker.getYVelocity();
            Log.d("ItemPickerView", "yVelocity=" + yVelocity);
            resetUI();

        }
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(mCenter, mPaint);

        if (Util.isEmpty(mItems)) {
            return;
        }
        for (Child c : mItems) {
            canvas.drawText(c.getName(), c.mLocation.x, c.mLocation.y, mTextPaint);
        }
    }


    private void offsetChildren(float distanceY) {
        if (distanceY != 0 && !Util.isEmpty(mItems)) {
            for (int i = 0; i < mItems.size(); i++) {
                mItems.get(i).mLocation.offset(0, distanceY);
            }
            invalidate();
        }
    }

    public interface OnItemSelectListener {
        void onItem(Child item);
    }

    public void setDisplayItems(final List<Child> data) {
        post(new Runnable() {
            @Override
            public void run() {
                if (Util.isEmpty(data)) {
                    mItems.clear();
                    invalidate();
                } else {
                    mItems.clear();
                    mItems.addAll(data);
                    setUpInitLocation();
                    offset2SelectLocation();
                    invalidate();
                }
            }
        });
    }

    private void checkSelected() {
        if (!Util.isEmpty(mItems)) {
            Child select = null;
            for (Child child : mItems) {
                PointF pointF = child.mLocation;
                boolean contains = mCenter.contains(pointF.x, pointF.y);
                if (contains && !child.isSelect) {
                    select = child;
                }
            }
            if (select != null) {
                clearSelect();
                select.setSelect(true);
                Util.showToast(select.getName());
                if (mListener != null) {
                    mListener.onItem(select);
                }
            }
        }
    }

    private void clearSelect() {
        for (Child child : mItems) {
            child.setSelect(false);
        }
    }

    private void setUpInitLocation() {
        if (!Util.isEmpty(mItems)) {
            int width = getWidth();
            int height = getHeight();
            float cx = width / 2f;
            float cy = height / 2f;

            for (int i = 0; i < mItems.size(); i++) {
                mItems.get(i).mLocation.set(cx, cy + i * itemHeight);
            }
        }
    }

    public void setSelect() {

    }

    public abstract static class Child {
        PointF mLocation = new PointF();
        public Object extra;
        public boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public abstract String getName();

    }
}
