package com.b2gsoft.sailorsexpress.model;

import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SubSubCategoryProductData {

    @SerializedName("data")
    private Data data;

    public SubSubCategoryProductData(Data data) {
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

        @SerializedName("subsubcategory")
        @Nullable
        private SubSubCategory subSubCategory;

        public Data(@Nullable List<Product> products, @Nullable SubSubCategory subSubCategory) {
            this.products = products;
            this.subSubCategory = subSubCategory;
        }

        @Nullable
        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(@Nullable List<Product> products) {
            this.products = products;
        }

        @Nullable
        public SubSubCategory getSubSubCategory() {
            return subSubCategory;
        }

        public void setSubSubCategory(@Nullable SubSubCategory subSubCategory) {
            this.subSubCategory = subSubCategory;
        }
    }
}
