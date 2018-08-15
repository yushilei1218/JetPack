package com.test.shileiyu.jetpack.ui.home;

import android.app.Activity;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.AbsRBaseAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.Album;
import com.test.shileiyu.jetpack.common.bean.LoadState;
import com.test.shileiyu.jetpack.common.bean.ModelState;
import com.test.shileiyu.jetpack.common.bean.SubTab;
import com.test.shileiyu.jetpack.common.bean.TwoTuple;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author shilei.yu
 */
public class CategoryActivity extends BaseActivity {

    public static final String EXTRA = "EXTRA";

    @BindView(R.id.app_bar_layout)
    AppBarLayout keyWordLayout;

    @BindView(R.id.act_category_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.act_category_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.img_error)
    ImageView imgError;

    private SubTab mTab;

    private CategoryViewModel mViewModel;

    private Adapter2 mAdapter;

    private LoadState mLoadState = LoadState.NO_LOADING;

    public static void invoke(Context context, @NonNull SubTab tab) {
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra(EXTRA, tab);
        context.startActivity(intent);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTab = (SubTab) getIntent().getSerializableExtra(EXTRA);

        getSupportActionBar().setTitle(mTab.keywordName);

        mSwipeRefreshLayout.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
            @Override
            public boolean canChildScrollUp(@NonNull SwipeRefreshLayout parent, @Nullable View child) {

                return keyWordLayout.getTop() < 0;
            }
        });

        mAdapter = new Adapter2(this);

        mRecyclerView.setAdapter(mAdapter);

        mViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        mViewModel.init(this, mTab);

        getLifecycle().addObserver(mViewModel.dataModel);

        mViewModel.dataModel.mLiveData.observe(this, new Observer<TwoTuple<List<Album>, ModelState>>() {
            @Override
            public void onChanged(@Nullable TwoTuple<List<Album>, ModelState> tuple) {
                //1.下拉刷新
                //2.上拉加载更多
                //3.DialogLoading
                //4.首页是否有数据
                //5.是否可以加载更多
                hideDialogLoading();

                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                boolean isSuccess = tuple.second.isSuccess;
                if (isSuccess) {
                    //刷新列表数据
                    mAdapter.data = tuple.first;
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(mViewModel);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.key_word_layout, KeyWordFragment.instance(this, mTab, mViewModel))
                .commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_category;
    }

    public static class Adapter2 extends AbsRBaseAdapter<Album> implements View.OnClickListener {
        Activity mActivity;

        Adapter2(Activity activity) {
            mActivity = activity;
        }

        @Override
        public VH<Album> getVH(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
            return new VH<Album>(inflate) {
                @Override
                public void bind(Album item) {
                    ImageView img = findView(R.id.item_album_img);
                    TextView title = findView(R.id.item_album_title);
                    TextView desc = findView(R.id.item_album_desc);
                    Glide.with(mActivity).load(item.coverMiddle).into(img);
                    title.setText(item.title);
                    desc.setText(item.intro);
                    itemView.setOnClickListener(Adapter2.this);
                }
            };
        }

        @Override
        public void onClick(View v) {

        }
    }
}
