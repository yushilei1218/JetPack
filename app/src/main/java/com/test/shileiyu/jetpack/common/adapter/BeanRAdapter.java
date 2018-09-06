package com.test.shileiyu.jetpack.common.adapter;

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
 * @date 2018/9/5
 */

public class BeanRAdapter extends AbsRBaseAdapter<Bean>{
    @Override
    public VH<Bean> getVH(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bean, parent, false);
        return new VH<Bean>(inflate) {
            @Override
            public void bind(Bean item) {
                TextView tv = itemView.findViewById(R.id.item_tv);
                tv.setText(item.name);
            }
        };
    }
}
