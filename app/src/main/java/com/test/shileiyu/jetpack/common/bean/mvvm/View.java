package com.test.shileiyu.jetpack.common.bean.mvvm;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.TextView;

import com.test.shileiyu.jetpack.common.bean.Bean;
import com.test.shileiyu.jetpack.common.bean.LoadState;

/**
 * @author shilei.yu
 * @date 2018/9/18
 */

public class View {
    TextView mTextView;
    ListView mListView;

    private ViewModel mViewModel;


    public void onCreate() {
        mViewModel = new ViewModel();
        mViewModel.mBeanData.observe(null, new Observer<Bean>() {
            @Override
            public void onChanged(@Nullable Bean bean) {
                mTextView.setText(bean.name);
            }
        });
        mViewModel.mLoadData.observe(null, new Observer<LoadState>() {
            @Override
            public void onChanged(@Nullable LoadState loadState) {
                mListView.callOnClick();
            }
        });
    }

    public void onDestory() {

    }
}
