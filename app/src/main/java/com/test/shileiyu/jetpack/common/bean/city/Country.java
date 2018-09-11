package com.test.shileiyu.jetpack.common.bean.city;

import android.os.SystemClock;

import com.test.shileiyu.jetpack.common.bean.Constant;
import com.test.shileiyu.jetpack.common.util.ThreadPool;
import com.test.shileiyu.jetpack.common.util.Util;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @date 2018/9/11
 */

public class Country extends BaseComposite<Province> {

    public List<Province> mProvinces;
    public List<City> mCities;
    public List<District> mDistricts;

    public WeakReference<UIBinder> mUiWeak;

    public void setUIBinder(UIBinder binder) {
        mUiWeak = new WeakReference<>(binder);
    }

    public void onSelect(final Province p) {
        setChildSelect(p);

        if (Util.isEmpty(p.children)) {
            mCities = null;
            mDistricts = null;
            show();
            p.requestChildren(new SimpleCallBack<Boolean>() {
                @Override
                public void onCall(Boolean data) {
                    onSelect(p);
                }
            });
        } else {
            mCities = p.children;
            mDistricts = null;
            show();
            City child = p.getSelectChild();
            onSelect(child);
        }
    }

    public void init() {
        requestChildren(new SimpleCallBack<Boolean>() {
            @Override
            public void onCall(Boolean data) {
                show();
                Province child = getSelectChild();
                onSelect(child);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public void onSelect(final City city) {
        city.parent.setChildSelect(city);

        if (!Util.isEmpty(city.children)) {
            mDistricts = city.children;
            show();
        } else {
            mDistricts = null;
            show();
            city.requestChildren(new SimpleCallBack<Boolean>() {
                @Override
                public void onCall(Boolean data) {
                    onSelect(city);
                }
            });
        }

    }

    @SuppressWarnings("unchecked")
    public void onSelect(District p) {
        p.parent.setChildSelect(p);
    }

    private void show() {
        if (mUiWeak != null && mUiWeak.get() != null) {
            mUiWeak.get().showPickers(mProvinces, mCities, mDistricts);
        }
    }

    public Country(int id, String name) {
        super(id, name);
    }

    @Override
    public void requestChildren(final SimpleCallBack<Boolean> callBack) {
        ThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(Constant.TIME);
                List<Province> data = new ArrayList<>();
                for (int i = 0; i < 32; i++) {
                    data.add(new Province(i, "A" + i + "省"));
                }
                addChild(data);
                resetChildren();
                mProvinces = children;

                callBack.onCall(true);
            }
        });
    }

    public void toast() {
        Province province = getSelectChild();
        if (province != null) {
            City city = province.getSelectChild();
            if (city != null) {
                District district = city.getSelectChild();
                if (district != null) {
                    Util.showToast(province.name + " " + city.name + " " + district.name);
                }
            }
        }
    }

    public interface UIBinder {
        void showPickers(List<Province> p, List<City> c, List<District> d);
    }
}
