package com.test.shileiyu.jetpack.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.adapter.BeanRAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.Bean;
import com.test.shileiyu.jetpack.common.widget.ItemTouchCallBack;

import butterknife.BindView;

public class ItemTouchHelperActivity extends BaseActivity {
    @BindView(R.id.touch_helper_recycler)
    RecyclerView mRecyclerView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        BeanRAdapter adapter = new BeanRAdapter();
        adapter.data = Bean.getList("custom touch helper", 60);
        mRecyclerView.setAdapter(adapter);
        int dragDirs = ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        int swipeDirs = ItemTouchHelper.LEFT;
        ItemTouchCallBack callBack = new ItemTouchCallBack(dragDirs, swipeDirs, adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callBack);
        helper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_item_touch_helper;
    }
}
