package com.app.swishd.home.search.interfaces;


import com.app.swishd.home.search.model.FindJobModel;

public interface OnEditCallback {
    void onSearchEditOk(FindJobModel model);

    void onDeleteSearch();
}
