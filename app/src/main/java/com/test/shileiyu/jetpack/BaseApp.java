package com.test.shileiyu.jetpack;

import android.app.Application;

/**
 * @author shilei.yu
 * @date 2018/8/8
 */

public class BaseApp extends Application {
    public static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication=this;
    }
}
