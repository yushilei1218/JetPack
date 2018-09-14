package com.test.shileiyu.jetpack.ui;

import android.animation.ValueAnimator;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.AbsRBaseAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.Bean;
import com.test.shileiyu.jetpack.common.util.Util;
import com.test.shileiyu.jetpack.common.widget.DragCoordinatorLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BehaviorActivity extends BaseActivity {
    private static final String TAG = "Behavior";
    @BindView(R.id.recycler_be)
    RecyclerView mRecyclerView;
    @BindView(R.id.app_bar_be)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.header_bar)
    RelativeLayout mHeader;
    @BindView(R.id.img_bg)
    ImageView mImg;
    @BindView(R.id.xuan_fu_input)
    View mXuanFu;
    @BindView(R.id.drag_coor)
    DragCoordinatorLayout mDragLayout;
    private int mImgHeight;
    private int mXuanFuTop;

    @BindView(R.id.error_layout)
    NestedScrollView errorLayout;
    @BindView(R.id.float_btn)
    FloatingActionButton fbtn;
    @BindView(R.id.error_content)
    View errorContent;
    private Adapter mAdapter;
    @BindView(R.id.root)
    ViewGroup mRoot;
    private int mRootHeight;
    @BindView(R.id.app_bar_content)
    ViewGroup mAppBarContent;

    @Override
    protected void initView(Bundle savedInstanceState) {

        //3、获取屏幕的默认分辨率
        Display display = getWindowManager().getDefaultDisplay();

        Point outSize = new Point();
        display.getSize(outSize);
        int height = outSize.y;
        errorContent.getLayoutParams().height = height;


        mAdapter = new Adapter();
        mAdapter.data = Bean.getList();
        mRecyclerView.setAdapter(mAdapter);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d(TAG, "onOffsetChanged verticalOffset= " + verticalOffset);
                int top = appBarLayout.getTop();
                if (top <= 0 && Math.abs(top) <= mImg.getHeight()) {
                    float i = Math.abs(top) / (float) mImg.getHeight();
                    mHeader.setAlpha(i);
                } else {
                    mHeader.setAlpha(1);
                }
                int min = (mHeader.getHeight() - mXuanFu.getHeight()) / 2;
                int distance = mXuanFu.getTop() - min;
                if (distance == 0) {
                    return;
                }

                int tx;
                int ty;
                float sx = 1.0f;
                int xuanFuTop = mXuanFu.getTop();
                int y = xuanFuTop + verticalOffset;
                if (min > y) {
                    ty = min - xuanFuTop;
                } else {
                    ty = verticalOffset;
                }
                float rate = (float) Math.abs(ty) / distance;
                tx = (int) (rate * Util.dp2px(getApplicationContext(), 40));
                sx = 1.0f - rate * 0.4f;
                int maxDeltaRight = Util.dp2px(getApplicationContext(), 100);
                mXuanFu.setTranslationY(ty);
                mXuanFu.setTranslationX(tx);
                ViewGroup.LayoutParams lp = mXuanFu.getLayoutParams();
                lp.width = (int) (600 + (1 - rate) * maxDeltaRight);
                mXuanFu.setLayoutParams(lp);
//                int right= (int) (600+(1-rate)*maxDeltaRight);
//                mXuanFu.setRight(right);


            }
        });
        mImg.post(new Runnable() {
            @Override
            public void run() {
                mImgHeight = mImg.getHeight();
            }
        });

        mXuanFu.post(new Runnable() {
            @Override
            public void run() {
                mXuanFuTop = mXuanFu.getTop();

            }
        });

        mDragLayout.setDragListener(new DragCoordinatorLayout.OnDragListener() {
            @Override
            public boolean isCanDragDown() {
                return mAppBarLayout.getTop() >= 0;
            }

            @Override
            public void onDrag(float distanceX, float distanceY) {
                if (distanceY <= 0) {
                    return;
                }
                Log.d("onDrag", "onDrag distanceY= " + distanceY);
                ViewGroup.LayoutParams lp = mImg.getLayoutParams();
                lp.height = (int) (mImgHeight + distanceY);
                mImg.setPivotX(mImg.getWidth() / 2f);
                float scale = 1 + distanceY / 1000f;
                mImg.setScaleX(scale);
                mImg.setLayoutParams(lp);

            }

            @Override
            public void onRelease(float distanceX, final float distanceY) {
                if (distanceY < 0) {
                    mImg.setScaleX(1f);
                    ViewGroup.LayoutParams lp = mImg.getLayoutParams();
                    lp.height = mImgHeight;
                    mImg.setLayoutParams(lp);
                    mXuanFu.setTranslationY(distanceY);
                    return;
                }
                ValueAnimator av = ValueAnimator.ofFloat(1f, 0f);
                av.setInterpolator(new DecelerateInterpolator());
                av.setDuration(200);
                av.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Float animatedValue = (Float) animation.getAnimatedValue();
                        float scale = 1 + distanceY * animatedValue / 1000f;
                        mImg.setPivotX(mImg.getWidth() / 2f);
                        mImg.setScaleX(scale);
                        ViewGroup.LayoutParams lp = mImg.getLayoutParams();
                        lp.height = (int) (mImgHeight + animatedValue * distanceY);
                        mImg.setLayoutParams(lp);
                        mXuanFu.setTranslationY(distanceY * animatedValue);
                    }
                });
                av.start();
                Log.d("onDrag", "onRelease distanceY= " + distanceY);
            }
        });
    }

    boolean isAdd = true;

    @OnClick({
            R.id.xuan_fu_input,
            R.id.filter,
            R.id.float_btn2,
            R.id.float_btn
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xuan_fu_input:
                Util.showToast("Input");
                break;
            case R.id.float_btn2:
                ViewGroup.LayoutParams lp = mAppBarContent.getLayoutParams();
                int height = Util.dp2px(this, 500);
                if (isAdd) {
                    lp.height += height;
                } else {
                    lp.height -= height;
                }
                isAdd = !isAdd;
                mAppBarContent.setLayoutParams(lp);
                break;
            case R.id.float_btn:
                if (Util.isEmpty(mAdapter.data)) {
                    mAdapter.data = Bean.getList();
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mAdapter.notifyDataSetChanged();
                    errorLayout.setVisibility(View.GONE);
                } else {
                    mAdapter.data = null;
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setVisibility(View.GONE);
                    errorLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.filter:
                mRecyclerView.stopScroll();
                mAppBarLayout.setExpanded(false, true);
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_behavior;
    }

    private class Adapter extends AbsRBaseAdapter<Bean> {

        @Override
        public VH<Bean> getVH(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bean, parent, false);

            return new VH<Bean>(itemView) {
                @Override
                public void bind(Bean item) {
                    TextView tv = (TextView) itemView.findViewById(R.id.item_tv);
                    tv.setText(item.name);
                }
            };
        }
    }
}
