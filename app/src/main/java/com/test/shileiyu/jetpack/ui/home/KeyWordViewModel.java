package com.test.shileiyu.jetpack.ui.home;

import android.arch.lifecycle.ViewModel;
import android.view.View;

import com.test.shileiyu.jetpack.common.bean.KeyWord;
import com.test.shileiyu.jetpack.common.bean.SubTab;

/**
 * @author shilei.yu
 * @date 2018/8/10
 */

public class KeyWordViewModel extends ViewModel implements View.OnClickListener {

    KeyWordModel mModel;

    public void init(SubTab tab) {
        mModel = new KeyWordModel(tab);
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag != null && tag instanceof KeyWord) {
            mModel.onKeyWordSelect((KeyWord) tag);
        }
    }
}
