package com.test.shileiyu.jetpack.ui;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
    private MediaPlayer mediaPlayer;

    @Override
    protected void initView(Bundle savedInstanceState) {

        mediaPlayer = new MediaPlayer();
        AbsRBaseAdapter<Item> adapter = new AbsRBaseAdapter<Item>() {

            @Override
            public VH<Item> getVH(@NonNull ViewGroup parent, int viewType) {
                View child = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mp4_view, parent, false);
                child.getLayoutParams().height = getResources().getDisplayMetrics().heightPixels;
                return new VH<Item>(child) {
                    @Override
                    public void bind(Item item) {
                        final SurfaceView surfaceView = (SurfaceView) itemView.findViewById(R.id.surface);
                        ImageView img = (ImageView) itemView.findViewById(R.id.img);

                        SurfaceHolder holder = surfaceView.getHolder();
                        holder.addCallback(new SurfaceHolder.Callback() {
                            @Override
                            public void surfaceCreated(SurfaceHolder holder) {

                            }

                            @Override
                            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                            }

                            @Override
                            public void surfaceDestroyed(SurfaceHolder holder) {

                            }
                        });
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                play(surfaceView);
                            }
                        });
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
        adapter.data = data;
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                outRect.set(0, 40, 0, 40);
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }
        });
    }

    private void play(SurfaceView view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.reset();
        SurfaceHolder holder = view.getHolder();
        try {
            mediaPlayer.setDataSource("https://media.w3.org/2010/05/sintel/trailer.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
        mediaPlayer.setDisplay(holder);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mediaPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recycler_view;
    }

    public static class Item {

    }
}
