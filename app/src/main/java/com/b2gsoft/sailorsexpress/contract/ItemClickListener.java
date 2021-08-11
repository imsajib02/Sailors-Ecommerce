package com.b2gsoft.sailorsexpress.contract;

import com.b2gsoft.sailorsexpress.model.Brand;
import com.b2gsoft.sailorsexpress.model.Category;
import com.b2gsoft.sailorsexpress.model.Product;
import com.b2gsoft.sailorsexpress.model.Shop;
import com.b2gsoft.sailorsexpress.model.Size;
import com.b2gsoft.sailorsexpress.model.SubCategory;

public interface ItemClickListener {

    public void onCategoryClicked(Category category);
    public void onSubCategoryClicked(SubCategory subCategory);
    public void onProductClicked(Product product);
    public void onBrandClicked(Brand brand);
    public void onShopClicked(Shop shop);
    public void onSizeClicked(Size size);
    public void onSortBySelected(int position);
    public void onPriceBySelected(int position);
}
