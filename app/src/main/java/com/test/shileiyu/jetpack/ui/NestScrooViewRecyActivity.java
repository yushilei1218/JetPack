package com.test.shileiyu.jetpack.ui;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.adapter.BeanRAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.Bean;

import butterknife.BindView;

public class NestScrooViewRecyActivity extends BaseActivity {
    @BindView(R.id.recycler_nest)
    RecyclerView mView;
    @BindView(R.id.footer_tv)
    TextView mFooter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        ViewGroup.LayoutParams lp = mView.getLayoutParams();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        lp.height = dm.heightPixels;
        BeanRAdapter adapter = new BeanRAdapter();
        adapter.data = Bean.getList("分页1 ", 20);
        mView.setAdapter(adapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_nest_scroo_view_recy;
    }
}
