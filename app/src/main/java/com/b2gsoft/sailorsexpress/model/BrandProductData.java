package com.b2gsoft.sailorsexpress.model;

import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BrandProductData {

    @SerializedName("data")
    private Data data;

    public BrandProductData(Data data) {
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

        @SerializedName("brand")
        @Nullable
        private Brand brand;

        public Data(@Nullable List<Product> products, @Nullable Brand brand) {
            this.products = products;
            this.brand = brand;
        }

        @Nullable
        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(@Nullable List<Product> products) {
            this.products = products;
        }

        @Nullable
        public Brand getBrand() {
            return brand;
        }

        public void setBrand(@Nullable Brand brand) {
            this.brand = brand;
        }
    }
}
