package com.test.shileiyu.jetpack.common.bean.mvvm;

import com.test.shileiyu.jetpack.common.bean.Bean;

/**
 * @author shilei.yu
 * @date 2018/9/18
 */

public class Model {


    public ViewModel mViewModel;

    public Model(ViewModel model) {
    }

    public void login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bean bean = new Bean("aaa");
                mViewModel.mBeanData.postValue(bean);
            }
        }).start();
    }
}
