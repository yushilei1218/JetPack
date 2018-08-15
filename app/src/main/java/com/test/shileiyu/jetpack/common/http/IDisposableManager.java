package com.test.shileiyu.jetpack.common.http;

import android.support.annotation.NonNull;

import io.reactivex.disposables.Disposable;

/**
 * @author shilei.yu
 * @date 2018/8/15
 */

public interface IDisposableManager {
    void add(@NonNull Disposable d);

    void remove(@NonNull Disposable d);

    void onCleared();
}
