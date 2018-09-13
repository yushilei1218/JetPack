package com.test.shileiyu.jetpack.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

/**
 * @author shilei.yu
 * @date 2018/9/13
 */

public class MiButton extends Button {
    public MiButton(Context context) {
        super(context);
    }

    public MiButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MiButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        String s = "l=" + l + " t=" + t + " r=" + r + " b=" + b;
        Log.d("MiButton", "layout " + s);
        super.layout(l, t, r, b);
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        String s = "l=" + l + " t=" + t + " r=" + r + " b=" + b;
        Log.d("MiButton", "setFrame " + s);
        return super.setFrame(l, t, r, b);
    }
}
