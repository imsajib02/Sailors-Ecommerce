package com.b2gsoft.sailorsexpress.contract;

import com.b2gsoft.sailorsexpress.model.Shop;

import java.util.List;

public interface ShopContract {

    public void showAll(List<Shop> shopList);
    public void failedToGetShops();
}
