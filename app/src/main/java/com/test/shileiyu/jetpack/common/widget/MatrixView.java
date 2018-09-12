package com.test.shileiyu.jetpack.common.widget;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.VectorDrawable;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.util.Util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author shilei.yu
 * @date 2018/8/16
 */

public class MatrixView extends View {
    String TAG = "MatrixView";
    private static final float MAX_SCALE = 2f;
    private static final float ZOOM_SCALE = 1.7f;
    private static final float MIN_SCALE = 0.8f;

    TextPaint mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    Paint mThumbnailPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mIntersectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Matrix mMatrix = new Matrix();

    private ScaleGestureDetector mScaleGestureDetector;

    private GestureDetector mDetector;

    /**
     * 场地对象
     */
    private Area mArea;
    /**
     * 座位
     */
    private List<ISeat> mSeats;

    /**
     * 座位视图初始化宽度
     */
    private final float SEAT_WIDTH = 0.6f / Area.pxMetreRate;

    private final float THUMBNAIL_SEAT_WIDTH = 0.6F / Area.thumbnailPxMetreRate;
    /**
     * 场地在视图中初始化宽高
     */
    private int mAreaViewWidth;

    private int mAreaViewHeight;

    private final Rect tempBound = new Rect();
    private final Rect tempBound2 = new Rect();
    private final Rect tempBound3 = new Rect();

    private boolean isNeedDrawOverView = true;
    /**
     * 缩略图宽高
     */
    private int mThumbnailWidth;
    private int mThumbnailHeight;
    private Rect mThumbnailRect = new Rect();

    private int colorNormal;
    private int colorSold;
    private int colorLock;
    private int colorOVSold;

    private boolean isOnScrolling = false;

    private VectorDrawableCompat mSoldSeat;
    private VectorDrawableCompat mLockSeat;
    private VectorDrawableCompat mNormalSeat;

    public MatrixView(Context context) {
        super(context, null);
    }

    public MatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.RED);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(14);

        mThumbnailPaint.setStyle(Paint.Style.FILL);

        mIntersectPaint.setStyle(Paint.Style.STROKE);
        mIntersectPaint.setStrokeWidth(4);
        mIntersectPaint.setColor(getResources().getColor(R.color.colorStroke));

        colorLock = getResources().getColor(R.color.colorLock);
        colorSold = getResources().getColor(R.color.colorSold);
        colorOVSold = getResources().getColor(R.color.colorOVSold);
        colorNormal = getResources().getColor(R.color.colorNormal);

        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();
                float mScaleX = getMScaleX();
                if (scaleFactor * mScaleX > MAX_SCALE) {
                    scaleFactor = MAX_SCALE / mScaleX;
                }
                if (scaleFactor * mScaleX < MIN_SCALE) {
                    scaleFactor = MIN_SCALE / mScaleX;
                }
                Log.d(TAG, "onScale " + scaleFactor + " mScaleX=" + mScaleX);
                mMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());

                invalidate();
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {

            }
        });

        mDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                float translateX = getMTranslateX();
                float translateY = getMTranslateY();
                float mScaleX = getMScaleX();
                float x = e.getX();
                float y = e.getY();
                float oldX = (x - translateX) / mScaleX;
                float oldY = (y - translateY) / mScaleX;
                String msg = "x =" + x + " y=" + y + " tx=" + translateX + "" +
                        " ty=" + translateY + " scale=" + mScaleX + " oldx= " + oldX + " oldY=" + oldY;
                Log.d(TAG, msg);
                Util.showToast(msg);
                ISeat temp = null;
                if (!Util.isEmpty(mSeats)) {
                    for (ISeat s : mSeats) {
                        computeSeatBoundInView(tempBound, s, mArea);
                        if (tempBound.contains((int) oldX, (int) oldY)) {
                            Log.d(TAG, "Seat 被点中了 " + s.toString());
                            temp = s;
                            break;
                        }
                    }
                }
                if (temp != null) {
                    if (!temp.isSold()) {
                        temp.setSelect(!temp.isSelect());
                        invalidate();
                    }
                }
                autoZoomIfNeed(e);
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                isOnScrolling = true;

                mMatrix.postTranslate(-distanceX, -distanceY);

                invalidate();
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });

        mSoldSeat = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_seat_sold_new, context.getTheme());
        mLockSeat = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_seat_select_new, context.getTheme());
        mNormalSeat = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_seat_normal_new, context.getTheme());

    }

    private void autoZoomIfNeed(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        float scaleX = getMScaleX();
        if (scaleX < ZOOM_SCALE) {
            //需要以这个点为中心zoom
            startZoom(scaleX, ZOOM_SCALE, x, y);
        }
    }

    private void startZoom(float curScale, float toScale, float px, float py) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(curScale, toScale);
        ZoomAnimation animation = new ZoomAnimation(px, py);
        valueAnimator.addUpdateListener(animation);
        valueAnimator.setInterpolator((new DecelerateInterpolator()));
        valueAnimator.setDuration(400);
        valueAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w <= 0 || h <= 0) {
            return;
        }
        if (!isParamsCanDraw()) {
            return;
        }
        //计算出场地进入视图对应的占用视图的宽、高
        mAreaViewWidth = (int) (mArea.areaWidth / Area.pxMetreRate);
        mAreaViewHeight = (int) (mArea.areaHeight / Area.pxMetreRate);
        //计算缩略图宽高
        mThumbnailWidth = (int) (mArea.areaWidth / Area.thumbnailPxMetreRate);
        mThumbnailHeight = (int) (mArea.areaHeight / Area.thumbnailPxMetreRate);

        mMatrix.reset();
        float cx = w / 2f;
        float cy = h / 2f;
        float ax = mAreaViewWidth / 2f;
        float ay = mAreaViewHeight / 2f;
        mMatrix.postTranslate(cx - ax, cy - ay);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        if (Util.isEmpty(mSeats)) {
            return;
        }
        //画座位
        drawSeat(canvas);
        //画Number装饰
        drawNumberDecorate(canvas);
        //画缩略图
        drawOverView(canvas);
    }

    private void drawSeat(Canvas canvas) {
        canvas.save();
        canvas.translate(getMTranslateX(), getMTranslateY());
        canvas.scale(getMScaleX(), getMScaleX());

        for (ISeat s : mSeats) {
            //确定座位在视图上的初始化坐标
            computeSeatBoundInView(tempBound, s, mArea);
            VectorDrawableCompat vdc;
            if (s.isSelect()) {
                vdc = mLockSeat;
            } else if (s.isSold()) {
                vdc = mSoldSeat;
            } else {
                vdc = mNormalSeat;
            }
            if (vdc != null) {
                Log.d(TAG, vdc.toString());
                vdc.setBounds(tempBound);
                vdc.draw(canvas);
            }

        }
        canvas.restore();
    }

    private void drawOverView(Canvas canvas) {
        if (!isNeedDrawOverView) {
            return;
        }
        if (mThumbnailHeight > 0 && mThumbnailWidth > 0) {
            canvas.save();
            int width = getWidth();
            int left = width - mThumbnailWidth;
            canvas.translate(left, 0);
            mThumbnailRect.set(0, 0, mThumbnailWidth, mThumbnailHeight);
            canvas.clipRect(mThumbnailRect);
            canvas.drawColor(Color.parseColor("#55000000"));
            //画缩略图中的座位
            for (ISeat s : mSeats) {
                computeSeatBoundInThumbnail(tempBound, s, mArea);
                int color;
                if (s.isSold()) {
                    color = colorOVSold;
                } else if (s.isSelect()) {
                    color = colorLock;
                } else {
                    color = colorNormal;
                }
                mThumbnailPaint.setColor(color);
                canvas.drawRect(tempBound, mThumbnailPaint);
            }
            //画场地图与视图交集框
            tempBound.set(0, 0, getWidth(), getHeight());
            computeAreaViewBound(tempBound2);
            if (Rect.intersects(tempBound, tempBound2)) {
                //tempBound3是交集
                Util.computeIntersectBound(tempBound, tempBound2, tempBound3);
                Log.d(TAG, "view Bound " + tempBound.toString() + " area bound " + tempBound2.toString() + " 交集 bound " + tempBound3.toString());
                //将交集转换为缩略图内的Bound
                transfer2ThumbnailBound(tempBound2, tempBound3, tempBound);
                canvas.drawRect(tempBound, mIntersectPaint);
            }
            canvas.restore();
        }
    }

    private void drawNumberDecorate(Canvas canvas) {

    }

    private void transfer2ThumbnailBound(Rect areaBound, Rect intersectBound, Rect save) {
        float rate = (float) mThumbnailHeight / mAreaViewHeight;
        int offsetX = areaBound.left;
        int offsetY = areaBound.top;
        areaBound.offset(-offsetX, -offsetY);
        intersectBound.offset(-offsetX, -offsetY);
        save.top = (int) (intersectBound.top * rate);
        save.left = (int) (intersectBound.left * rate);
        save.right = (int) (intersectBound.right * rate);
        save.bottom = (int) (intersectBound.bottom * rate);
    }

    private void computeAreaViewBound(Rect bound) {
        float scaleX = getMScaleX();
        bound.set(0, 0, (int) (mAreaViewWidth * scaleX), (int) (mAreaViewHeight * scaleX));
        int mTranslateX = (int) getMTranslateX();
        int mTranslateY = (int) getMTranslateY();
        Log.d(TAG, "TX=" + mTranslateX + " TY=" + mTranslateY + " scaleX=" + scaleX);
        bound.offset(mTranslateX, mTranslateY);

        Log.d(TAG, "AreaViewBound " + bound.toShortString());
    }

    private void computeSeatBoundInView(Rect bound, ISeat s, Area area) {
        int areaWidth = area.areaWidth;
        int areaHeight = area.areaHeight;

        int xInArea = s.getXInArea();
        int yInArea = s.getYInArea();
        float x = ((float) xInArea / areaWidth) * mAreaViewWidth;
        float y = ((float) yInArea / areaHeight) * mAreaViewHeight;
        float per = SEAT_WIDTH / 2f;
        int left = (int) (x - per);
        int top = (int) (y - per);
        int right = (int) (x + per);
        int bottom = (int) (y + per);
        bound.set(left, top, right, bottom);
    }

    private void computeSeatBoundInThumbnail(Rect bound, ISeat s, Area area) {
        int areaWidth = area.areaWidth;
        int areaHeight = area.areaHeight;

        int xInArea = s.getXInArea();
        int yInArea = s.getYInArea();
        float x = ((float) xInArea / areaWidth) * mThumbnailWidth;
        float y = ((float) yInArea / areaHeight) * mThumbnailHeight;
        float per = THUMBNAIL_SEAT_WIDTH / 2f;
        int left = (int) (x - per);
        int top = (int) (y - per);
        int right = (int) (x + per);
        int bottom = (int) (y + per);
        bound.set(left, top, right, bottom);
    }

    private boolean isParamsCanDraw() {
        return mArea != null && mArea.areaHeight > 0 && mArea.areaWidth > 0;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        mScaleGestureDetector.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isOnScrolling) {
                isOnScrolling = false;
                autoTranslate2FitViewBoundIfNeed();
            }
        }
        return true;
    }

    private void autoTranslate2FitViewBoundIfNeed() {
        //视图显示的边界
        tempBound2.set(0, 0, getWidth(), getHeight());
        //计算当前场地边界
        computeAreaViewBound(tempBound);
        //计算 偏移
        int deltaLeft = tempBound.left - tempBound2.left;
        int deltaRight = tempBound.right - tempBound2.right;
        int deltaTop = tempBound.top - tempBound2.top;
        int deltaBottom = tempBound.bottom - tempBound2.bottom;
        int[] indents = new int[4];
        indents[0] = deltaLeft > 0 ? deltaLeft : 0;
        indents[1] = deltaTop < 0 ? deltaTop : 0;
        indents[2] = deltaRight < 0 ? deltaRight : 0;
        indents[3] = deltaBottom > 0 ? deltaBottom : 0;
        int deltaX = -(indents[0] + indents[2]);
        int deltaY = -(indents[1] + indents[3]);
        if (deltaX != 0 || deltaY != 0) {
            //自动平移
            int tx = (int) getMTranslateX();
            int ty = (int) getMTranslateY();
            ValueAnimator va = ValueAnimator.ofFloat(0f, 1f);
            va.setDuration(200);
            va.addUpdateListener(new TranslateListener(tx, deltaX, ty, deltaY));
            va.setInterpolator(new DecelerateInterpolator());
            va.start();
        }
    }

    private class TranslateListener implements ValueAnimator.AnimatorUpdateListener {
        int mStartX;
        int mDeltaX;
        int mStartY;
        int mDeltaY;

        TranslateListener(int startX, int deltaX, int startY, int deltaY) {
            mStartX = startX;
            mDeltaX = deltaX;
            mStartY = startY;
            mDeltaY = deltaY;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            Float value = (Float) animation.getAnimatedValue();
            float offsetX = value * mDeltaX;
            float offsetY = value * mDeltaY;
            float mTranslateX = getMTranslateX();
            float mTranslateY = getMTranslateY();
            float ox = offsetX - mTranslateX + mStartX;
            float oy = offsetY - mTranslateY + mStartY;
            mMatrix.postTranslate(ox, oy);
            invalidate();
        }
    }

    private float[] m = new float[9];

    private float getMScaleX() {
        mMatrix.getValues(m);
        return m[Matrix.MSCALE_X];
    }

    private float getMTranslateX() {
        mMatrix.getValues(m);
        return m[Matrix.MTRANS_X];
    }

    private float getMTranslateY() {
        mMatrix.getValues(m);
        return m[Matrix.MTRANS_Y];
    }

    public void setUpView(Area area, List<ISeat> seats) {
        mArea = area;
        mSeats = seats;
        invalidate();
    }

    private class ZoomAnimation implements ValueAnimator.AnimatorUpdateListener {
        float px;
        float py;

        ZoomAnimation(float px, float py) {
            this.px = px;
            this.py = py;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            Float value = (Float) animation.getAnimatedValue();
            float mScaleX = getMScaleX();
            float scale = value / mScaleX;
            mMatrix.postScale(scale, scale, px, py);
            invalidate();
        }
    }

}
