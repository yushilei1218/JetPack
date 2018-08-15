package com.test.shileiyu.jetpack.ui.album;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.test.shileiyu.jetpack.BaseApp;
import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.bean.Album;
import com.test.shileiyu.jetpack.common.widget.LoadMoreProcessor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author shilei.yu
 */
public class AlbumFragment extends Fragment {


    @BindView(R.id.fg_album_lv)
    ListView mAlbumLv;

    Unbinder unbinder;

    private AlbumViewModel mModel;

    private LoadMoreProcessor mLoadMoreProcessor;
    private AlbumAdapter mAdapter;

    public AlbumFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);

        AlbumFactory factory = new AlbumFactory(BaseApp.sApplication) {
            @SuppressWarnings("unchecked")
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass == AlbumViewModel.class) {
                    //这里构造的ViewModel时 可从组件中获取必要argument等参数，
                    // 从而传递给Model作为初始化条件
                    return (T) new AlbumViewModel(new AlbumLiveData());
                }
                return super.create(modelClass);
            }
        };
        ViewModelProvider provider = ViewModelProviders.of(this, factory);

        mModel = provider.get(AlbumViewModel.class);

        mModel.get().observe(this, new Observer<List<Album>>() {
            @Override
            public void onChanged(@Nullable List<Album> albums) {
                mAdapter.data = albums;
                mAdapter.notifyDataSetChanged();
                mLoadMoreProcessor.loadFinish();
            }
        });

        mLoadMoreProcessor = new LoadMoreProcessor() {
            @Override
            public void onLoadMore() {
                mModel.mData.requestAlbums(false);
            }
        };
        mAdapter = new AlbumAdapter();
        mAlbumLv.setAdapter(mAdapter);
        mAlbumLv.setOnScrollListener(mLoadMoreProcessor);

        mModel.mData.requestAlbums(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public final class AlbumAdapter extends BaseAdapter {
        List<Album> data;

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
                convertView.setTag(new VH(convertView));
            }

            VH tag = (VH) convertView.getTag();
            Album album = data.get(position);
            Glide.with(parent.getContext())
                    .load(album.coverMiddle)
                    .apply(RequestOptions.circleCropTransform())
                    .into(tag.img);
            tag.title.setText(album.title);
            tag.desc.setText(album.intro);
            return convertView;
        }
    }

    public final class VH {
        ImageView img;
        TextView title;
        TextView desc;

        VH(View view) {
            img = view.findViewById(R.id.item_album_img);
            title = view.findViewById(R.id.item_album_title);
            desc = view.findViewById(R.id.item_album_desc);
        }
    }
}
