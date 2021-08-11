package com.b2gsoft.sailorsexpress.contract;

import com.b2gsoft.sailorsexpress.model.Brand;

import java.util.List;

public interface BrandContract {

    public void showAll(List<Brand> brandList);
    public void failedToGetBrands();
}
