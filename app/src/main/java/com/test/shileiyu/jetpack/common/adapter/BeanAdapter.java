package com.test.shileiyu.jetpack.common.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.AbsBaseAdapter;
import com.test.shileiyu.jetpack.common.bean.Bean;

/**
 * @author shilei.yu
 * @date 2018/9/5
 */

public class BeanAdapter extends AbsBaseAdapter<Bean> {
    @Override
    public VH<Bean> getVH() {
        return new VH<Bean>() {
            @Override
            public View create(Bean item, ViewGroup parent) {
                return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bean, parent, false);
            }

            @Override
            public void bind(Bean item) {
                TextView tv = itemView.findViewById(R.id.item_tv);
                tv.setText(item.name);
            }
        };
    }
}
