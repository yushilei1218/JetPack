package com.test.shileiyu.jetpack.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.test.shileiyu.jetpack.R;
import com.google.android.gms.plus.PlusOneButton;
import com.test.shileiyu.jetpack.common.bean.city.BaseComposite;
import com.test.shileiyu.jetpack.common.bean.city.City;
import com.test.shileiyu.jetpack.common.bean.city.Country;
import com.test.shileiyu.jetpack.common.bean.city.District;
import com.test.shileiyu.jetpack.common.bean.city.Province;
import com.test.shileiyu.jetpack.common.widget.ItemPickerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * A fragment with a Google +1 button.
 */
public class CityPickerFragment extends BottomSheetDialogFragment {

    Country mCountry;
    @BindView(R.id.province_pick)
    ItemPickerView mProvincePick;
    @BindView(R.id.city_pick)
    ItemPickerView mCityPick;
    @BindView(R.id.district_pick)
    ItemPickerView mDistrictPick;
    private Unbinder mUnbinder;

    public CityPickerFragment() {
    }

    public static CityPickerFragment instance(Country country) {
        CityPickerFragment fg = new CityPickerFragment();
        fg.mCountry = country;
        return fg;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_city_picker, container, false);
        mUnbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Province[] composites = new Province[8];
        composites[0]=new Province(1,"北京");
        composites[1]=new Province(2,"香港");
        composites[2]=new Province(2,"黑龙江");
        composites[3]=new Province(2,"吉林");
        composites[4]=new Province(2,"辽宁");
        composites[5]=new Province(2,"河北");
        composites[6]=new Province(2,"河南");
        composites[7]=new Province(2,"山东");
        showPickers(composites, null, null);
    }

    public void showPickers(Province[] p, City[] c, District[] d) {
        showPickOrNot(p, mProvincePick);
        showPickOrNot(c, mCityPick);
        showPickOrNot(d, mDistrictPick);
    }

    private void showPickOrNot(BaseComposite[] composites, ItemPickerView picker) {
        if (isEmpty(composites)) {
            picker.setDisplayItems(null);
        } else {
            List<ItemPickerView.Child> children = new ArrayList<>();
            for (int i = 0; i < composites.length; i++) {
                children.add(new ChildWrap(composites[i]));
            }
            picker.setDisplayItems(children);
        }
    }

    private boolean isEmpty(Object[] a) {
        return a == null || a.length == 0;
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

    public static class ChildWrap extends ItemPickerView.Child<BaseComposite> {
        BaseComposite c;

        public ChildWrap(BaseComposite c) {
            this.c = c;
        }

        @Override
        public String getName() {
            return c.name;
        }
    }
}
