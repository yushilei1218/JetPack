package com.test.shileiyu.jetpack.ui.search;

import com.test.shileiyu.jetpack.common.bean.filter.Composite;
import com.test.shileiyu.jetpack.common.bean.filter.MultiSelComposite;
import com.test.shileiyu.jetpack.common.bean.filter.OriginData;
import com.test.shileiyu.jetpack.common.bean.filter.SingleSelComposite;
import com.test.shileiyu.jetpack.common.util.Util;

import java.util.ArrayList;
import java.util.Iterator;
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

    private OriginData mRootData;

    private List<Composite> tempSelect = new ArrayList<>();

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

        mRootData = OriginData.get();
        mRootData.resetChildren();
        resetChildren(mRootData.children);
    }

    private void resetChildren(List<Composite> children) {
        if (!Util.isEmpty(children)) {
            for (int i = 0; i < children.size(); i++) {
                Composite c = children.get(i);
                c.resetChildren();
                resetChildren(c.children);
            }
        }
    }

    public OriginData getRootData() {
        return mRootData;
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

    public void onCompositeClick(Composite composite) {
        boolean lastNote = composite.isLastNote();
        Class<? extends Composite> aClass = composite.getClass();
        if (lastNote) {
            if (MultiSelComposite.class.isAssignableFrom(aClass)) {
                ((MultiSelComposite) composite).multiSelect();
            } else if (SingleSelComposite.class.isAssignableFrom(aClass)) {
                ((SingleSelComposite) composite).singleSelect();
            }
            List<Composite> children = composite.parent.children;
            if (children.get(0).isSelect) {
                composite.tagParent(false);
            } else {
                composite.tagParent(true);
            }
        } else {
            ((SingleSelComposite) composite).singleSelect();
        }
        if (lastNote && !composite.isDefault() && composite.isSelect) {
            if (!Util.isEmpty(tempSelect)) {
                for (int i = 0; i < tempSelect.size(); i++) {
                    Composite child = tempSelect.get(i);
                    if (!child.parent.equals(composite.parent)) {
                        child.parent.resetChildren();
                        child.tagParent(false);
                    }
                }
            }
            Iterator<Composite> iterator = tempSelect.iterator();
            while (iterator.hasNext()) {
                Composite next = iterator.next();
                if (!next.isSelect) {
                    iterator.remove();
                }
            }
            composite.tagParent(true);
            tempSelect.add(composite);
        }
    }
}
