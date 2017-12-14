package com.app.swishd.home.send.interfaces;


import com.app.swishd.home.send.model.ResponseOfficeList;

public interface OnOfficeSelectCallback {
    void onOfficeSelected(ResponseOfficeList.DetailItem mItem);
}
