package com.test.shileiyu.jetpack.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.util.Util;

import java.util.List;

/**
 * @author shilei.yu
 * @date 2018/8/16
 */

public class AreaView extends View {

    private static final String TAG = "AreaView";

    private List<ISeat> mSeats;

    private OnSeatClickListener mSeatClickListener;

    private int mAreaWidth;
    private int mAreaHeight;

    private Matrix mMatrix = new Matrix();

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    ScaleGestureDetector mSGDetector;

    GestureDetector mGDetector;

    private Bitmap mBitmapChecked;
    private Bitmap mBitmapLocked;
    private Bitmap mBitmapNormal;

    public AreaView(Context context) {
        super(context, null);
    }

    public AreaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        mSGDetector = new ScaleGestureDetector(context, mOnScaleGestureListener);

        mGDetector = new GestureDetector(context, mOnGestureListener);

        mBitmapChecked = BitmapFactory.decodeResource(getResources(), R.mipmap.seat_checked);
        mBitmapLocked = BitmapFactory.decodeResource(getResources(), R.mipmap.seat_lock);
        mBitmapNormal = BitmapFactory.decodeResource(getResources(), R.mipmap.seat_normal);
    }


    public void setUpAreaView(int areaWidth, int areaHeight, List<ISeat> seats) {
        this.mAreaWidth = areaWidth;
        this.mAreaHeight = areaHeight;
        this.mSeats = seats;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        if (!isParamsCanDraw()) {
            return;
        }

        for (ISeat s : mSeats) {
            drawSeat(canvas, s);
        }
    }

    private void drawSeat(Canvas canvas, ISeat s) {

    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mSGDetector.onTouchEvent(event);
        mGDetector.onTouchEvent(event);
        return true;
    }


    private boolean isParamsCanDraw() {
        return mAreaHeight > 0 && mAreaWidth > 0 && !Util.isEmpty(mSeats);
    }

    private ScaleGestureDetector.OnScaleGestureListener mOnScaleGestureListener = new ScaleGestureDetector.OnScaleGestureListener() {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    };

    private GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }
    };

    public interface OnSeatClickListener {
        void onClick(ISeat seat);
    }
}
