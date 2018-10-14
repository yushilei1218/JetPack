package com.test.shileiyu.jetpack.ui;

import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.AbsRBaseAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;

import butterknife.BindView;

public class TextureMediaActivity extends BaseActivity {
    @BindView(R.id.media_recycler)
    RecyclerView recyclerView;
    MediaPlayer mediaPlayer;

    @Override
    protected void initView(Bundle savedInstanceState) {
        Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);
        mediaPlayer=new MediaPlayer();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_texture_media;
    }


    public static class MediaBean {

    }

    public  class Adapter extends AbsRBaseAdapter<MediaBean> {

        @Override
        public VH<MediaBean> getVH(@NonNull ViewGroup parent, int viewType) {
            View child = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_texture, parent, false);
            return new VH<MediaBean>(child) {
                @Override
                public void bind(MediaBean item) {
                    TextView tv = findView(R.id.media_desc);
                    final TextureView textureView = findView(R.id.media_texture);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SurfaceTexture texture = textureView.getSurfaceTexture();
                            if (texture==null){
                                textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                                    @Override
                                    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                                        mediaPlayer.setSurface(new Surface(surface));
                                    }

                                    @Override
                                    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

                                    }

                                    @Override
                                    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                                        return false;
                                    }

                                    @Override
                                    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

                                    }
                                });
                            }else {
                                mediaPlayer.setSurface(new Surface(texture));
                            }

                        }
                    });
                }
            };
        }
    }
}
