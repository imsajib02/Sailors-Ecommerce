package com.b2gsoft.sailorsexpress.model;

import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CategoryProductData {

    @SerializedName("data")
    private Data data;

    public CategoryProductData(Data data) {
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

        @SerializedName("category")
        @Nullable
        private Category category;

        @SerializedName("subcategory")
        @Nullable
        private List<SubCategory> subCategories;

        public Data(@Nullable List<Product> products, @Nullable Category category, @Nullable List<SubCategory> subCategories) {
            this.products = products;
            this.category = category;
            this.subCategories = subCategories;
        }

        @Nullable
        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(@Nullable List<Product> products) {
            this.products = products;
        }

        @Nullable
        public Category getCategory() {
            return category;
        }

        public void setCategory(@Nullable Category category) {
            this.category = category;
        }

        @Nullable
        public List<SubCategory> getSubCategories() {
            return subCategories;
        }

        public void setSubCategories(@Nullable List<SubCategory> subCategories) {
            this.subCategories = subCategories;
        }
    }
}
