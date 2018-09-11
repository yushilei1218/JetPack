package com.test.shileiyu.jetpack.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.city.Country;

import butterknife.OnClick;

public class NumberPickerActivity extends BaseActivity {

    final Country mCountry = new Country(0, "中国");

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_number_picker;
    }

    @OnClick({R.id.number_tv})
    public void onClick(View view) {
        CityPickerFragment.instance(mCountry).show(getSupportFragmentManager(), "select");
    }
}
