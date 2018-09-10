package com.test.shileiyu.jetpack.common.util;

import android.support.annotation.NonNull;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shilei.yu
 * @date 2018/9/10
 */

public class ThreadPool {
    private static AtomicInteger sAtomicInteger = new AtomicInteger();
    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(4,
            Integer.MAX_VALUE >> 2, 30, TimeUnit.SECONDS
            , new LinkedBlockingDeque<Runnable>(), new ThreadFactory() {
        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("sub thread:" + sAtomicInteger);
            sAtomicInteger.incrementAndGet();
            return thread;
        }
    });

    public static void submit(Runnable task) {
        EXECUTOR.submit(task);
    }
}
