package com.test.shileiyu.jetpack.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.Bean;
import com.test.shileiyu.jetpack.common.widget.RAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyBehaviorActivity extends BaseActivity {
    @BindView(R.id.my_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.header_v1)
    View mView;
    @BindView(R.id.header_v0)
    View mView0;

    @Override
    protected void initView(Bundle savedInstanceState) {
        RAdapter adapter = new RAdapter();
        List<Bean> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(new Bean("item +" + i));
        }
        adapter.data = data;
        mRecyclerView.setAdapter(adapter);
        mView0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.setVisibility(mView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_behavior;
    }
}
