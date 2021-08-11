package com.b2gsoft.sailorsexpress.model;

import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SubCategoryProductData {

    @SerializedName("data")
    private Data data;

    public SubCategoryProductData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("product")
        @Nullable
        private List<Product> products;

        @SerializedName("subcategory")
        @Nullable
        private SubCategory subCategory;

        @SerializedName("subsubcategory")
        @Nullable
        private List<SubSubCategory> subSubCategories;

        public Data(@Nullable List<Product> products, @Nullable SubCategory subCategory, @Nullable List<SubSubCategory> subSubCategories) {
            this.products = products;
            this.subCategory = subCategory;
            this.subSubCategories = subSubCategories;
        }

        @Nullable
        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(@Nullable List<Product> products) {
            this.products = products;
        }

        @Nullable
        public SubCategory getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(@Nullable SubCategory subCategory) {
            this.subCategory = subCategory;
        }

        @Nullable
        public List<SubSubCategory> getSubSubCategories() {
            return subSubCategories;
        }

        public void setSubSubCategories(@Nullable List<SubSubCategory> subSubCategories) {
            this.subSubCategories = subSubCategories;
        }
    }
}
