package com.test.shileiyu.jetpack.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.AbsBaseAdapter;
import com.test.shileiyu.jetpack.common.base.AbsRBaseAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.Bean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class DragActivity extends BaseActivity {
    @BindView(R.id.recycler)
    RecyclerView mLv;

    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;

    @Override
    protected void initView(Bundle savedInstanceState) {

        mAppBar.setExpanded(false);
        Adapter adapter = new Adapter();
        List<Bean> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add(new Bean("item+" + i));
        }
        adapter.data = data;
        mLv.setAdapter(adapter);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_drag;
    }

    private class Adapter extends AbsRBaseAdapter<Bean> {


        @Override
        public VH<Bean> getVH(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bean, parent, false);
            return new VH<Bean>(inflate) {
                @Override
                public void bind(Bean item) {
                    TextView tv = (TextView) itemView.findViewById(R.id.item_tv);
                    tv.setText(item.name);
                }
            };
        }
    }
}
