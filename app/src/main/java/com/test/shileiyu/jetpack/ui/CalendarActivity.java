package com.test.shileiyu.jetpack.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.Pools;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.AbsBaseAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.TwoTuple;
import com.test.shileiyu.jetpack.common.bean.cal.Day;
import com.test.shileiyu.jetpack.common.bean.cal.Month;
import com.test.shileiyu.jetpack.common.widget.NGridView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

public class CalendarActivity extends BaseActivity {
    @BindView(R.id.pager)
    ViewPager mViewPager;

    @Override
    protected void initView(Bundle savedInstanceState) {
        Adapter adapter = new Adapter();
        TwoTuple<List<Month>, List<Day>> tuple = Month.get(new Date(), 6);
        adapter.data = tuple.first;
        mViewPager.setAdapter(adapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_calendar;
    }

    private class Adapter extends PagerAdapter {
        Pools.SimplePool<View> mPool = new Pools.SimplePool<>(4);
        List<Month> data;

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view.equals(object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = mPool.acquire();
            if (view == null) {
                view = new NGridView(container.getContext());
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                view.setLayoutParams(lp);
            }
            NGridView gd = (NGridView) view;
            gd.setNumColumns(7);
            Adapter2 adapter = new Adapter2();
            adapter.data = data.get(position).children;
            gd.setAdapter(adapter);

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            mPool.release((View) object);
        }
    }

    private class Adapter2 extends AbsBaseAdapter<Day> implements View.OnClickListener {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

        @Override
        public VH<Day> getVH() {
            return new VH<Day>() {
                @Override
                public View create(Day item, ViewGroup parent) {
                    return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
                }

                @Override
                public void bind(Day item) {
                    TextView viewById = (TextView) itemView.findViewById(R.id.item_day_tv);
                    viewById.setText(sdf.format(item.date));
                    itemView.setTag(R.integer.id_r,item);
                    itemView.setOnClickListener(Adapter2.this);
                }
            };
        }

        @Override
        public void onClick(View v) {

        }
    }
}
