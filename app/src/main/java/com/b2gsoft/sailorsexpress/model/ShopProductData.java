package com.b2gsoft.sailorsexpress.model;

import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ShopProductData {

    @SerializedName("data")
    private Data data;

    public ShopProductData(Data data) {
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

        @SerializedName("shop")
        @Nullable
        private Shop shop;

        public Data(@Nullable List<Product> products, @Nullable Shop shop) {
            this.products = products;
            this.shop = shop;
        }

        @Nullable
        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(@Nullable List<Product> products) {
            this.products = products;
        }

        @Nullable
        public Shop getShop() {
            return shop;
        }

        public void setShop(@Nullable Shop shop) {
            this.shop = shop;
        }
    }
}
