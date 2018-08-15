package com.test.shileiyu.jetpack.ui.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.test.shileiyu.jetpack.common.bean.SubTab;
import com.test.shileiyu.jetpack.common.bean.TabResult;
import com.test.shileiyu.jetpack.common.http.NetWork;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author shilei.yu
 * @date 2018/8/9
 */

public class TabModel {

    MutableLiveData<List<SubTab>> mLiveData = new MutableLiveData<>();
    private Disposable mSubscribe;

    public LiveData<List<SubTab>> get() {
        return mLiveData;
    }

    public void getTabs() {
        mSubscribe = NetWork.sApi.getAllTab().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<TabResult>() {
            @Override
            public void accept(TabResult tabResult) throws Exception {
                mLiveData.setValue(tabResult.keywords);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

}
