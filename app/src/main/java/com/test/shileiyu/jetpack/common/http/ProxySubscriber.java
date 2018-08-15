package com.test.shileiyu.jetpack.common.http;

import android.support.annotation.NonNull;
import android.util.Log;

import com.test.shileiyu.jetpack.common.base.AbsModel;
import com.test.shileiyu.jetpack.common.bean.HttpResult;
import com.test.shileiyu.jetpack.common.bean.TabResult;
import com.test.shileiyu.jetpack.common.util.Util;

import org.reactivestreams.Subscription;

import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.operators.flowable.FlowableInternalHelper;
import io.reactivex.internal.subscribers.LambdaSubscriber;

/**
 * @author shilei.yu
 * @date 2018/8/15
 */

public abstract class ProxySubscriber<T> implements FlowableSubscriber<T>, Disposable {

    private static final String TAG = "ProxySubscriber";

    private IDisposableManager mManager;
    private String name = "";

    public ProxySubscriber() {
    }

    public ProxySubscriber(IDisposableManager manager) {
        mManager = manager;
    }

    public ProxySubscriber(IDisposableManager manager, String name) {
        mManager = manager;
        this.name = name;
    }

    @Override
    public String toString() {
        return "[ProxySubscriber " + name + "]";
    }

    private final LambdaSubscriber<T> realSubscriber = new LambdaSubscriber<>(new Consumer<T>() {
        @Override
        public void accept(T o) {
            //处理网络的逻辑
            if (o == null) {
                onFail(0, "http解析失败");
            } else {
                if (o instanceof HttpResult) {
                    onSuccess(o);
                } else if (o instanceof TabResult) {
                    onSuccess(o);
                } else {
                    onSuccess(o);
                }
            }
        }
    }, new Consumer<Throwable>() {
        @Override
        public void accept(Throwable throwable) {
            //网络异常的逻辑
            onFail(1, "网络异常");
        }
    }, Functions.EMPTY_ACTION, FlowableInternalHelper.RequestMax.INSTANCE);

    /**
     * 数据请求成功
     *
     * @param data 非空解析数据
     */
    public abstract void onSuccess(@NonNull T data);

    /**
     * 网络请求失败 或者业务失败
     *
     * @param code
     * @param msg
     */
    public abstract void onFail(int code, String msg);

    @Override
    public void onSubscribe(Subscription s) {
        manageDispose2ModelIfCan(this, true);
        realSubscriber.onSubscribe(s);
    }

    @Override
    public void onNext(T t) {
        log("onNext");
        manageDispose2ModelIfCan(this, false);
        realSubscriber.onNext(t);
    }

    @Override
    public void onError(Throwable t) {
        log("onError");
        manageDispose2ModelIfCan(this, false);
        realSubscriber.onError(t);
    }

    @Override
    public void onComplete() {
        log("onComplete");
        realSubscriber.onComplete();
    }

    @Override
    public void dispose() {
        realSubscriber.dispose();
    }

    @Override
    public boolean isDisposed() {
        return realSubscriber.isDisposed();
    }

    private void manageDispose2ModelIfCan(Disposable d, boolean isAdd) {
        if (mManager != null) {
            if (isAdd) {
                mManager.add(d);
            } else {
                mManager.remove(d);
            }
        }
    }

    private void log(String name) {
        Log.d(TAG, name + Util.threadName());
    }
}
