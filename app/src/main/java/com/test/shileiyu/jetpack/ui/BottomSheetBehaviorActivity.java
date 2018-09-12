package com.test.shileiyu.jetpack.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.widget.ListView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.adapter.BeanAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.Bean;
import com.test.shileiyu.jetpack.common.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;
import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;
import static android.support.design.widget.BottomSheetBehavior.STATE_HIDDEN;
import static android.support.design.widget.SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END;

/**
 * @author shilei.yu
 */
public class BottomSheetBehaviorActivity extends BaseActivity {
    @BindView(R.id.bottom_sheet_layout)
    View bottom;
    @BindView(R.id.swipe_dismiss)
    View swipe;
    @BindView(R.id.lv_slide)
    ListView slideLv;
    @BindView(R.id.slide_pane)
    SlidingPaneLayout slideLayout;

    private int[] stateArr = new int[]{
            STATE_COLLAPSED,
            STATE_EXPANDED,
            STATE_HIDDEN
    };
    private int index = 0;
    private BottomSheetBehavior<View> mBehavior;

    @Override
    protected void initView(Bundle savedInstanceState) {
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) swipe.getLayoutParams();
        SwipeDismissBehavior swipeDismissBehavior = new SwipeDismissBehavior();
        swipeDismissBehavior.setSwipeDirection(SWIPE_DIRECTION_START_TO_END);
        swipeDismissBehavior.setListener(new SwipeDismissBehavior.OnDismissListener() {
            @Override
            public void onDismiss(View view) {
                Util.showToast("onDismiss");
            }

            @Override
            public void onDragStateChanged(int state) {

            }
        });
        lp.setBehavior(swipeDismissBehavior);
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

        BeanAdapter adapter = new BeanAdapter();
        adapter.data = Bean.getList();
        slideLv.setAdapter(adapter);

        slideLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(@NonNull View panel, float slideOffset) {
//                int width = slideLv.getWidth();
//                slideLv.setTranslationX(slideOffset * width - width);
            }

            @Override
            public void onPanelOpened(@NonNull View panel) {
                Util.showToast("onPanelOpened");
            }

            @Override
            public void onPanelClosed(@NonNull View panel) {
                Util.showToast("onPanelClosed");
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
