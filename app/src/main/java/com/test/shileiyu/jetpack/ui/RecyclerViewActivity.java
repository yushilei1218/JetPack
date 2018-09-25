package com.test.shileiyu.jetpack.ui;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mcxtzhang.layoutmanager.swipecard.CardConfig;
import com.mcxtzhang.layoutmanager.swipecard.OverLayCardLayoutManager;
import com.mcxtzhang.layoutmanager.swipecard.RenRenCallback;
import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.AbsRBaseAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.util.Util;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;

public class RecyclerViewActivity extends BaseActivity {
    @BindView(R.id.my_recycler_2)
    RecyclerView recyclerView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new OverLayCardLayoutManager());
        CardConfig.initConfig(this);
        AbsRBaseAdapter<Item> ada = new AbsRBaseAdapter<Item>() {

            @Override
            public VH<Item> getVH(@NonNull ViewGroup parent, int viewType) {
                View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
                return new VH<Item>(inflate) {
                    @Override
                    public void bind(Item item) {

                    }
                };
            }
        };
        ArrayList<Item> data = new ArrayList<>();
        data.add(new Item());
        data.add(new Item());
        data.add(new Item());
        data.add(new Item());
        data.add(new Item());
        data.add(new Item());
        data.add(new Item());
        data.add(new Item());
        data.add(new Item());
        data.add(new Item());
        ada.data= data;
        ItemTouchHelper.Callback callback = new RenRenCallback(recyclerView, ada, data);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(ada);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_recycler_view;
    }

    public static class Item {

    }
}
