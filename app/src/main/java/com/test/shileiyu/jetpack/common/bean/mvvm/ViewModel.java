package com.test.shileiyu.jetpack.common.bean.mvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.test.shileiyu.jetpack.common.bean.Bean;
import com.test.shileiyu.jetpack.common.bean.LoadState;

/**
 * @author shilei.yu
 * @date 2018/9/18
 */

public class ViewModel {
    public MutableLiveData<Bean> mBeanData = new MutableLiveData<Bean>();
    public MutableLiveData<LoadState> mLoadData = new MutableLiveData<LoadState>();
    private Model mModel;
    public ViewModel() {
        mModel=new Model(this);
    }
}
