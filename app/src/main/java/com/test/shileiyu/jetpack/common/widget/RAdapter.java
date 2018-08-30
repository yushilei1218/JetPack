package com.test.shileiyu.jetpack.common.widget;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.AbsRBaseAdapter;
import com.test.shileiyu.jetpack.common.bean.Bean;

/**
 * @author shilei.yu
 * @date 2018/8/30
 */

public class RAdapter extends AbsRBaseAdapter<Bean>{
    @Override
    public VH<Bean> getVH(@NonNull ViewGroup parent, int viewType) {
        final View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bean, parent, false);
        return new VH<Bean>(inflate) {
            @Override
            public void bind(Bean item) {
                TextView tv = (TextView) inflate.findViewById(R.id.item_tv);
                tv.setText(item.name);
            }
        };
    }
}
