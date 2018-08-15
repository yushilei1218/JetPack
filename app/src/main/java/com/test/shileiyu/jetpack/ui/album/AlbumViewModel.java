package com.test.shileiyu.jetpack.ui.album;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.test.shileiyu.jetpack.common.bean.Album;

import java.io.Closeable;
import java.util.List;

/**
 * @author shilei.yu
 * @date 2018/8/8
 */

public class AlbumViewModel extends ViewModel {

    public AlbumViewModel(AlbumLiveData data) {
        mData = data;
    }

    public AlbumLiveData mData;

    public LiveData<List<Album>> get() {
        return mData;
    }

    @Override
    protected void onCleared() {
        mData.cancel();
    }
}
