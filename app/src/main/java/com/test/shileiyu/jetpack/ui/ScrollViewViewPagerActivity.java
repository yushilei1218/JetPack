package com.test.shileiyu.jetpack.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.widget.InterceptScrollView;

import butterknife.BindView;

/**
 * @author shilei.yu
 */
public class ScrollViewViewPagerActivity extends BaseActivity {
    @BindView(R.id.my_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.intercept_scroll_view)
    InterceptScrollView mScrollView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mViewPager.setAdapter(new Adapter());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_scroll_view_view_pager;
    }

    public class Adapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view.equals(object);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View child = LayoutInflater.from(container.getContext()).inflate(R.layout.view_pager_item, container, false);
            ImageView img = (ImageView) child.findViewById(R.id.my_img);
            TextView tv = child.findViewById(R.id.my_tv);
            img.setBackgroundResource(R.mipmap.bg_3);
            String text = "" + position;
            tv.setText(text);
            container.addView(child);
            return child;
        }
    }
}
