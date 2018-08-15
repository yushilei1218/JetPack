package com.test.shileiyu.jetpack.common.widget;

import android.widget.AbsListView;

/**
 * @author shilei.yu
 * @date 2018/8/8
 */

public abstract class LoadMoreProcessor implements AbsListView.OnScrollListener {

    private boolean isCanLoadMore = true;
    private int mLastItem;
    private int mTotalItemCount;
    private boolean isLoadingMore = false;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (isLoadingMore) {
            return;
        }
        if (scrollState == SCROLL_STATE_IDLE) {
            if (mTotalItemCount > 0 && mLastItem == mTotalItemCount) {
                isLoadingMore = true;
                onLoadMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mLastItem = firstVisibleItem + visibleItemCount;
        mTotalItemCount = totalItemCount;
    }

    public boolean isCanLoadMore() {
        return isCanLoadMore;
    }

    public void setCanLoadMore(boolean canLoadMore) {
        isCanLoadMore = canLoadMore;
    }

    public void loadFinish() {
        isLoadingMore = false;
    }

    public abstract void onLoadMore();
}
