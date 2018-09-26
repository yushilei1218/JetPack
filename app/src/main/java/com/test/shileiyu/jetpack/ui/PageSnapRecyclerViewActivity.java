package com.test.shileiyu.jetpack.ui;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.adapter.BeanRAdapter;
import com.test.shileiyu.jetpack.common.base.AbsRBaseAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.Bean;
import com.test.shileiyu.jetpack.common.util.Util;

import butterknife.BindView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

public class PageSnapRecyclerViewActivity extends BaseActivity {
    @BindView(R.id.page_snap_recycler)
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void initView(Bundle savedInstanceState) {
        final PagerSnapHelper helper = new PagerSnapHelper();
        mLayoutManager = mRecyclerView.getLayoutManager();

        helper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case SCROLL_STATE_IDLE:
                        View snapView = helper.findSnapView(mLayoutManager);
                        if (snapView != null) {
                            Object tag = snapView.getTag(R.integer.id_r);
                            if (tag != null) {
                                Util.showToast(tag.toString());
                            }
                        }
                        break;
                    case SCROLL_STATE_SETTLING:
                        break;
                    case SCROLL_STATE_DRAGGING:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        Adapter adapter = new Adapter();
        adapter.data = Bean.getList("page snap", 20);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDrawOver(Canvas c, RecyclerView parent) {
                super.onDrawOver(c, parent);
            }

            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                outRect.set(0, 100, 0, 100);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_page_snap_recycler_view;
    }

    public static class Adapter extends AbsRBaseAdapter<Bean> {

        @Override
        public VH<Bean> getVH(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_snap, parent, false);
            return new VH<Bean>(view) {
                @Override
                public void bind(Bean item) {
                    itemView.setTag(R.integer.id_r, item.name);
                }
            };
        }
    }
}
