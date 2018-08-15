package com.test.shileiyu.jetpack.ui.home;


import android.arch.lifecycle.ViewModel;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;


import com.test.shileiyu.jetpack.common.base.IBaseView;
import com.test.shileiyu.jetpack.common.bean.KeyWord;
import com.test.shileiyu.jetpack.common.bean.SubTab;
import com.test.shileiyu.jetpack.common.util.Util;

import java.util.List;

/**
 * @author shilei.yu
 * @date 2018/8/9
 */

public class CategoryViewModel extends ViewModel implements KeyWordFragment.OnKeyWordChangeListener, SwipeRefreshLayout.OnRefreshListener {
    public CategoryModel dataModel;
    private IBaseView mBaseView;

    public void init(IBaseView m, SubTab tab) {
        mBaseView = m;
        dataModel = new CategoryModel(tab);
    }


    @Override
    public void onChange(List<KeyWord> tabs) {
        mBaseView.showDialogLoading("2");
        dataModel.setKeyWord(tabs);
        dataModel.load(true);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onFail() {

    }

    @Override
    public void onRefresh() {
        Util.showToast("refresh");
        dataModel.load(true);
    }
}
