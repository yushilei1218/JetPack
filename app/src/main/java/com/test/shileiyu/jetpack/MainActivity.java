package com.test.shileiyu.jetpack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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

    @OnClick(R.id.main_2_tab)
    public void onViewClicked() {
        startActivity(new Intent(this, TabActivity.class));
    }
}
