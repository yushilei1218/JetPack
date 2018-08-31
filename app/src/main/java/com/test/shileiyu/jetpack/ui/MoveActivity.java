package com.test.shileiyu.jetpack.ui;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.widget.VerticalBarrageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.view.animation.Animation.RESTART;

public class MoveActivity extends BaseActivity {
    @BindView(R.id.barrage_view)
    VerticalBarrageView mBarrageView;
    @BindView(R.id.img_22)
    ImageView img;
    @BindView(R.id.img1)
    ImageView img1;


    @Override
    protected void initView(Bundle savedInstanceState) {
        List<VerticalBarrageView.Item> data = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            VerticalBarrageView.Item item = new VerticalBarrageView.Item();
            item.text = "item +++" + i;
            data.add(item);

        }
        mBarrageView.setUpView(data);

        RequestOptions ro = RequestOptions.circleCropTransform();
        Glide.with(this).load(R.mipmap.big_pic).apply(ro).into(img);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(RESTART);
        animation.setDuration(10000);
        animation.setInterpolator(new LinearInterpolator());
        img.post(new Runnable() {
            @Override
            public void run() {
                img.startAnimation(animation);
            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation ani = AnimationUtils.loadAnimation(getApplication(), R.anim.set);
                ani.setInterpolator(new LinearInterpolator());
                img1.startAnimation(ani);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_move;
    }
}
