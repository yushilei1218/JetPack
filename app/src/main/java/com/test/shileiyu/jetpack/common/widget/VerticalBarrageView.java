package com.test.shileiyu.jetpack.common.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

import com.test.shileiyu.jetpack.BaseApp;
import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.util.Util;

import java.util.LinkedList;
import java.util.List;

/**
 * @author shilei.yu
 * @date 2018/8/20
 */

public class VerticalBarrageView extends View {
    TextPaint mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    List<Item> data = new LinkedList<>();
    Rect tempBound = new Rect();
    Rect tempBound2 = new Rect();
    private final int HEADER_SIZE = 20;
    private final int MX = 30;
    private final int OFFSETY = 100;
    private boolean isAttach = false;
    private ValueAnimator mVa;

    public VerticalBarrageView(Context context) {
        super(context);
    }

    public VerticalBarrageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTextPaint.setTextSize(24);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!Util.isEmpty(data)) {
            for (Item i : data) {
                drawSingleItem(canvas, i);
            }
        }
    }

    private void drawSingleItem(Canvas canvas, Item i) {
        Bitmap temp;
        if (i.real != null && !i.real.isRecycled()) {
            temp = i.real;
        } else {
            temp = Item.pleaceHolder;
        }
        int offset = HEADER_SIZE / 2;
        tempBound.set(i.x - offset, i.y - offset, i.x + offset, i.y + offset);
        tempBound2.set(0, 0, temp.getWidth(), temp.getHeight());
        canvas.drawBitmap(temp, tempBound2, tempBound, null);
        canvas.drawText(i.text, i.x + HEADER_SIZE, i.y, mTextPaint);

    }

    public void setUpView(List<Item> data) {
        //1.关闭动画
        this.data.clear();
        this.data.addAll(data);
        formatData();
        startAnimationIfNeed();
    }

    private void startAnimationIfNeed() {
        if (isAttach && !Util.isEmpty(data)) {
            mVa = ValueAnimator.ofInt(0, 10000);
            mVa.setRepeatCount(-1);
            mVa.setDuration(1000 * 120);
            mVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    offsetItemsY();
                    invalidate();
                }
            });
            mVa.start();
        }
    }

    private void offsetItemsY() {
        if (!Util.isEmpty(data)) {
            for (Item i : data) {
                i.y -= 2;
            }
        }
        check(data);
    }

    private void check(List<Item> data) {
        Item item = data.get(0);
        if (item.y < -HEADER_SIZE) {
            item.y = data.get(data.size() - 1).y + OFFSETY;
            data.remove(item);
            data.add(item);
        }
    }

    private void formatData() {
        if (!Util.isEmpty(data)) {
            int index = 0;
            int height = getMeasuredHeight();
            for (Item i : data) {
                i.x = MX;
                i.y = height + index * OFFSETY;
                index++;
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isAttach = true;
        formatData();
        startAnimationIfNeed();
    }

    @Override
    protected void onDetachedFromWindow() {
        isAttach = false;
        if (mVa != null) {
            mVa.cancel();
        }
        super.onDetachedFromWindow();
    }

    public static class Item {
        int x;
        int y;
        Bitmap real;
        static Bitmap pleaceHolder = BitmapFactory.decodeResource(BaseApp.sApplication.getResources(), R.mipmap.ic_deafult);
        public String text;
    }
}
