package com.test.shileiyu.jetpack.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.adapter.BeanAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.Bean;
import com.test.shileiyu.jetpack.common.bean.city.Country;
import com.test.shileiyu.jetpack.common.bean.city.SimpleCallBack;
import com.test.shileiyu.jetpack.common.util.Util;
import com.test.shileiyu.jetpack.common.widget.LogLinearLayout;
import com.test.shileiyu.jetpack.common.widget.NGridView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class NumberPickerActivity extends BaseActivity {

    final Country mCountry = new Country(0, "中国");

    @BindView(R.id.number_tv)
    TextView mView;
    @BindView(R.id.n_grid)
    NGridView mGridView;
    @BindView(R.id.n_lv)
    ListView mLv;
    @BindView(R.id.log_layout)
    LogLinearLayout mLayout;
    @BindView(R.id.text_tv)
    View mTv;
    @BindView(R.id.grid_layout)
    LinearLayout girdLayout;


    private int mGridHeight;
    private int mInt;


    @Override
    protected void initView(Bundle savedInstanceState) {
        BeanAdapter adapter = new BeanAdapter();
        adapter.data = Bean.getList();
        mLv.setAdapter(adapter);
        BeanAdapter gAdapter = new BeanAdapter();
        gAdapter.data = new ArrayList<Bean>(Bean.getList().subList(0, 9));
        mGridView.setAdapter(gAdapter);
        mLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mLayout.getWidth();
                int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
                girdLayout.measure(widthMeasureSpec
                        , View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST));
                mGridHeight = girdLayout.getMeasuredHeight();
                Util.showToast("Grid height=" + mGridHeight);

                mTv.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(Util.dp2px(getBaseContext(), 100), View.MeasureSpec.EXACTLY));
                mInt = mTv.getMeasuredHeight();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_number_picker;
    }

    @OnClick({R.id.number_tv, R.id.show_Gird, R.id.show_TV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.number_tv:
                CityPickerFragment.instance(mCountry, new SimpleCallBack<Boolean>() {
                    @Override
                    public void onCall(Boolean data) {
                        mView.setText(mCountry.toast());
                    }
                }).show(getSupportFragmentManager(), "select");
                break;
            case R.id.show_Gird:
                int visibility = girdLayout.getVisibility();
                if (visibility == View.GONE) {
                    openGridWithAni(girdLayout, mGridHeight);
                } else {
                    closeGridWithAni(girdLayout, mGridHeight);
                }
                break;
            case R.id.show_TV:
                int v1 = mTv.getVisibility();
                if (v1 == View.GONE) {
                    openGridWithAni(mTv, mInt);
                } else {
                    closeGridWithAni(mTv, mInt);
                }
                break;
            default:
                break;
        }
    }

    private void openGridWithAni(final View view, int height) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator va = ValueAnimator.ofInt(0, height);
        va.setInterpolator(new DecelerateInterpolator());
        va.setDuration(200);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                ViewGroup.LayoutParams lp = view.getLayoutParams();
                lp.height = value;
                view.setLayoutParams(lp);
            }
        });
        va.start();
    }

    private void closeGridWithAni(final View view, int height) {
        ValueAnimator va = ValueAnimator.ofInt(height, 0);
        va.setInterpolator(new DecelerateInterpolator());
        va.setDuration(200);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                ViewGroup.LayoutParams lp = view.getLayoutParams();
                lp.height = value;
                view.setLayoutParams(lp);
            }
        });
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        va.start();
    }
}
