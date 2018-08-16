package com.test.shileiyu.jetpack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.test.shileiyu.jetpack.ui.AreaViewActivity;
import com.test.shileiyu.jetpack.ui.SeatActivity;
import com.test.shileiyu.jetpack.ui.home.TabActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.main_2_tab, R.id.main_3_tab, R.id.main_4_tab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_2_tab:
                startActivity(new Intent(this, TabActivity.class));
                break;
            case R.id.main_3_tab:
                startActivity(new Intent(this, AreaViewActivity.class));
                break;
            case R.id.main_4_tab:
                startActivity(new Intent(this, SeatActivity.class));
                break;
            default:
                break;
        }

    }
}
