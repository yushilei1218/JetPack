package com.test.shileiyu.jetpack.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.test.shileiyu.jetpack.common.util.Util;

/**
 * @author shilei.yu
 * @date 2018/8/16
 */

public class MatrixView extends View {
    String TAG = "MatrixView";
    private int per = 100;
    TextPaint mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    Matrix mMatrix = new Matrix();
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mDetector;

    public MatrixView(Context context) {
        super(context, null);
    }

    public MatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.RED);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(14);

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
                float mTranlateX = getMTranlateX();
                float mTranlateY = getMTranlateY();
                float mScaleX = getMScaleX();
                float x = e.getX();
                float y = e.getY();
                float oldX = (x - mTranlateX) / mScaleX;
                float oldY = (y - mTranlateY) / mScaleX;
                String msg = "x =" + x + " y=" + y + " tx=" + mTranlateX + "" +
                        " ty=" + mTranlateY + " scale=" + mScaleX + " oldx= " + oldX + " oldY=" + oldY;
                Log.d(TAG, msg);
                Util.showToast(msg);
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setMatrix(mMatrix);

        int width = getWidth();
        int height = getHeight();

        int column = width / per;
        int row = height / per;
        for (int i = 0; i < row; i++) {
            int x = i * per;
            for (int j = 0; j < column; j++) {
                int y = j * 100;
                canvas.drawText(x + "," + y, x, y, mPaint);
            }
        }
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

    private float getMTranlateX() {
        mMatrix.getValues(m);
        return m[Matrix.MTRANS_X];
    }

    private float getMTranlateY() {
        mMatrix.getValues(m);
        return m[Matrix.MTRANS_Y];
    }

}
