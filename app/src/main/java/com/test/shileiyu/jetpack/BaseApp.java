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

        //这是dev添加的一行代码

        //这是Master添加的一行代码

        //this is dev add two line

        //this is master add two line

        //master add 111
    }
}
