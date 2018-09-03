package com.test.shileiyu.jetpack.common.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import retrofit2.http.PUT;

/**
 * @author shilei.yu
 * @date 2018/8/10
 */

public abstract class AbsBaseAdapter<T> extends BaseAdapter {
    public List<T> data;

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            VH<T> vh = getVH();
            convertView = vh.create(data.get(position), parent);
            vh.itemView = convertView;
            convertView.setTag(vh);
        }
        ((VH) convertView.getTag()).bind(data.get(position));

        return convertView;
    }

    public abstract VH<T> getVH();

    public static abstract class VH<T> {
        protected View itemView;

        public abstract View create(T item, ViewGroup parent);

        public abstract void bind(T item);

    }
}
