package com.test.shileiyu.jetpack.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.adapter.BeanRAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.Bean;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS;
import static android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED;
import static android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED;
import static android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL;
import static android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP;

public class AppBarLayoutActivity extends BaseActivity {
    @BindView(R.id.app_bar_recy)
    RecyclerView mRecyclerView;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appbar;
    @BindView(R.id.app_bar_child)
    View child;
    private int[] tags = new int[]{
            SCROLL_FLAG_ENTER_ALWAYS,
            SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED,
            SCROLL_FLAG_EXIT_UNTIL_COLLAPSED,
            SCROLL_FLAG_SCROLL,
            SCROLL_FLAG_SCROLL | SCROLL_FLAG_ENTER_ALWAYS,
            SCROLL_FLAG_SCROLL | SCROLL_FLAG_SNAP,
            SCROLL_FLAG_EXIT_UNTIL_COLLAPSED | SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED,
    };
    int index = 0;
    private BeanRAdapter mAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mAdapter = new BeanRAdapter();
        mAdapter.data = Bean.getList(getAppBarFlagType());
        mRecyclerView.setAdapter(mAdapter);


        getAppBarFlagType();
    }

    private String getAppBarFlagType() {
        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) child.getLayoutParams();
        int scrollFlags = layoutParams.getScrollFlags();
        String msg = "";
        switch (scrollFlags) {
            case SCROLL_FLAG_ENTER_ALWAYS:
                msg = "SCROLL_FLAG_ENTER_ALWAYS";
                break;
            case SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED:
                msg = "SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED";
                break;
            case SCROLL_FLAG_EXIT_UNTIL_COLLAPSED:
                msg = "SCROLL_FLAG_EXIT_UNTIL_COLLAPSED";
                break;
            case SCROLL_FLAG_SCROLL:
                msg = "SCROLL_FLAG_SCROLL";
                break;
            case SCROLL_FLAG_SNAP:
                msg = "SCROLL_FLAG_SNAP";
                break;
            case SCROLL_FLAG_SCROLL | SCROLL_FLAG_ENTER_ALWAYS:
                msg = "SCROLL_FLAG_SCROLL | SCROLL_FLAG_ENTER_ALWAYS";
                break;
            case SCROLL_FLAG_SCROLL | SCROLL_FLAG_SNAP:
                msg = "SCROLL_FLAG_SCROLL | SCROLL_FLAG_SNAP";
                break;
            case SCROLL_FLAG_EXIT_UNTIL_COLLAPSED | SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED:
                msg = "SCROLL_FLAG_EXIT_UNTIL_COLLAPSED | SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED";
                break;
            default:
                break;
        }
        return "AppBar Flag=" + msg;

    }

    @OnClick(R.id.qiehuan)
    public void onClick(View view) {
        AppBarLayout.LayoutParams lp = (AppBarLayout.LayoutParams) child.getLayoutParams();
        lp.setScrollFlags(tags[index % tags.length]);
        index++;
        child.setLayoutParams(lp);

        mAdapter.data = Bean.getList(getAppBarFlagType());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_bar_layout;
    }
}
