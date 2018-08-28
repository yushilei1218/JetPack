package com.test.shileiyu.jetpack.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.AbsRBaseAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.Bean;
import com.test.shileiyu.jetpack.common.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BehaviorActivity extends BaseActivity {
    @BindView(R.id.recycler_be)
    RecyclerView mRecyclerView;
    @BindView(R.id.app_bar_be)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.header_bar)
    RelativeLayout mHeader;
    @BindView(R.id.img_bg)
    ImageView mImg;
    @BindView(R.id.xuan_fu_input)
    TextView mXuanFu;

    @Override
    protected void initView(Bundle savedInstanceState) {
        Adapter adapter = new Adapter();
        List<Bean> data = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            data.add(new Bean("item+" + i));
        }
        adapter.data = data;
        mRecyclerView.setAdapter(adapter);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int top = appBarLayout.getTop();
                if (top <= 0 && Math.abs(top) <= mImg.getHeight()) {
                    float i = Math.abs(top) / (float) mImg.getHeight();
                    mHeader.setAlpha(i);
                } else {
                    mHeader.setAlpha(1);
                }
                int min = (mHeader.getHeight() - mXuanFu.getHeight()) / 2;
                int deltaOffset;
                int xuanFuTop = mXuanFu.getTop();
                int y = xuanFuTop + verticalOffset;
                if (min > y) {
                    deltaOffset = min - xuanFuTop;
                } else {
                    deltaOffset = verticalOffset;
                }
                mXuanFu.setTranslationY(deltaOffset);

            }
        });
    }

    @OnClick(R.id.xuan_fu_input)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xuan_fu_input:
                Util.showToast("Input");
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
