package com.test.shileiyu.jetpack.ui.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;

import butterknife.BindView;

public class SearchActivity extends BaseActivity {


    private SearchModel mModel;

    private FilterPanel mPanel;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mModel = new SearchModel();
        mPanel = new FilterPanel(mModel, getWindow().getDecorView());

        mPanel.setUpFilterBarCloseState();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }
}
