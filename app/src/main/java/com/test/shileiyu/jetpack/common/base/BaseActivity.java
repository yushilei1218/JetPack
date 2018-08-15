package com.test.shileiyu.jetpack.common.base;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.test.shileiyu.jetpack.R;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    private BaseView mBaseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        View baseLayout = findViewById(R.id.my_base_layout);

        mBaseView = new BaseView(baseLayout) {
            @Override
            public Activity getActivity() {
                return BaseActivity.this;
            }
        };

        ButterKnife.bind(this);

        initView(savedInstanceState);
    }

    protected abstract void initView(Bundle savedInstanceState);

    public abstract int getLayoutId();

    @Override
    public void showDialogLoading(CharSequence msg) {
        mBaseView.showDialogLoading(msg);
    }

    @Override
    public void hideDialogLoading() {
        mBaseView.hideDialogLoading();
    }

    @Override
    public void showLoading(CharSequence msg) {
        mBaseView.showLoading(msg);
    }

    @Override
    public void hideLoading() {
        mBaseView.hideLoading();
    }

    @Override
    public void showError(int imgRid, CharSequence msg, View.OnClickListener onClick) {
        mBaseView.showError(imgRid, msg, onClick);
    }

    @Override
    public void showError(CharSequence msg, View.OnClickListener onClick) {
        mBaseView.showError(msg, onClick);
    }

    @Override
    public void showError(CharSequence msg) {
        mBaseView.showError(msg);
    }

    @Override
    public void hideBaseLayout() {
        mBaseView.hideBaseLayout();
    }

    public String getTAG() {
        return this.getClass().getSimpleName();
    }
}
