package com.test.shileiyu.jetpack.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.AbsBaseAdapter;

/**
 * @author shilei.yu
 * @date 2018/8/31
 */

public class SortAdapter extends AbsBaseAdapter<SortParams> {
    OnItemClick mClick;

    public SortAdapter(OnItemClick click) {
        mClick = click;
    }

    @Override
    public VH<SortParams> getVH() {
        return new VH<SortParams>() {
            @Override
            public View create(SortParams item, ViewGroup parent) {
                return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sort, parent, false);
            }

            @Override
            public void bind(final SortParams item) {
                TextView tv = itemView.findViewById(R.id.item_sort_tv);
                View img = itemView.findViewById(R.id.item_sort_img);
                tv.setText(item.name);
                img.setSelected(item.isSelect);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mClick != null) {
                            mClick.onClick(item);
                        }
                    }
                });
            }
        };
    }

    public interface OnItemClick {
        void onClick(SortParams item);
    }
}
