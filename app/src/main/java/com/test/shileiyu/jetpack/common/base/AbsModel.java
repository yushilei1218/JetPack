package com.test.shileiyu.jetpack.common.base;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.util.Log;

import com.test.shileiyu.jetpack.common.http.IDisposableManager;
import com.test.shileiyu.jetpack.common.util.Util;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author shilei.yu
 * @date 2018/8/15
 */

public abstract class AbsModel implements IDisposableManager, GenericLifecycleObserver {
    private static final String TAG = "AbsModel";

    private List<Disposable> data = new ArrayList<>();

    @Override
    public synchronized void add(@NonNull Disposable d) {
        Log.d(TAG, "add Disposable " + d.toString() + " " + threadName());
        data.add(d);
    }

    @Override
    public synchronized void remove(@NonNull Disposable d) {
        Log.d(TAG, "remove Disposable " + d.toString() + threadName());
        data.remove(d);
    }

    @Override
    public synchronized void onCleared() {
        if (!Util.isEmpty(data)) {
            for (Disposable d : data) {
                if (!d.isDisposed()) {
                    Log.d(TAG, "cancel Disposable " + d.toString() + threadName());
                    try {
                        d.dispose();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        data.clear();
    }

    private String threadName() {
        return " Thread=" + Thread.currentThread().getName();
    }

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        if (event != null && event == Lifecycle.Event.ON_DESTROY) {
            onCleared();
        }
    }
}
