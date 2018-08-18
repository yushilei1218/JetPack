package com.test.shileiyu.jetpack.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.util.Util;

import java.util.List;

/**
 * @author shilei.yu
 * @date 2018/8/16
 */

public class MatrixView extends View {
    String TAG = "MatrixView";
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

    private Bitmap mLockBitmap;
    private Bitmap mNormalBitmap;
    private Bitmap mCheckBitmap;

    private final Rect bitmapBound = new Rect();

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

    private int colorSelect;
    private int colorNormal;
    private int colorLock;

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

        mLockBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.seat_lock);
        mNormalBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.seat_normal);
        mCheckBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.seat_checked);

        colorLock = getResources().getColor(R.color.colorLock);
        colorNormal = getResources().getColor(R.color.colorNormal);
        colorSelect = getResources().getColor(R.color.colorSelect);

        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();
                float mScaleX = getMScaleX();
                if (scaleFactor * mScaleX > 2) {
                    scaleFactor = 2f / mScaleX;
                }
                if (scaleFactor * mScaleX < 0.8) {
                    scaleFactor = 0.8f / mScaleX;
                }
                Log.d(TAG, "onScale " + scaleFactor + " mScaleX=" + mScaleX);
                mMatrix.postScale(scaleFactor, scaleFactor);

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
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                mMatrix.postTranslate(-distanceX, -distanceY);

                invalidate();
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });
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
                    color = colorLock;
                } else if (s.isSelect()) {
                    color = colorSelect;
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

    private void transfer2ThumbnailBound(Rect areaBound, Rect intersectBound, Rect save) {
        float rate = (float) mThumbnailHeight / mAreaViewHeight;
        int offsetX = areaBound.left;
        int offsetY = areaBound.top;
        areaBound.offset(-offsetX, -offsetY);
        intersectBound.offset(-offsetX, -offsetY);
        save.top= (int) (intersectBound.top*rate);
        save.left= (int) (intersectBound.left*rate);
        save.right= (int) (intersectBound.right*rate);
        save.bottom= (int) (intersectBound.bottom*rate);
    }

    private void computeAreaViewBound(Rect bound) {
        float scaleX = getMScaleX();
        bound.set(0, 0, mAreaViewWidth, mAreaViewHeight);
        int mTranslateX = (int) getMTranslateX();
        int mTranslateY = (int) getMTranslateY();
        Log.d(TAG, "TX=" + mTranslateX + " TY=" + mTranslateY+" scaleX="+scaleX);
        bound.offset(mTranslateX, mTranslateY);
        bound.top= (int) (bound.top*scaleX);
        bound.left= (int) (bound.left*scaleX);
        bound.right= (int) (bound.right*scaleX);
        bound.bottom= (int) (bound.bottom*scaleX);
    }

    private void drawNumberDecorate(Canvas canvas) {

    }

    private void drawSeat(Canvas canvas) {
        canvas.save();
        canvas.translate(getMTranslateX(), getMTranslateY());
        canvas.scale(getMScaleX(), getMScaleX());

        for (ISeat s : mSeats) {
            //确定座位在视图上的初始化坐标
            computeSeatBoundInView(tempBound, s, mArea);
            Bitmap temp;
            if (s.isSelect()) {
                temp = mCheckBitmap;
            } else if (s.isSold()) {
                temp = mLockBitmap;
            } else {
                temp = mNormalBitmap;
            }
            bitmapBound.set(0, 0, temp.getWidth(), temp.getHeight());
            canvas.drawBitmap(temp, bitmapBound, tempBound, mPaint);
        }
        canvas.restore();
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
        return true;
    }

    float[] m = new float[9];

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

}
