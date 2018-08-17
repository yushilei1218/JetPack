package com.test.shileiyu.jetpack.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.widget.Area;
import com.test.shileiyu.jetpack.common.widget.ISeat;
import com.test.shileiyu.jetpack.common.widget.MatrixView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

public class MatrixActivity extends BaseActivity {
    @BindView(R.id.matrix_view)
    MatrixView mMatrixView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        Area area = new Area();
        area.areaHeight = 50;
        area.areaWidth = 50;
        Random random = new Random();

        List<ISeat> data = new ArrayList<>();
        for (int i = 0; i < 48; i++) {
            int x = 1 + i;
            for (int j = 0; j < 48; j++) {
                int y = 1 + j;
                int ran = random.nextInt(9);
                data.add(new Seat(x, y, false, ran % 3 == 0, i + "排" + j + "列"));
            }
        }

        mMatrixView.setUpView(area, data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_matrix;
    }


    public class Seat implements ISeat {
        public Seat(int x, int y, boolean isSelect, boolean isSold, String name) {
            this.x = x;
            this.y = y;
            this.isSelect = isSelect;
            this.isSold = isSold;
            this.name = name;
        }

        int x;
        int y;
        boolean isSelect;
        boolean isSold;
        String name;

        @Override
        public long getId() {
            return 0;
        }

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
        public boolean isSold() {
            return isSold;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
