package com.test.shileiyu.jetpack.ui;

import android.os.Bundle;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.widget.VerticalBarrageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MoveActivity extends BaseActivity {
    @BindView(R.id.barrage_view)
    VerticalBarrageView mBarrageView;


    @Override
    protected void initView(Bundle savedInstanceState) {
        List<VerticalBarrageView.Item> data = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            VerticalBarrageView.Item item = new VerticalBarrageView.Item();
            item.text = "item +++" + i;
            data.add(item);

        }
        mBarrageView.setUpView(data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_move;
    }
}
