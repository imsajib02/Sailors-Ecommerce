package com.b2gsoft.sailorsexpress.contract;

import com.b2gsoft.sailorsexpress.model.BrandProductData;
import com.b2gsoft.sailorsexpress.model.CategoryProductData;
import com.b2gsoft.sailorsexpress.model.Product;
import com.b2gsoft.sailorsexpress.model.ShopProductData;
import com.b2gsoft.sailorsexpress.model.SubCategoryProductData;

import java.util.List;

public interface ProductContract {

    public void showAllBySubCategory(SubCategoryProductData subCategoryProductData);
    public void showAllByCategory(CategoryProductData categoryProductData);
    public void showAllByBrand(BrandProductData brandProductData);
    public void showAllByShop(ShopProductData shopProductData);
    public void failedToGetData();
    public void showProductDetails(Product product, List<Product> similarProductList);
}