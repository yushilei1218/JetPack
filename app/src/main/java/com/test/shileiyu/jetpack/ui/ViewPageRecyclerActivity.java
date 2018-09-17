package com.test.shileiyu.jetpack.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.util.Pools;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.AbsBaseAdapter;
import com.test.shileiyu.jetpack.common.base.AbsRBaseAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.Bean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ViewPageRecyclerActivity extends BaseActivity {
    private static final String TAG = "ViewPageRecyc";
    @BindView(R.id.recycler_a)
    RecyclerView mRecyclerView;
    private AbsRBaseAdapter<BeanA> mAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = 50;
            }
        });

        mAdapter = new AbsRBaseAdapter<BeanA>() {
            @Override
            public VH<BeanA> getVH(@NonNull ViewGroup parent, int viewType) {
                View child = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager, parent, false);
                return new VH<BeanA>(child) {
                    @Override
                    public void bind(BeanA item) {
                        Log.e(TAG, "recycler bind");
                        ViewPager pager = (ViewPager) itemView.findViewById(R.id.item_pager);
                        PageAdapter adapter = new PageAdapter();
                        adapter.data = item.data;
                        pager.setAdapter(adapter);
                    }
                };
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.data = BeanA.getA(5);
                mAdapter.notifyDataSetChanged();
            }
        }, 1000);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_view_page_list_view;
    }

    public static class BeanA {
        List<List<Bean>> data;

        public static List<BeanA> getA(int size) {
            List<BeanA> data = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                BeanA e = new BeanA();
                e.data = get(5);
                data.add(e);
            }
            return data;
        }

        public static List<List<Bean>> get(int size) {
            List<List<Bean>> data = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                ArrayList<Bean> beans = new ArrayList<>();
                for (int j = 0; j < 6; j++) {
                    beans.add(new Bean("http://fdfs.xmcdn.com/group7/M03/64/C6/wKgDX1cxh4vQtrYiAAmBcWMpkhM762_mobile_small.jpg"));
                }
                data.add(beans);
            }
            return data;
        }
    }

    public class PageAdapter extends PagerAdapter {
        Pools.SimplePool<View> mPool = new Pools.SimplePool<>(4);
        List<List<Bean>> data;

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
            Log.e(TAG, "pager instantiateItem");
            List<Bean> beans = data.get(position);
            View child = mPool.acquire();
            if (child == null) {
                child = LayoutInflater.from(container.getContext()).inflate(R.layout.item_grid_view, container, false);
            }
            GridView grid = (GridView) child.findViewById(R.id.grid_view);
            BeanAdapter adapter = new BeanAdapter();
            adapter.data = beans;
            grid.setAdapter(adapter);
            container.addView(child);
            return child;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            mPool.release((View) object);
        }
    }

    public class BeanAdapter extends AbsBaseAdapter<Bean> {

        @Override
        public VH<Bean> getVH() {

            return new VH<Bean>() {
                @Override
                public View create(Bean item, ViewGroup parent) {
                    Log.e(TAG, "grid create");
                    return LayoutInflater.from(ViewPageRecyclerActivity.this).inflate(R.layout.item_grid_child, parent, false);
                }

                @Override
                public void bind(Bean item) {
                    ImageView viewById = (ImageView) itemView.findViewById(R.id.item_img);
                    Glide.with(ViewPageRecyclerActivity.this).load(item.name).into(viewById);
                }
            };
        }
    }
}
