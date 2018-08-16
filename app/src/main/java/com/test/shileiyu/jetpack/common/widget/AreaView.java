package com.test.shileiyu.jetpack.common.widget;

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

public class AreaView extends View implements ScaleGestureDetector.OnScaleGestureListener {

    private static final String TAG = "AreaView";

    private List<ISeat> mSeats;

    private OnSeatClickListener mSeatClickListener;

    private int mAreaWidth;
    private int mAreaHeight;

    private Matrix mMatrix = new Matrix();

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Bitmap mBitmapA;
    private Bitmap mBitmapB;

    ScaleGestureDetector mSGDetector;

    private float rate = 1.0f;

    private float showRate;

    private int mTop;
    private int mLeft;
    private int mRight;
    private int mBottom;

    private Rect tempBounds = new Rect();
    private Rect bunds = new Rect();

    private Path mPath = new Path();

    public AreaView(Context context) {
        super(context, null);
    }

    public AreaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        mBitmapA = BitmapFactory.decodeResource(getResources(), R.mipmap.select);
        mBitmapB = BitmapFactory.decodeResource(getResources(), R.mipmap.un_select);

        mSGDetector = new ScaleGestureDetector(context, this);
    }

    public void setSeatClickListener(OnSeatClickListener seatClickListener) {
        mSeatClickListener = seatClickListener;
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

        //视图中间点
        float cx = w / 2f;
        float cy = h / 2f;
        float areaRate = (float) mAreaWidth / mAreaHeight;
        float viewRate = (float) w / h;
        if (viewRate <= areaRate) {
            //场地在View上全部展示出来显示的比例值
            showRate = (float) w / mAreaWidth;
        } else {
            showRate = (float) h / mAreaHeight;
        }
        //确定内部视图最初的 4个角坐标
        mTop = (int) (cy - (mAreaHeight * showRate) / 2f);
        mLeft = (int) (cx - (mAreaWidth * showRate) / 2f);
        mRight = (int) (cx + (mAreaWidth * showRate) / 2f);
        mBottom = (int) (cy + (mAreaHeight * showRate) / 2f);
        String msg = "ViewWidth=" + w + " ViewHeight=" + h + " top=" + mTop + " left=" + mLeft + " right=" + mRight + " bottom=" + mBottom;
        Log.d(TAG, msg);

        mPath.reset();
        mPath.moveTo(mLeft, mTop);
        mPath.lineTo(mRight, mTop);
        mPath.lineTo(mRight, mBottom);
        mPath.lineTo(mLeft, mBottom);
        mPath.lineTo(mLeft, mTop);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        if (!isParamsCanDraw()) {
            return;
        }
        canvas.drawPath(mPath, mPaint);
        //
        for (ISeat s : mSeats) {
            drawSeat(canvas, s);
        }
    }

    private void drawSeat(Canvas canvas, ISeat s) {
        Bitmap temp;
        if (s.isSelect()) {
            temp = mBitmapB;
        } else {
            temp = mBitmapA;
        }

        int x = transferAreaX2ViewX(s.getXInArea());
        int y = transferAreaY2ViewY(s.getYInArea());
        int offset = 10;
        tempBounds.set(x - offset, y - offset, x + offset, y + offset);
        Log.d(TAG, tempBounds.toShortString());
        bunds.set(0, 0, temp.getWidth(), temp.getHeight());
        canvas.drawBitmap(temp, bunds, tempBounds, mPaint);
    }

    private int transferAreaX2ViewX(int xInArea) {
        float rate = (float) xInArea / mAreaWidth;
        int areaShowWith = mRight - mLeft;
        return (int) (mLeft + areaShowWith * rate);
    }

    private int transferAreaY2ViewY(int yInArea) {
        float rate = (float) yInArea / mAreaHeight;
        int areaShowHeight = mBottom - mTop;
        return (int) (mTop + areaShowHeight * rate);
    }

    float mX;
    float mY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(getContext(), "x=" + x + " y=" + y, Toast.LENGTH_SHORT).show();
                break;
            case MotionEvent.ACTION_MOVE:

                if (mX == 0 || mY == 0) {
                    this.mX = x;
                    this.mY = y;
                    break;
                }

                float v = mX - x;
                float v1 = mY - y;
                scrollBy((int) v, (int) v1);
                this.mX = x;
                this.mY = y;
                break;
            default:
                break;
        }
        return true;
    }


    private boolean isParamsCanDraw() {
        return mAreaHeight > 0 && mAreaWidth > 0 && !Util.isEmpty(mSeats);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }


    public interface OnSeatClickListener {
        void onClick(ISeat seat);
    }
}
