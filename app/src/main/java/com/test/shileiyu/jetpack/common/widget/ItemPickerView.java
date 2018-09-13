package com.test.shileiyu.jetpack.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Looper;
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

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @date 2018/9/11
 */

public class ItemPickerView extends View {
    private static final String TAG = "ItemPickerView";
    private static final float VELOCITY_Y_RATIO = 1 / 8f;

    private List<BaseItem> mItems = new ArrayList<>();

    private GestureDetector mDetector;

    private int itemHeight = 150;

    private int showCount = 3;

    private TextPaint mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private RectF mCenter = new RectF();

    private VelocityTracker mTracker;

    private OnItemSelectListener mListener;

    private int mMinimumVelocity;

    private int mMaximumVelocity;

    private FlingRunnable mFlingRunnable;

    private boolean isSettling = false;

    private int mSelectAreaColor;

    private int mNoneSelectAreaColor;

    private int mTextOffset;

    private ValueAnimator mUiSettlerAni;

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
        });

        ViewConfiguration configuration = ViewConfiguration.get(context);
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = (int) (configuration.getScaledMaximumFlingVelocity() * VELOCITY_Y_RATIO);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.LTGRAY);

        int textSize = 24;
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ItemPickerView);
            mSelectAreaColor = ta.getColor(R.styleable.ItemPickerView_item_select_area_color, Color.RED);
            mNoneSelectAreaColor = ta.getColor(R.styleable.ItemPickerView_item_none_select_area_color, Color.RED);
            itemHeight = ta.getDimensionPixelSize(R.styleable.ItemPickerView_item_height, 150);
            textSize = ta.getDimensionPixelSize(R.styleable.ItemPickerView_item_text_size, 24);
            showCount = ta.getInt(R.styleable.ItemPickerView_show_count, 3);
            if (showCount < 3) {
                showCount = 3;
            }
            ta.recycle();
        }

        mTextPaint.setTextSize(textSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetricsInt metricsInt = mTextPaint.getFontMetricsInt();

        mTextOffset = (int) ((Math.abs(metricsInt.ascent) - Math.abs(metricsInt.descent)) / 2f);

    }

    @Override
    protected void onDetachedFromWindow() {
        if (mTracker != null) {
            mTracker.clear();
            mTracker.recycle();
        }
        super.onDetachedFromWindow();
    }

    private void resetUI() {
        BaseItem baseItem = getCenterNearestChild();
        if (baseItem == null) {
            return;
        }
        float distanceY = getCenterY() - baseItem.mLocation.y;
        if (Math.abs(distanceY) >= itemHeight / 4f) {
            //动画复位
            mUiSettlerAni = ValueAnimator.ofFloat(0f, 1f);
            mUiSettlerAni.setDuration(150);
            mUiSettlerAni.setInterpolator(new DecelerateInterpolator());
            mUiSettlerAni.addUpdateListener(new UiSettler(distanceY));
            mUiSettlerAni.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    checkSelectedAndCallBack();
                    isSettling = false;
                }

                @Override
                public void onAnimationStart(Animator animation, boolean isReverse) {
                    isSettling = true;
                }
            });
            mUiSettlerAni.start();
        } else {
            offsetChildren(distanceY);
            checkSelectedAndCallBack();
        }
    }

    /**
     * 获取当前数据源中离中心点最近的item
     *
     * @return null or item
     */
    @Nullable
    private BaseItem getCenterNearestChild() {
        if (Util.isEmpty(mItems)) {
            return null;
        }
        float cy = getCenterY();
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
        float cy = getCenterY();
        float deltaY = itemHeight / 2f;
        mCenter.set(0, cy - deltaY, w, cy + deltaY);
        setUpInitLocation();
        offset2SelectLocation();
    }

    private void offset2SelectLocation() {
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
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
        if (mTracker == null) {
            mTracker = VelocityTracker.obtain();
        }
        mTracker.addMovement(event);
        mDetector.onTouchEvent(event);

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {

            mTracker.computeCurrentVelocity(1000, mMaximumVelocity);
            float yVelocity = mTracker.getYVelocity();
            //触发fling
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
            float itemX = c.mLocation.x;
            float itemY = c.mLocation.y;
            boolean contains = mCenter.contains(itemX, itemY);
            if (contains) {
                mTextPaint.setColor(mSelectAreaColor);
            } else {
                mTextPaint.setColor(mNoneSelectAreaColor);
            }
            canvas.drawText(c.getName(), itemX, itemY + mTextOffset, mTextPaint);
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

    /**
     * 设置当前Picker 需要展示的数据项
     *
     * @param data 为空时清空展示，否则展示
     */
    public void setDisplayItems(final List<BaseItem> data) {
        Runnable task = new Runnable() {

            @Override
            public void run() {
                endAnimationIfNeed();

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
        };
        if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
            task.run();
        } else {
            post(task);
        }
    }

    /**
     * 触发检查当前数据项集合，确定被选中的item，触发回调
     */
    private void checkSelectedAndCallBack() {
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

    private void endAnimationIfNeed() {
        if (mFlingRunnable != null) {
            mFlingRunnable.endFling();
        }
        isSettling = false;
        if (mUiSettlerAni != null && mUiSettlerAni.isRunning()) {
            mUiSettlerAni.cancel();
        }

    }

    /**
     * item被选中监听器
     */
    public interface OnItemSelectListener {
        /**
         * 内部item被选中了
         *
         * @param item 被选中的item
         */
        void onItemSelect(@NonNull BaseItem item);
    }

    /**
     * Picker数据项基类
     */
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

    /**
     * UI复位-被选中的item动画回滚到恰当位置
     */
    private class UiSettler implements ValueAnimator.AnimatorUpdateListener {
        private final float total;
        private float past = 0;

        UiSettler(float total) {
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

    /**
     * Fling处理类
     */
    private class FlingRunnable implements Runnable {

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

        void endFling() {
            removeCallbacks(this);
            mScroller.abortAnimation();
        }

        void start(int yVelocity) {
            int initialY = yVelocity < 0 ? Integer.MAX_VALUE : 0;
            mLastFlingY = initialY;
            mScroller.fling(0, initialY, 0, yVelocity, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
            post(this);
        }
    }
}
