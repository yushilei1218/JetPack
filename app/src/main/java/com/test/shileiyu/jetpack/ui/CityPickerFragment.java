package com.test.shileiyu.jetpack.ui;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.bean.city.BaseComposite;
import com.test.shileiyu.jetpack.common.bean.city.City;
import com.test.shileiyu.jetpack.common.bean.city.Country;
import com.test.shileiyu.jetpack.common.bean.city.District;
import com.test.shileiyu.jetpack.common.bean.city.Province;
import com.test.shileiyu.jetpack.common.bean.city.SimpleCallBack;
import com.test.shileiyu.jetpack.common.util.Util;
import com.test.shileiyu.jetpack.common.widget.ItemPickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A fragment with a Google +1 button.
 */
public class CityPickerFragment extends DialogFragment implements Country.UIBinder {

    Country mCountry;
    @BindView(R.id.province_pick)
    ItemPickerView mProvincePick;
    @BindView(R.id.city_pick)
    ItemPickerView mCityPick;
    @BindView(R.id.district_pick)
    ItemPickerView mDistrictPick;

    private Unbinder unbinder;

    private SimpleCallBack<Boolean> mBack;

    public CityPickerFragment() {
    }

    public static CityPickerFragment instance(Country country, SimpleCallBack<Boolean> back) {
        CityPickerFragment fg = new CityPickerFragment();
        fg.mCountry = country;
        fg.mBack = back;
        return fg;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_city_picker, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mCountry.setUIBinder(this);

        mProvincePick.setListener(new ItemPickerView.OnItemSelectListener() {
            @Override
            public void onItemSelect(@NonNull ItemPickerView.BaseItem item) {
                Province extra = (Province) item.extra;
                mCountry.onSelect(extra);
            }
        });

        mCityPick.setListener(new ItemPickerView.OnItemSelectListener() {
            @Override
            public void onItemSelect(@NonNull ItemPickerView.BaseItem item) {
                City extra = (City) item.extra;
                mCountry.onSelect(extra);
            }
        });

        mDistrictPick.setListener(new ItemPickerView.OnItemSelectListener() {
            @Override
            public void onItemSelect(@NonNull ItemPickerView.BaseItem item) {
                District extra = (District) item.extra;
                mCountry.onSelect(extra);
            }
        });

        mCountry.init();
    }

    @OnClick(R.id.confirm_pick)
    public void onClick() {
        dismiss();
        if (mBack != null) {
            mBack.onCall(true);
        }
        mCountry.toast();
    }

    @Override
    public void showPickers(List<Province> p, List<City> c, List<District> d) {
        showPickOrNot(p, mProvincePick);
        showPickOrNot(c, mCityPick);
        showPickOrNot(d, mDistrictPick);
    }

    private void showPickOrNot(List<? extends BaseComposite> composites, ItemPickerView picker) {
        if (Util.isEmpty(composites)) {
            picker.setDisplayItems(null);
        } else {
            List<ItemPickerView.BaseItem> baseItems = new ArrayList<>();
            for (int i = 0; i < composites.size(); i++) {
                baseItems.add(new BaseItemWrap(composites.get(i)));
            }
            picker.setDisplayItems(baseItems);
        }
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }


    @Override
    public void onStart() {
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            Window window = dialog.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                    .MATCH_PARENT);
        }
        super.onStart();
    }

    public static class BaseItemWrap extends ItemPickerView.BaseItem {
        public BaseItemWrap(BaseComposite c) {
            this.isSelect = c.isSelect;
            extra = c;

        }

        @Override
        public String getName() {
            if (extra != null) {
                return ((BaseComposite) extra).name;
            }
            return null;
        }
    }
}
