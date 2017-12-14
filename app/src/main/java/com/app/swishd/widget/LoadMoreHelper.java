package com.app.swishd.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import java.util.ArrayList;

import retrofit2.Callback;

public class LoadMoreHelper {

    private int itemCount = 0;
    private int pageSize = 0;
    private boolean isLoading;

    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LoadMoreListener listener;
    private RecyclerView recyclerView;

    public interface LoadMoreListener {
        void onLoadMore(int lastItemPosition);
    }

    private LoadMoreHelper() {
    }

    public void attachToRecyclerView() {
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                try {
                    checkScrollEnd();
                } catch (Exception e) {
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                try {
                    checkScrollEnd();
                } catch (Exception e) {
                }
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                try {
                    if (dy > 0) //check for scroll down
                        checkScrollEnd();
                } catch (Exception e) {
                }
            }
        });
    }

    private void checkScrollEnd() {
        if (isLoading)
            return;
        visibleItemCount = recyclerView.getLayoutManager().getChildCount();
        totalItemCount = recyclerView.getLayoutManager().getItemCount();

        pastVisiblesItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
            if (listener != null)
                try {
//                    int pageNum = totalItemCount / pageSize;
//                    int lastItemPosition = (pageSize * pageNum);
                    if (totalItemCount < itemCount) {
                        listener.onLoadMore(totalItemCount);
                    }
                } catch (Exception e) {
                }
        }
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }


    public static class Builder {
        private int mItemCount;
        private LoadMoreListener mLoadMoreListner;
        private int mPageSize = 20;
        private RecyclerView mRecyclerView;

        private Builder() {
        }

        public Builder(RecyclerView recyclerView) {
            mRecyclerView = recyclerView;
        }

        public Builder itemCount(int itemCount) {
            mItemCount = itemCount;
            return this;
        }

        public Builder listener(LoadMoreListener listener) {
            mLoadMoreListner = listener;
            return this;
        }

        /**
         * Default page size is 20
         */
        public Builder pageSize(int pageSize) {
            mPageSize = pageSize;
            return this;
        }

        public LoadMoreHelper build() {
            LoadMoreHelper helper = new LoadMoreHelper();
            helper.itemCount = mItemCount;
            helper.listener = mLoadMoreListner;
            helper.pageSize = mPageSize;
            helper.recyclerView = mRecyclerView;
            helper.attachToRecyclerView();
            return helper;
        }
    }
}
