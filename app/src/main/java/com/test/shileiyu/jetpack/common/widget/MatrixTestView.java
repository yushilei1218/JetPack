package com.test.shileiyu.jetpack.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MatrixTestView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    private static final int OFFSET = 100;
    private RectF tempBound = new RectF(0, 0, 100, 100);

    private Matrix mMatrix = new Matrix();

    public MatrixTestView(Context context) {
        this(context, null);
    }

    public MatrixTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);

        textPaint.setColor(Color.BLUE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(12);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //先后很重要 会影响后面
        mMatrix.postScale(2, 2);
        mMatrix.postTranslate(w / 2, h / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int column = width / OFFSET;
        int row = height / OFFSET;
        for (int i = 0; i < column; i++) {
            int x = i * OFFSET;
            for (int j = 0; j < row; j++) {
                int y = j * OFFSET;
                String text = "x:" + x + ",y:" + y;
                canvas.drawText(text, x, y, textPaint);
            }
        }
        canvas.save();
        canvas.translate(100, 100);
        canvas.scale(2, 2);
        canvas.drawRect(tempBound, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(0, 100);
        canvas.drawRect(tempBound, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(-200, -200);
        canvas.scale(4, 4);
        canvas.drawRect(tempBound, mPaint);
        canvas.restore();


        mMatrix.mapRect(tempBound);

        canvas.drawRect(tempBound, mPaint);


        Log.d("MatrixTestView", tempBound.toString() + " cx=" + getWidth() / 2 + " cy=" + getHeight() / 2);
        tempBound.set(0, 0, 100, 100);
    }
}
