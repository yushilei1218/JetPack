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

    @Override
    protected void initView(Bundle savedInstanceState) {
        BottomSheetBehavior behavior = getBottomSheetBehavior();
        behavior.setHideable(true);
        behavior.setPeekHeight(Util.dp2px(this, 50));
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private BottomSheetBehavior getBottomSheetBehavior() {
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) bottom.getLayoutParams();
        return (BottomSheetBehavior) lp.getBehavior();
    }

    @OnClick(R.id.bottom_opr)
    public void onClick(View view) {
        int state = stateArr[index % stateArr.length];
        BottomSheetBehavior behavior = getBottomSheetBehavior();
        behavior.setState(state);
        index++;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bottom_sheet_behavior;
    }
}
