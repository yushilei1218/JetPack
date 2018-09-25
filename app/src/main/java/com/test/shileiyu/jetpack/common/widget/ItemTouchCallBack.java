package com.test.shileiyu.jetpack.common.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.test.shileiyu.jetpack.common.base.AbsRBaseAdapter;
import com.test.shileiyu.jetpack.common.bean.Bean;

import java.util.List;

public class ItemTouchCallBack extends ItemTouchHelper.SimpleCallback {

    private final AbsRBaseAdapter<Bean> adapter;

    public ItemTouchCallBack(int dragDirs, int swipeDirs, AbsRBaseAdapter<Bean> adapter) {
        super(dragDirs, swipeDirs);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        List<Bean> data = adapter.data;
        int from = viewHolder.getAdapterPosition();
        Bean remove = data.remove(from);
        int to = target.getAdapterPosition();
        data.add(to, remove);
        adapter.notifyItemMoved(from, to);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
