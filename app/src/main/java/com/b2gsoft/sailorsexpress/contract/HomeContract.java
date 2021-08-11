package com.b2gsoft.sailorsexpress.contract;

import com.b2gsoft.sailorsexpress.model.HomeData;

public interface HomeContract {

    public void onSuccess(HomeData homeData);
    public void onFailure();
}
