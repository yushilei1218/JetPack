package com.test.shileiyu.jetpack.common.base;

import android.view.View;

/**
 * @author shilei.yu
 * @date 2018/8/15
 */

public interface IBaseView {
    void showDialogLoading(CharSequence msg);

    void hideDialogLoading();

    void showLoading(CharSequence msg);

    void hideLoading();

    void showError(int imgRid, CharSequence msg, View.OnClickListener onClick);

    void showError(CharSequence msg, View.OnClickListener onClick);

    void showError(CharSequence msg);

    void hideBaseLayout();

}
