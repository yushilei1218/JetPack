package com.test.shileiyu.jetpack.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;

import butterknife.BindView;

public class Test2Activity extends BaseActivity {
    @BindView(R.id.test_tv_2)
    TextView mTv;

    @Override
    protected void initView(Bundle savedInstanceState) {
        final String a = "壬戌之秋，七月既望，苏子与客泛舟游于赤壁之下。清风徐来，水波不兴。举酒属客，诵明月之诗，歌窈窕之章。少焉，月出于东山之上，徘徊于斗牛之间。白露横江，水光接天。纵一苇之所如，凌万顷之茫然。浩浩乎如冯虚御风，而不知其所止；飘飘乎如遗世独立，羽化而登仙。";
        final String b = "这是一行测试文案";
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                boolean equals = mTv.getText().toString().equals(a);
                mTv.setText(equals ? b : a);

                Layout layout = mTv.getLayout();
                StaticLayout sl = new StaticLayout(equals ? b : a, layout.getPaint(), layout.getWidth(), layout.getAlignment(), layout.getSpacingMultiplier(), layout.getSpacingAdd(), true);
                int height1 = sl.getHeight();

                int expandHeight = layout.getHeight();
                int viewHeight = mTv.getHeight();

                Log.d("Test2", "layout height=" + expandHeight + " tv height=" + viewHeight + " layout " + layout.toString() + " my layout=" + height1);
                ObjectAnimator va = ObjectAnimator.ofInt(mTv, "height", viewHeight, expandHeight);
                va.setDuration(1000);
                va.setInterpolator(new DecelerateInterpolator());
                va.start();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test2;
    }
}
