package com.app.swishd.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class EndlessRecyclerOnScrollListenerWithLastItemCount extends RecyclerView.OnScrollListener {


    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private onLastItem onLastItem;
    private RecyclerView fragmentHomeRvOfferDetails;
    private LinearLayoutManager mLayoutManager;
    private boolean loading = false;


    public EndlessRecyclerOnScrollListenerWithLastItemCount(
            LinearLayoutManager mLayoutManager, RecyclerView recyclerView, onLastItem onLastItem) {
        this.mLayoutManager = mLayoutManager;
        this.fragmentHomeRvOfferDetails = recyclerView;
        this.onLastItem = onLastItem;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = fragmentHomeRvOfferDetails
                .getChildCount();

        totalItemCount = mLayoutManager
                .getItemCount();

        firstVisibleItem = mLayoutManager
                .findLastVisibleItemPosition();

        if (!loading) {

            if (totalItemCount == (visibleItemCount + firstVisibleItem)) {
                onLastItem.onLastItem();
                loading = true;
            }
        } else {
            if (totalItemCount > (visibleItemCount + firstVisibleItem))
                loading = false;
        }
    }

    public interface onLastItem {
        void onLastItem();
    }
}
