package com.test.shileiyu.jetpack.ui.search;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.AbsBaseAdapter;
import com.test.shileiyu.jetpack.common.bean.filter.Composite;
import com.test.shileiyu.jetpack.common.bean.filter.MultiSelComposite;
import com.test.shileiyu.jetpack.common.bean.filter.OriginData;
import com.test.shileiyu.jetpack.common.bean.filter.SingleSelComposite;
import com.test.shileiyu.jetpack.common.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shilei.yu
 * @date 2018/8/31
 */

public class FilterPanel {

    SearchModel mModel;

    View mRoot;

    @BindView(R.id.filter_layout)
    ViewGroup mFilter;
    @BindView(R.id.sort_tv)
    TextView mSortTv;
    @BindView(R.id.sort_img)
    ImageView mSortImg;
    @BindView(R.id.date_tv)
    TextView mDateTv;

    @BindView(R.id.location_img)
    ImageView mLocationImg;
    @BindView(R.id.location_tv)
    TextView mLocationTv;

    @BindView(R.id.date_img)
    ImageView mDateImg;
    @BindView(R.id.sort_lv)
    ListView mListView;

    @BindView(R.id.date_recy1)
    RecyclerView mRecycler1;
    @BindView(R.id.date_recy2)
    RecyclerView mRecycler2;

    @BindView(R.id.first_lv)
    ListView mFirstLv;
    @BindView(R.id.second_lv)
    ListView mSecondLv;
    @BindView(R.id.third_lv)
    ListView mThirdLv;

    private final int SORT_RID = R.id.sort_lv;
    private final int DATE_RID = R.id.date_layout;
    private final int LOCATION_RID = R.id.location_layout;

    private SortAdapter mAdapter;
    private final DateAdapter mAdapter1;
    private final DateAdapter mAdapter2;

    private final FirstAdapter secondAdapter;
    private final FirstAdapter firstAdapter;
    private final ThirdAdapter thirdAdapter;

    public FilterPanel(SearchModel model, View root) {
        ButterKnife.bind(this, root);
        mModel = model;
        mRoot = root;
        mRecycler1.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecycler2.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter1 = new DateAdapter();
        mRecycler1.setAdapter(mAdapter1);
        mAdapter2 = new DateAdapter();
        mRecycler2.setAdapter(mAdapter2);

        firstAdapter = new FirstAdapter();
        mFirstLv.setAdapter(firstAdapter);
        secondAdapter = new FirstAdapter();
        mSecondLv.setAdapter(secondAdapter);
        thirdAdapter = new ThirdAdapter();
        mThirdLv.setAdapter(thirdAdapter);
    }

    @OnClick({
            R.id.sort_tv,
            R.id.date_tv,
            R.id.filter_layout,
            R.id.confirm,
            R.id.location_tv
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter_layout:
                closeFilterLayout();
                break;
            case R.id.location_tv:
            case R.id.sort_tv:
            case R.id.date_tv:
                showOrClose(view.getId());
                break;
            case R.id.confirm:
                mModel.replaceData1(mAdapter1.data);
                mModel.replaceData2(mAdapter2.data);
                closeFilterLayout();
                break;
            default:
                break;
        }

    }

    public void showOrClose(int rid) {
        Object tag = mFilter.getTag();
        if (tag == null || !tag.equals(rid)) {
            showFilterLayout(rid);
        } else {
            closeFilterLayout();
        }
    }

    public void closeFilterLayout() {
        mFilter.setVisibility(View.GONE);
        mFilter.setTag(null);
    }

    public void showFilterLayout(int btnId) {
        mFilter.setVisibility(View.VISIBLE);
        mFilter.setTag(btnId);
        int innerLayoutId = -1;
        switch (btnId) {
            case R.id.sort_tv:
                innerLayoutId = SORT_RID;
                break;
            case R.id.date_tv:
                innerLayoutId = DATE_RID;
                break;
            case R.id.location_tv:
                innerLayoutId = LOCATION_RID;
                break;
            default:
                break;
        }
        showInnerLayout(innerLayoutId);
    }

    private void showInnerLayout(int layoutId) {

        int childCount = mFilter.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = mFilter.getChildAt(i);
            child.setVisibility(layoutId == child.getId() ? View.VISIBLE : View.GONE);
        }
        switch (layoutId) {
            case SORT_RID: {
                if (mAdapter == null) {
                    mAdapter = new SortAdapter(new SortAdapter.OnItemClick() {
                        @Override
                        public void onClick(SortParams item) {
                            mModel.selectSort(item);
                            mAdapter.notifyDataSetChanged();
                            closeFilterLayout();
                            setUpFilterBarCloseState();
                        }
                    });
                    mListView.setAdapter(mAdapter);
                }
                mAdapter.data = mModel.getSorts();
                mAdapter.notifyDataSetChanged();
            }
            break;
            case DATE_RID:
                List<DateParams> dateParams = mModel.cloneData1();
                List<DateParams> dateParams1 = mModel.cloneData2();
                mAdapter1.data = dateParams;
                mAdapter1.notifyDataSetChanged();
                mAdapter2.data = dateParams1;
                mAdapter2.notifyDataSetChanged();
                break;
            case LOCATION_RID:
                updateLocationAdapter();
                break;
            default:
                break;
        }
    }

    public void updateLocationAdapter() {
        OriginData originData = mModel.getRootData();
        if (originData != null) {
            List<Composite> types = originData.children;
            firstAdapter.data = types;
            firstAdapter.notifyDataSetChanged();
            if (!Util.isEmpty(types)) {
                Composite select = null;
                for (Composite c : types) {
                    if (c.isSelect) {
                        select = c;
                        break;
                    }
                }
                if (select != null) {
                    //二级
                    List<Composite> levelTwo = select.children;
                    if (Util.isEmpty(levelTwo.get(0).children)) {
                        mSecondLv.setVisibility(View.GONE);
                        thirdAdapter.data = levelTwo;
                        thirdAdapter.notifyDataSetChanged();
                    } else {
                        mSecondLv.setVisibility(View.VISIBLE);
                        secondAdapter.data = levelTwo;
                        secondAdapter.notifyDataSetChanged();
                        Composite third = null;
                        for (Composite c : levelTwo) {
                            if (c.isSelect) {
                                third = c;
                                break;
                            }
                        }
                        if (third != null) {
                            thirdAdapter.data = third.children;
                            thirdAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
    }

    public void setUpFilterBarCloseState() {
        mSortTv.setText(mModel.getTmpSort().name);
        boolean defaultSort = mModel.isDefaultSort();
        mSortTv.setSelected(!defaultSort);
        mSortImg.setSelected(false);
        mDateTv.setText("日期选择");
        mDateImg.setSelected(false);
        mLocationTv.setText("位置信息");
    }

    public class FirstAdapter extends AbsBaseAdapter<Composite> implements View.OnClickListener {

        @Override
        public VH<Composite> getVH() {
            return new VH<Composite>() {
                @Override
                public View create(Composite item, ViewGroup parent) {
                    return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_first, parent, false);
                }

                @Override
                public void bind(Composite item) {
                    TextView tv = itemView.findViewById(R.id.item_first_tv);
                    View tag = itemView.findViewById(R.id.item_first_tag);
                    tv.setText(item.name);
                    tv.setSelected(item.isSelect);
                    tag.setVisibility(item.isTag ? View.VISIBLE : View.GONE);
                    itemView.setTag(R.integer.id_r, item);
                    itemView.setOnClickListener(FirstAdapter.this);
                }
            };
        }

        @Override
        public void onClick(View v) {
            Object tag = v.getTag(R.integer.id_r);
            mModel.onCompositeClick((Composite) tag);
            updateLocationAdapter();
        }
    }

    public class ThirdAdapter extends AbsBaseAdapter<Composite> implements View.OnClickListener {

        @Override
        public VH<Composite> getVH() {
            return new VH<Composite>() {
                @Override
                public View create(Composite item, ViewGroup parent) {
                    return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_third, parent, false);
                }

                @Override
                public void bind(Composite item) {
                    TextView tv = itemView.findViewById(R.id.item_third_tv);
                    View gou1 = itemView.findViewById(R.id.gou1);
                    View gou2 = itemView.findViewById(R.id.gou2);
                    tv.setText(item.name);
                    tv.setSelected(item.isSelect);
                    Class<? extends Composite> aClass = item.getClass();
                    if (SingleSelComposite.class.isAssignableFrom(aClass)) {
                        gou2.setVisibility(View.GONE);
                        gou1.setVisibility(item.isSelect ? View.VISIBLE : View.GONE);
                    } else if (MultiSelComposite.class.isAssignableFrom(aClass)) {
                        gou1.setVisibility(View.GONE);
                        gou2.setVisibility(item.isSelect ? View.VISIBLE : View.GONE);
                    }
                    itemView.setTag(R.integer.id_r, item);
                    itemView.setOnClickListener(ThirdAdapter.this);
                }
            };
        }

        @Override
        public void onClick(View v) {
            Object tag = v.getTag(R.integer.id_r);
            mModel.onCompositeClick((Composite) tag);
            updateLocationAdapter();
        }
    }
}
