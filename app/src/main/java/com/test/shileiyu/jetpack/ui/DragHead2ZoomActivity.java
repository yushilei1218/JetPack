package com.test.shileiyu.jetpack.ui;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.adapter.BeanAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.Bean;
import com.test.shileiyu.jetpack.common.widget.DragCoordinatorLayout;

import java.util.List;

import butterknife.BindView;

public class DragHead2ZoomActivity extends BaseActivity {

    @BindView(R.id.drag_coor2)
    DragCoordinatorLayout mLayout;
    @BindView(R.id.drag_lv)
    ListView mListView;
    private View mHeaderImg;
    private int mImgHeight;

    @Override
    protected void initView(Bundle savedInstanceState) {
        final BeanAdapter adapter = new BeanAdapter();
        adapter.data = Bean.getList();
        View header = LayoutInflater.from(this).inflate(R.layout.header, mListView, false);
        mHeaderImg = header.findViewById(R.id.header_img);
        mListView.addHeaderView(header);
        mListView.setAdapter(adapter);
        mLayout.setDragListener(new DragCoordinatorLayout.OnDragListener() {
            @Override
            public boolean isCanDragDown() {
                return !mListView.canScrollVertically(-1);
            }

            @Override
            public void onDrag(float distanceX, float distanceY) {
                if (distanceY <= 0) {
                    return;
                }
                mHeaderImg.setPivotX(mHeaderImg.getWidth() / 2f);
                mHeaderImg.setScaleX(1 + distanceY / 1000f);
                ViewGroup.LayoutParams lp = mHeaderImg.getLayoutParams();
                lp.height = (int) (mImgHeight + distanceY);
                mHeaderImg.setLayoutParams(lp);
            }

            @Override
            public void onRelease(float distanceX, final float distanceY) {
                if (distanceY <= 0) {
                    mHeaderImg.setScaleX(1f);
                    ViewGroup.LayoutParams lp = mHeaderImg.getLayoutParams();
                    lp.height = mImgHeight;
                    mHeaderImg.setLayoutParams(lp);
                    return;
                }
                ValueAnimator va = ValueAnimator.ofFloat(1f, 0f);
                va.setDuration(200);
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Float value = (Float) animation.getAnimatedValue();
                        mHeaderImg.setScaleX(1 + value * distanceY / 1000f);
                        ViewGroup.LayoutParams lp = mHeaderImg.getLayoutParams();
                        lp.height= (int) (mImgHeight+distanceY*value);
                        mHeaderImg.setLayoutParams(lp);
                    }
                });
                va.setInterpolator(new DecelerateInterpolator());
                va.start();

            }
        });
        mHeaderImg.post(new Runnable() {
            @Override
            public void run() {
                mImgHeight = mHeaderImg.getHeight();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_drag_head2_zoom;
    }
}
