package com.test.shileiyu.jetpack.ui.home;


import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.AbsBaseAdapter;
import com.test.shileiyu.jetpack.common.base.AbsRBaseAdapter;
import com.test.shileiyu.jetpack.common.bean.KeyWord;
import com.test.shileiyu.jetpack.common.bean.KeyWordCategory;
import com.test.shileiyu.jetpack.common.bean.Result;
import com.test.shileiyu.jetpack.common.bean.SubTab;
import com.test.shileiyu.jetpack.common.http.NetWork;
import com.test.shileiyu.jetpack.common.util.Constant;
import com.test.shileiyu.jetpack.common.util.Util;
import com.test.shileiyu.jetpack.common.widget.FixedListView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 */
public class KeyWordFragment extends Fragment {

    @BindView(R.id.key_common_sort)
    TextView keyCommonSort;

    @BindView(R.id.key_most_play)
    TextView keyMostPlay;

    @BindView(R.id.key_least_update)
    TextView keyLeastUpdate;

    @BindView(R.id.key_least_expand)
    TextView keyLeastExpand;

    @BindView(R.id.key_word_sub_layout)
    LinearLayout keyWordLayout;
    Unbinder unbinder;

    SubTab mTab;

    OnKeyWordChangeListener mChangeListener;


    private KeyWordViewModel mViewModel;

    HashMap<KeyWordCategory, RcAdapter> map = new HashMap<>();

    public KeyWordFragment() {
    }

    public static KeyWordFragment instance(SubTab tab, OnKeyWordChangeListener mChangeListener) {
        KeyWordFragment fragment = new KeyWordFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constant.EXTRA, tab);
        fragment.setArguments(args);
        fragment.mChangeListener = mChangeListener;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTab = (SubTab) (getArguments() != null ? getArguments().getSerializable(Constant.EXTRA) : null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_key_word, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mChangeListener.onLoading();

        mViewModel = ViewModelProviders.of(this).get(KeyWordViewModel.class);

        mViewModel.init(mTab);
        mViewModel.mModel.mLiveData.observe(this, new Observer<List<KeyWordCategory>>() {
            @Override
            public void onChanged(@Nullable List<KeyWordCategory> keyWordCategories) {
                if (keyWordCategories == null) {
                    return;
                }
                for (int i = 0; i < keyWordCategories.size(); i++) {
                    KeyWordCategory category = keyWordCategories.get(i);
                    addRecyclerView4KeyWordCategory(category);
                }
            }
        });
        mViewModel.mModel.mCategoryLiveData.observe(this, new Observer<KeyWordCategory>() {
            @Override
            public void onChanged(@Nullable KeyWordCategory keyWordCategory) {
                if (keyWordCategory == null) {
                    return;
                }
                RcAdapter rcAdapter = map.get(keyWordCategory);
                if (rcAdapter != null) {
                    rcAdapter.notifyDataSetChanged();
                }
            }
        });
        mViewModel.mModel.mKeyLiveData.observe(this, new Observer<List<KeyWord>>() {
            @Override
            public void onChanged(@Nullable List<KeyWord> keyWords) {
                mChangeListener.onChange(keyWords);
            }
        });

        getLifecycle().addObserver(mViewModel.mModel);
        mViewModel.mModel.getKeyWords();
    }

    private void addRecyclerView4KeyWordCategory(KeyWordCategory category) {
        Context context = getContext();
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        RcAdapter adapter = new RcAdapter();
        adapter.data = category.metadataValues;
        recyclerView.setAdapter(adapter);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        keyWordLayout.addView(recyclerView, layoutParams);
        map.put(category, adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.key_common_sort, R.id.key_most_play, R.id.key_least_update, R.id.key_least_expand})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.key_common_sort:
                break;
            case R.id.key_most_play:
                break;
            case R.id.key_least_update:
                break;
            case R.id.key_least_expand:
                keyWordLayout.setVisibility(keyWordLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public interface OnKeyWordChangeListener {
        void onChange(List<KeyWord> tabs);

        void onLoading();

        void onFail();
    }

    public class RcAdapter extends AbsRBaseAdapter<KeyWord> {
        @Override
        public VH<KeyWord> getVH(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_key_word, parent, false);
            return new VH<KeyWord>(itemView) {
                @Override
                public void bind(KeyWord item) {
                    TextView textView = findView(R.id.item_key_tv);
                    textView.setSelected(item.isSelect);
                    textView.setText(item.displayName);
                    itemView.setTag(item);
                    itemView.setOnClickListener(mViewModel);
                }
            };
        }

    }
}
