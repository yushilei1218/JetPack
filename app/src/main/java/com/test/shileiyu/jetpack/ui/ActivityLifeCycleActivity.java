package com.test.shileiyu.jetpack.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
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
import com.test.shileiyu.jetpack.common.util.Util;

import java.util.Set;

import butterknife.BindView;

public class ActivityLifeCycleActivity extends BaseActivity {
    @BindView(R.id.test_tv_2)
    TextView mTv;

    private String TAG = "LifeStyle";

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
    protected void onCreate(Bundle savedInstanceState) {
        log("onCreate");
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onRestart() {
        log("onRestart");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        log("onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        log("onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        log("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        log("onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        log("onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        log("onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        log("onRestoreInstanceState");
        Set<String> strings = savedInstanceState.keySet();
        for (String key : strings) {
            Object o = savedInstanceState.get(key);
            if (key.equals("android:viewHierarchyState")) {
                Bundle o1 = (Bundle) o;
                Set<String> set = o1.keySet();
                for (String key2 : set) {
                    Object o2 = o1.get(key2);
                    if (o2 instanceof TextView.SavedState) {
                        TextView.SavedState state = (TextView.SavedState) o2;
                        log("onRestoreInstanceState key=" + key + " value=" + state.toString());
                    } else {

                        log("onRestoreInstanceState key=" + key + " value=" + o2.toString());
                    }
                }
            }


        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        log("onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
        int keyboardHidden = newConfig.keyboardHidden;
        if (keyboardHidden == Configuration.KEYBOARDHIDDEN_NO) {
            Util.showToast("KEYBOARDHIDDEN_NO");

        } else if (keyboardHidden == Configuration.KEYBOARDHIDDEN_YES) {
            Util.showToast("KEYBOARDHIDDEN_YES");
        }
    }

    private void log(String msg) {
        Log.d(TAG, msg);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test2;
    }
}
