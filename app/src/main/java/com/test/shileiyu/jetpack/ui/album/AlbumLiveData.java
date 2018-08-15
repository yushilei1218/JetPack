package com.test.shileiyu.jetpack.ui.album;

import android.arch.lifecycle.LiveData;
import android.widget.Toast;

import com.test.shileiyu.jetpack.common.bean.Album;
import com.test.shileiyu.jetpack.common.bean.HttpResult;
import com.test.shileiyu.jetpack.common.http.NetWork;
import com.test.shileiyu.jetpack.common.util.Util;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author shilei.yu
 * @date 2018/8/8
 */

public class AlbumLiveData extends LiveData<List<Album>> {
    private List<Album> data = new ArrayList<>();

    private int page = 1;

    private Disposable mSubscribe;

    public void requestAlbums(final boolean isFirst) {
        if (isFirst) {
            page = 1;
        } else {
            page++;
        }
        int pageSize = 20;
        mSubscribe = NetWork.sApi.getAlbumList(page, pageSize, 69)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HttpResult<List<Album>>>() {
                    @Override
                    public void accept(HttpResult<List<Album>> albumHttpResult) throws Exception {
                        Util.showToast("accept");
                        if (isFirst) {
                            data.clear();
                        }
                        if (albumHttpResult.list != null && albumHttpResult.list.size() > 0) {
                            data.addAll(albumHttpResult.list);
                        }
                        setValue(data);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Util.showToast("throwable" + throwable.getMessage());
                    }
                });
    }

    public void cancel() {
        if (mSubscribe != null && !mSubscribe.isDisposed()) {
            mSubscribe.dispose();
        }
    }
}
