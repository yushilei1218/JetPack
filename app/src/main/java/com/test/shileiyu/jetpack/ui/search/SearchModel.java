package com.test.shileiyu.jetpack.ui.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @date 2018/8/31
 */

public class SearchModel {
    final List<SortParams> mParams = new ArrayList<>();
    private SortParams mTmpSort;

    private List<DateParams> mDateParams = new ArrayList<>();
    private List<DateParams> mDateParams2 = new ArrayList<>();
    private final SortParams mDef;

    public SearchModel() {
        mDef = new SortParams("推荐排序");
        mParams.add(mDef);
        mParams.add(new SortParams("智能排序"));
        mParams.add(new SortParams("最近开场"));
        selectSort(mDef);
        mDateParams.add(new DateParams("不限"));
        mDateParams.add(new DateParams("一居"));
        mDateParams.add(new DateParams("二居"));
        mDateParams.add(new DateParams("三居"));

        mDateParams2.add(new DateParams("不限"));
        mDateParams2.add(new DateParams("一居"));
        mDateParams2.add(new DateParams("二居"));
        mDateParams2.add(new DateParams("三居"));
    }

    public List<SortParams> getSorts() {
        return mParams;
    }

    public SortParams getTmpSort() {
        return mTmpSort;
    }

    public boolean isDefaultSort() {
        return mTmpSort.equals(mDef);
    }

    public void selectSort(SortParams params) {
        for (SortParams p : mParams) {
            boolean is = p.equals(params);
            p.isSelect = is;
            if (is) {
                mTmpSort = p;
            }
        }
    }

    public List<DateParams> cloneData1() {
        List<DateParams> data = new ArrayList<>(mDateParams.size());
        for (DateParams d : mDateParams) {
            data.add((DateParams) d.clone());
        }
        return data;
    }

    public void replaceData1(List<DateParams> data) {
        mDateParams = data;
    }

    public List<DateParams> cloneData2() {
        List<DateParams> data = new ArrayList<>(mDateParams2.size());
        for (DateParams d : mDateParams2) {
            data.add((DateParams) d.clone());
        }
        return data;
    }

    public void replaceData2(List<DateParams> data) {
        mDateParams2 = data;
    }
}
