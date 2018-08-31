package com.test.shileiyu.jetpack.ui.search;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shilei.yu
 * @date 2018/8/31
 */

public class FilterPanel {
    private final DateAdapter mAdapter1;
    private final DateAdapter mAdapter2;
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
    @BindView(R.id.date_img)
    ImageView mDateImg;
    @BindView(R.id.sort_lv)
    ListView mListView;

    @BindView(R.id.date_recy1)
    RecyclerView mRecycler1;
    @BindView(R.id.date_recy2)
    RecyclerView mRecycler2;


    private final int SORT_RID = R.id.sort_lv;
    private final int DATE_RID = R.id.date_layout;

    private SortAdapter mAdapter;


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
    }

    @OnClick({
            R.id.sort_tv,
            R.id.date_tv,
            R.id.filter_layout,
            R.id.confirm
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter_layout:
                closeFilterLayout();
                break;
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
            default:
                break;
        }
    }

    public void setUpFilterBarCloseState() {
        mSortTv.setText(mModel.getTmpSort().name);
        boolean defaultSort = mModel.isDefaultSort();
        mSortTv.setSelected(!defaultSort);
        mSortImg.setSelected(false);
        mDateTv.setText("日期选择");
        mDateImg.setSelected(false);
    }
}
