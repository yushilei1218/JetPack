package com.test.shileiyu.jetpack.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.adapter.BeanRAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.Bean;

import butterknife.BindView;

public class LinearSnapActivity extends BaseActivity {
    @BindView(R.id.linear_snap_recycler)
    RecyclerView mRecyclerView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        BeanRAdapter adapter = new BeanRAdapter();
        adapter.data = Bean.getList("linearSnap", 60);
        mRecyclerView.setAdapter(adapter);
        LinearSnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                outRect.set(0, 20, 0, 20);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_linear_snap;
    }
}
