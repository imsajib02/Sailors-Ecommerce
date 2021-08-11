package com.b2gsoft.sailorsexpress.contract;

import com.b2gsoft.sailorsexpress.model.SearchData;

public interface SearchContract {

    public void showData(SearchData searchData);
    public void onFailure();
}