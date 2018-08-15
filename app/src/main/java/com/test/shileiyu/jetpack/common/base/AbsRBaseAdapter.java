package com.test.shileiyu.jetpack.common.base;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.http.PUT;

/**
 * @author shilei.yu
 * @date 2018/8/10
 */

public abstract class AbsRBaseAdapter<T> extends RecyclerView.Adapter<AbsRBaseAdapter.VH> {
    public List<T> data;

    @NonNull
    @Override
    public VH<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getVH(parent, viewType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(data.get(position));
    }

    public abstract VH<T> getVH(@NonNull ViewGroup parent, int viewType);

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public static abstract class VH<T> extends RecyclerView.ViewHolder {
        private SparseArray<View> mViews = new SparseArray<>();

        public VH(View itemView) {
            super(itemView);
        }

        public abstract void bind(T item);

        @SuppressWarnings("unchecked")
        protected <E extends View> E findView(@IdRes int rId) {
            View view = mViews.get(rId);
            if (view == null) {
                view = itemView.findViewById(rId);
                if (view != null) {
                    mViews.put(rId, view);
                }
            }
            return (E) view;
        }
    }
}
