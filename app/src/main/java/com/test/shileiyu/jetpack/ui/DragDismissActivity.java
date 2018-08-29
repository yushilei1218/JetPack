package com.test.shileiyu.jetpack.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.Pools;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.widget.DragDismissLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DragDismissActivity extends BaseActivity {

    private static final String EXTRA_INDEX = "EXTRA_INDEX";
    private static final String EXTRA = "EXTRA";

    private ArrayList<String> mString;
    private int mStartIndex;

    @BindView(R.id.pager_drag)
    ViewPager mPager;

    public static void invoke(Activity a, int startIndex, List<String> data) {
        Intent intent = new Intent(a, DragDismissActivity.class);
        intent.putStringArrayListExtra(EXTRA, new ArrayList<>(data));
        intent.putExtra(EXTRA_INDEX, startIndex);
        a.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mString = getIntent().getStringArrayListExtra(EXTRA);
        mStartIndex = getIntent().getIntExtra(EXTRA_INDEX, 0);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Adapter adapter = new Adapter();
        adapter.data = mString;
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(mStartIndex);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_drag_dismiss;
    }

    private class Adapter extends PagerAdapter {
        Pools.SimplePool<View> mPool = new Pools.SimplePool<>(4);
        List<String> data;

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
            View acquire = mPool.acquire();
            if (acquire == null) {
                acquire = LayoutInflater.from(container.getContext()).inflate(R.layout.item_drag, container, false);
            }
            PhotoView view = (PhotoView) acquire.findViewById(R.id.item_drag_photo);
            RequestOptions ro = RequestOptions.centerInsideTransform();

            Glide.with(container.getContext()).load(data.get(position)).apply(ro).into(view);

            container.addView(acquire);
            if (acquire instanceof DragDismissLayout) {
                ((DragDismissLayout) acquire).setCallBack(new DragDismissLayout.DragDismissCallBack() {
                    @Override
                    public boolean isCanDrag() {
                        return true;
                    }

                    @Override
                    public void dismiss() {
                        finish();
                        overridePendingTransition(0,0);
                    }
                });
            }
            return acquire;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            mPool.release((View) object);
        }
    }
}
