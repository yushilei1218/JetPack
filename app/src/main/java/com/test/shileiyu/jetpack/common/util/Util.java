package com.test.shileiyu.jetpack.common.util;

import android.graphics.Rect;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.test.shileiyu.jetpack.BaseApp;

import java.util.Collection;
import java.util.Set;

/**
 * @author shilei.yu
 * @date 2018/8/8
 */

public class Util {
    public static void showToast(String msg) {
        Toast.makeText(BaseApp.sApplication, msg, Toast.LENGTH_SHORT).show();
    }

    public static String toJson(Object o) {
        return JSON.toJSONString(o);
    }

    public static boolean isEmpty(Collection set) {
        return set == null || set.size() == 0;
    }

    public static String threadName() {
        return " Thread =" + Thread.currentThread().getName();
    }

    public static void computeIntersectBound(Rect src1, Rect src2, Rect save) {
        boolean intersects = Rect.intersects(src1, src2);
        if (intersects) {
            int newLeft;
            int newTop;
            int newRight;
            int newBottom;
            newLeft = src1.left > src2.left ? src1.left : src2.left;
            newTop = src2.top > src1.top ? src2.top : src1.top;
            newRight = src1.right > src2.right ? src2.right : src1.right;
            newBottom = src1.bottom > src2.bottom ? src2.bottom : src1.bottom;
            save.set(newLeft, newTop, newRight, newBottom);
        } else {
            save.set(0, 0, 0, 0);
        }
    }


    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    public static void main(String[] aa) {
        Lock lock = new Lock();
        lock.index = 0;
        lock.end = 5;
        Thread a1 = new Thread(new Run(lock), "T1");
        Thread a2 = new Thread(new Run(lock), "T2");
        Thread a3 = new Thread(new Run(lock), "T3");
        Thread a4 = new Thread(new Run(lock), "T4");
        Thread a5 = new Thread(new Run(lock), "T5");
        a1.start();
        a2.start();
        a3.start();
        a4.start();
        a5.start();
    }

    public static class Run implements Runnable {
        Lock mLock;

        public Run(Lock lock) {
            mLock = lock;
        }

        @Override
        public void run() {
            synchronized (mLock) {
                mLock.index++;
                if (mLock.isEnd()) {
                    System.out.println(Thread.currentThread().getName() + "  Hello");
                    System.out.println(Thread.currentThread().getName() + "  Word");
                    mLock.notifyAll();
                } else {
                    if (mLock.canNotify()) {

                        System.out.println(Thread.currentThread().getName() + "  Word");
                        mLock.notifyAll();
                    } else {
                        System.out.println(Thread.currentThread().getName() + "  Hello");
                        try {
                            mLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

        }
    }

    public static class Lock {
        int index;
        int end;

        public boolean isEnd() {
            return index == end;
        }

        public boolean canNotify() {
            return index > end;
        }
    }
}
