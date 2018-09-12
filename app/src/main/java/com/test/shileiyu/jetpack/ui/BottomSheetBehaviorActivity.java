package com.test.shileiyu.jetpack.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.util.Util;

import butterknife.BindView;
import butterknife.OnClick;

import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;
import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;
import static android.support.design.widget.BottomSheetBehavior.STATE_HIDDEN;

public class BottomSheetBehaviorActivity extends BaseActivity {
    @BindView(R.id.bottom_sheet_layout)
    View bottom;

    private int[] stateArr = new int[]{
            STATE_COLLAPSED,
            STATE_EXPANDED,
            STATE_HIDDEN
    };
    private int index = 0;
    private BottomSheetBehavior<View> mBehavior;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mBehavior = BottomSheetBehavior.from(bottom);
        mBehavior.setHideable(true);
        mBehavior.setPeekHeight(Util.dp2px(this, 50));
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @OnClick(R.id.bottom_opr)
    public void onClick(View view) {
        int state = stateArr[index % stateArr.length];
        mBehavior.setState(state);
        index++;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bottom_sheet_behavior;
    }
}
