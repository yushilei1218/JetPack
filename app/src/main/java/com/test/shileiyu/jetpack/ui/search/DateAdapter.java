package com.test.shileiyu.jetpack.ui.search;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.AbsRBaseAdapter;

/**
 * @author shilei.yu
 * @date 2018/8/31
 */

public class DateAdapter extends AbsRBaseAdapter<DateParams> {
    @Override
    public VH<DateParams> getVH(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false);
        return new VH<DateParams>(inflate) {
            @Override
            public void bind(final DateParams item) {
                TextView tv = (TextView) itemView.findViewById(R.id.item_date_tv);
                tv.setText(item.name);
                tv.setSelected(item.isSelected);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.isSelected = !item.isSelected;
                        notifyDataSetChanged();
                    }
                });
            }
        };
    }
}
