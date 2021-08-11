package com.b2gsoft.sailorsexpress.contract;

import com.b2gsoft.sailorsexpress.model.Category;

import java.util.List;

public interface CategoryContract {

    public void showAll(List<Category> categoryList);
    public void failedToGetCategories();
}
