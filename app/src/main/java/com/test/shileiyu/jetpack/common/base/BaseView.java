package com.test.shileiyu.jetpack.common.base;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;

/**
 * @author shilei.yu
 * @date 2018/8/15
 */

public abstract class BaseView implements IBaseView {

    private Dialog loadDialog;
    private View mBaseLayout;
    private View mLoadLayout;
    private TextView mLoadTv;
    private View mErrorLayout;
    private TextView mErrorTv;
    private TextView mDialogTv;

    public BaseView(View baseLayout) {

        if (baseLayout != null) {
            mBaseLayout = baseLayout.findViewById(R.id.my_base_layout);
            if (mBaseLayout != null) {
                mLoadLayout = mBaseLayout.findViewById(R.id.base_loading_layout);
                mLoadTv = mBaseLayout.findViewById(R.id.base_loading_msg);
                mErrorLayout = mBaseLayout.findViewById(R.id.base_error_layout);
                mErrorTv = mBaseLayout.findViewById(R.id.base_error_msg);
            }
        }
    }

    public abstract Activity getActivity();

    private boolean isUIValid() {
        Activity activity = getActivity();
        return activity != null && !activity.isFinishing();
    }

    private boolean isBaseValid() {
        return mBaseLayout != null;
    }

    @Override
    public void showDialogLoading(CharSequence msg) {
        if (!isUIValid()) {
            return;
        }
        if (loadDialog == null) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.request_loading, null);
            mDialogTv = v.findViewById(R.id.loadTV);
            loadDialog = new Dialog(getActivity(), R.style.request_loading);
            loadDialog.setCanceledOnTouchOutside(false);
            loadDialog.setContentView(v);
        }
        mDialogTv.setText(msg);
        if (!loadDialog.isShowing()) {
            loadDialog.show();
        }
    }

    @Override
    public void hideDialogLoading() {
        if (loadDialog != null) {
            loadDialog.dismiss();
        }
    }

    @Override
    public void showLoading(CharSequence msg) {
        if (isBaseValid()) {
            mBaseLayout.setVisibility(View.VISIBLE);
            mErrorLayout.setVisibility(View.GONE);
            mLoadLayout.setVisibility(View.VISIBLE);
            mLoadTv.setText(msg);
        }
    }

    @Override
    public void hideLoading() {
        if (isBaseValid()) {
            mBaseLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(int imgRid, CharSequence msg, View.OnClickListener onClick) {
        if (isBaseValid()) {
            mBaseLayout.setVisibility(View.VISIBLE);
            mErrorLayout.setVisibility(View.VISIBLE);
            mLoadLayout.setVisibility(View.GONE);
            if (imgRid != -1) {

            }
            mErrorTv.setText(msg);
            mErrorTv.setOnClickListener(onClick);
        }
    }

    @Override
    public void showError(CharSequence msg, View.OnClickListener onClick) {
        showError(-1, msg, onClick);
    }

    @Override
    public void showError(CharSequence msg) {
        showError(msg, null);
    }

    @Override
    public void hideBaseLayout() {
        if (isBaseValid()) {
            mBaseLayout.setVisibility(View.GONE);
        }
    }
}
