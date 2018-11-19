package com.test.shileiyu.jetpack.ui;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;

import butterknife.BindView;

public class NestedScrollActivity extends BaseActivity {
    @BindView(R.id.nested_parent)
    NestedScrollView mParent;
    @BindView(R.id.nested_child)
    NestedScrollView mChild;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mParent.setNestedScrollingEnabled(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_nested_scroll;
    }
}
