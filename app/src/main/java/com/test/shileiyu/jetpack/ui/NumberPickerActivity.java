package com.test.shileiyu.jetpack.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.city.Country;
import com.test.shileiyu.jetpack.common.bean.city.SimpleCallBack;

import butterknife.BindView;
import butterknife.OnClick;

public class NumberPickerActivity extends BaseActivity {

    final Country mCountry = new Country(0, "中国");

    @BindView(R.id.number_tv)
    TextView mView;

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_number_picker;
    }

    @OnClick({R.id.number_tv})
    public void onClick(View view) {
        CityPickerFragment.instance(mCountry, new SimpleCallBack<Boolean>() {
            @Override
            public void onCall(Boolean data) {
                mView.setText(mCountry.toast());
            }
        }).show(getSupportFragmentManager(), "select");
    }
}
