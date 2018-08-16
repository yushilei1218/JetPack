package com.test.shileiyu.jetpack.ui;

import android.os.Bundle;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.widget.AreaView;
import com.test.shileiyu.jetpack.common.widget.ISeat;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AreaViewActivity extends BaseActivity {

    @BindView(R.id.area_view)
    AreaView mAreaView;

    @Override
    protected void initView(Bundle savedInstanceState) {

        List<ISeat> data = new ArrayList<>(50);
        int x = 20;
        int y = 20;

        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 10; column++) {
                Seat e = new Seat();
                e.x = x + column * 36;
                e.y = y + row * 30;
                e.name = "行 " + row + " 列 " + column;
                data.add(e);
            }
        }
        mAreaView.setUpAreaView(400, 200, data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_area_view;
    }

    public class Seat implements ISeat {
        public int x;
        public int y;
        public String name;
        public boolean isSelect;

        @Override
        public int getXInArea() {
            return x;
        }

        @Override
        public int getYInArea() {
            return y;
        }

        @Override
        public boolean isSelect() {
            return isSelect;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
