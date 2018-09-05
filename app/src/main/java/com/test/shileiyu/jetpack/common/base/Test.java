package com.test.shileiyu.jetpack.common.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.test.shileiyu.jetpack.BaseApp;
import com.test.shileiyu.jetpack.R;

/**
 * @author shilei.yu
 * @date 2018/9/4
 */

public class Test {
    public void test() {
        BitmapFactory.Options opts = new BitmapFactory.Options();

        opts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(BaseApp.sApplication.getResources(), R.mipmap.arrow_down, opts);
        int outWidth = opts.outWidth;
//        Bitmap bitmap1 = Bitmap.createBitmap();
//        bitmap.
    }
}
