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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.widget.OverScroller;
import android.widget.Scroller;

import com.test.shileiyu.jetpack.common.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @date 2018/9/11
 */

public class ItemPickerView extends View {

    private List<BaseItem> mItems = new ArrayList<>();

    private GestureDetector mDetector;

    private int itemHeight = 150;

    private int showCount = 5;

    private TextPaint mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private RectF mCenter = new RectF();
    VelocityTracker mTracker;
    Scroller mScroller;

    OnItemSelectListener mListener;

    private int mMinimumVelocity;

    private int mMaximumVelocity;

    private FlingRunnable mFlingRunnable;

    private boolean isSettling = false;

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
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity() / 8;
    }

    private void resetUI() {
        BaseItem baseItem = getCenterNearestChild();
        if (baseItem == null) {
            return;
        }
        float cy = getHeight() / 2f;
        float distanceY = cy - baseItem.mLocation.y;
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
            va.setDuration(150);
            va.setInterpolator(new DecelerateInterpolator());
            va.addUpdateListener(new Update(distanceY));
            va.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    checkSelected();
                    isSettling = false;
                }

                @Override
                public void onAnimationStart(Animator animation, boolean isReverse) {
                    isSettling = true;
                }
            });
            va.start();
        } else {
            offsetChildren(distanceY);
            checkSelected();
        }
    }

    private BaseItem getCenterNearestChild() {
        if (Util.isEmpty(mItems)) {
            return null;
        }
        float cy = getHeight() / 2f;
        BaseItem target = null;
        float dis = Float.MAX_VALUE;
        for (BaseItem baseItem : mItems) {
            float nowDis = Math.abs(cy - baseItem.mLocation.y);
            if (nowDis <= dis) {
                dis = nowDis;
                target = baseItem;
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
            BaseItem first = null;
            for (BaseItem c : mItems) {
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
        if (isSettling) {
            return false;
        }
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (mFlingRunnable != null) {
                mFlingRunnable.endFling();
            }
        }

        mTracker.addMovement(event);
        mDetector.onTouchEvent(event);

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {

            mTracker.computeCurrentVelocity(1000, mMaximumVelocity);
            float yVelocity = mTracker.getYVelocity();

            if (Math.abs(yVelocity) > mMinimumVelocity) {
                if (mFlingRunnable == null) {
                    mFlingRunnable = new FlingRunnable();
                }
                mFlingRunnable.start((int) yVelocity);
            } else {
                resetUI();
            }
            Log.d("ItemPickerView", "yVelocity=" + yVelocity + " mMinimumVelocity=" + mMinimumVelocity + " mMaximumVelocity=" + mMaximumVelocity);
        }
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(mCenter, mPaint);

        if (Util.isEmpty(mItems)) {
            return;
        }
        for (BaseItem c : mItems) {
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

    public void setDisplayItems(final List<BaseItem> data) {
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
            BaseItem select = null;
            for (BaseItem baseItem : mItems) {
                PointF pointF = baseItem.mLocation;
                boolean contains = mCenter.contains(pointF.x, pointF.y);
                if (contains && !baseItem.isSelect) {
                    select = baseItem;
                }
            }
            if (select != null) {
                clearSelect();
                select.setSelect(true);
                Util.showToast(select.getName());
                if (mListener != null) {
                    mListener.onItemSelect(select);
                }
            }
        }
    }

    private void clearSelect() {
        if (Util.isEmpty(mItems)) {
            return;
        }
        for (BaseItem baseItem : mItems) {
            baseItem.setSelect(false);
        }
    }

    private void setUpInitLocation() {
        if (Util.isEmpty(mItems)) {
            return;
        }
        int width = getWidth();
        int height = getHeight();

        if (width <= 0 || height <= 0) {
            return;
        }
        float cx = width / 2f;
        float cy = height / 2f;

        for (int i = 0; i < mItems.size(); i++) {
            mItems.get(i).mLocation.set(cx, cy + i * itemHeight);
        }
    }

    public interface OnItemSelectListener {
        /**
         * 内部item被选中了
         *
         * @param item 被选中的item
         */
        void onItemSelect(@NonNull BaseItem item);
    }

    public abstract static class BaseItem {
        PointF mLocation = new PointF();
        public Object extra;
        public boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        /**
         * 获取当前item 绘制时的名称
         *
         * @return name
         */
        public abstract String getName();
    }

    public class FlingRunnable implements Runnable {

        private final OverScroller mScroller;
        private int mLastFlingY;

        FlingRunnable() {
            mScroller = new OverScroller(getContext());
        }

        @Override
        public void run() {
            boolean more = mScroller.computeScrollOffset();
            if (more) {
                int currY = mScroller.getCurrY();
                int deltaY = mLastFlingY - currY;
                offsetChildren(-deltaY);
                mLastFlingY = currY;
                post(this);
            } else {
                resetUI();
            }
        }

        public void endFling() {
            removeCallbacks(this);
            mScroller.abortAnimation();
        }

        public void start(int yVelocity) {
            int initialY = yVelocity < 0 ? Integer.MAX_VALUE : 0;
            mLastFlingY = initialY;
            mScroller.fling(0, initialY, 0, yVelocity, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
            post(this);
        }
    }
}
