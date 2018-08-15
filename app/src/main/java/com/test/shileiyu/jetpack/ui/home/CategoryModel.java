package com.test.shileiyu.jetpack.ui.home;

import android.annotation.SuppressLint;
import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.test.shileiyu.jetpack.common.base.AbsModel;
import com.test.shileiyu.jetpack.common.bean.Album;
import com.test.shileiyu.jetpack.common.bean.HttpResult;
import com.test.shileiyu.jetpack.common.bean.KeyWord;
import com.test.shileiyu.jetpack.common.bean.ModelState;
import com.test.shileiyu.jetpack.common.bean.SubTab;
import com.test.shileiyu.jetpack.common.bean.TwoTuple;
import com.test.shileiyu.jetpack.common.http.NetWork;
import com.test.shileiyu.jetpack.common.http.ProxySubscriber;
import com.test.shileiyu.jetpack.common.util.Util;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author shilei.yu
 * @date 2018/8/9
 */

public class CategoryModel extends AbsModel {

    MutableLiveData<TwoTuple<List<Album>, ModelState>> mLiveData = new MutableLiveData<>();

    private List<Album> data = new ArrayList<>();

    private int pageId;

    private List<KeyWord> temp;

    private SubTab mTab;

    public CategoryModel(SubTab tab) {
        this.mTab = tab;
    }

    public void setKeyWord(List<KeyWord> data) {
        temp = data;
    }

    @SuppressLint("CheckResult")
    public void load(final boolean isFirst) {
        if (isFirst) {
            pageId = 1;
        } else {
            pageId++;
        }
        StringBuilder metadataSb = new StringBuilder();
        if (temp != null && temp.size() > 0) {
            for (KeyWord t : temp) {
                if (t.parent != null) {
                    if (t.parent.id == t.id) {
                        continue;
                    }
                    metadataSb.append(t.parent.id).append(":").append(t.id).append(",");
                }
            }
        }
        if (metadataSb.length() > 0) {
            metadataSb.deleteCharAt(metadataSb.length() - 1);
        }

        NetWork.sApi.get(pageId, 20, "hot", metadataSb.toString(), mTab.keywordId, mTab.categoryId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProxySubscriber<HttpResult<List<Album>>>(this, "请求列表") {

                    @Override
                    public void onSuccess(@NonNull HttpResult<List<Album>> bean) {
                        if (isFirst) {
                            data.clear();
                        }
                        if (!Util.isEmpty(bean.list)) {
                            data.addAll(bean.list);
                        }
                        ModelState modelState = new ModelState();
                        modelState.isSuccess = true;
                        mLiveData.setValue(new TwoTuple<>(data, modelState));
                        Util.showToast("加载列表成功");
                    }

                    @Override
                    public void onFail(int code, String msg) {
                        Util.showToast("加载列表失败");
                        mLiveData.setValue(new TwoTuple<List<Album>, ModelState>(null, new ModelState()));
                    }
                });

    }

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        super.onStateChanged(source, event);
        Log.d("CategoryModel", source.toString() + " " + event.name());
    }
}
