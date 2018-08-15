package com.test.shileiyu.jetpack.ui.home;

import android.arch.lifecycle.ViewModel;


/**
 * @author shilei.yu
 * @date 2018/8/9
 */

public class TabViewModel extends ViewModel {

    public final TabModel mModel = new TabModel();

    public void getTab() {
        mModel.getTabs();
    }
}
