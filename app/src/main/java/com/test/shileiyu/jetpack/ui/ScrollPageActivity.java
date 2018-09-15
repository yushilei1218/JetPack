package com.test.shileiyu.jetpack.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.adapter.BeanRAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.Bean;
import com.test.shileiyu.jetpack.common.widget.PageLinearLayout;

import butterknife.BindView;

public class ScrollPageActivity extends BaseActivity {
    @BindView(R.id.page_layout)
    PageLinearLayout linearLayout;
    @BindView(R.id.recycler_1)
    RecyclerView mRecycler;

    @Override
    protected void initView(Bundle savedInstanceState) {
        BeanRAdapter adapter = new BeanRAdapter();
        adapter.data = Bean.getList();
        mRecycler.setAdapter(adapter);

        linearLayout.setCallback(new PageLinearLayout.DragCallback() {
            @Override
            public boolean isIntercept(int page, int axis) {
                if (page == 1 && axis < 0) {
                    //page=1 且向上滑动

                    return !mRecycler.canScrollVertically(1);
                } else if (page == 2 && axis > 0) {
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_scroll_page;
    }
}
