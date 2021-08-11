package com.b2gsoft.sailorsexpress.model;

import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeData {

    @SerializedName("data")
    private Data data;

    public HomeData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("info")
        @Nullable
        private Info info;

        @SerializedName("featuredCategory")
        @Nullable
        private List<Category> featuredCategory;

        @SerializedName("category")
        @Nullable
        private List<Category> category;

        @SerializedName("brand")
        @Nullable
        private OurBrands ourBrands;

        @SerializedName("shop")
        @Nullable
        private OurShops ourShops;

        @SerializedName("topSelling")
        @Nullable
        private TopSelling topSelling;

        @SerializedName("featured")
        @Nullable
        private FeaturedProducts featuredProducts;

        @SerializedName("newArrival")
        @Nullable
        private NewArrival newArrival;

        public Data(@Nullable Info info, @Nullable List<Category> featuredCategory, @Nullable List<Category> category, @Nullable OurBrands ourBrands, @Nullable OurShops ourShops,
                    @Nullable TopSelling topSelling, @Nullable FeaturedProducts featuredProducts, @Nullable NewArrival newArrival) {

            this.info = info;
            this.featuredCategory = featuredCategory;
            this.category = category;
            this.ourBrands = ourBrands;
            this.ourShops = ourShops;
            this.topSelling = topSelling;
            this.featuredProducts = featuredProducts;
            this.newArrival = newArrival;
        }

        @Nullable
        public Info getInfo() {
            return info;
        }

        public void setInfo(@Nullable Info info) {
            this.info = info;
        }

        @Nullable
        public List<Category> getFeaturedCategory() {
            return featuredCategory;
        }

        public void setFeaturedCategory(@Nullable List<Category> featuredCategory) {
            this.featuredCategory = featuredCategory;
        }

        @Nullable
        public List<Category> getCategory() {
            return category;
        }

        public void setCategory(@Nullable List<Category> category) {
            this.category = category;
        }

        @Nullable
        public OurBrands getOurBrands() {
            return ourBrands;
        }

        public void setOurBrands(@Nullable OurBrands ourBrands) {
            this.ourBrands = ourBrands;
        }

        @Nullable
        public OurShops getOurShops() {
            return ourShops;
        }

        public void setOurShops(@Nullable OurShops ourShops) {
            this.ourShops = ourShops;
        }

        @Nullable
        public TopSelling getTopSelling() {
            return topSelling;
        }

        public void setTopSelling(@Nullable TopSelling topSelling) {
            this.topSelling = topSelling;
        }

        @Nullable
        public FeaturedProducts getFeaturedProducts() {
            return featuredProducts;
        }

        public void setFeaturedProducts(@Nullable FeaturedProducts featuredProducts) {
            this.featuredProducts = featuredProducts;
        }

        @Nullable
        public NewArrival getNewArrival() {
            return newArrival;
        }

        public void setNewArrival(@Nullable NewArrival newArrival) {
            this.newArrival = newArrival;
        }
    }


    public class OurBrands {

        @SerializedName("title")
        @Nullable
        private String title;

        @SerializedName("items")
        @Nullable
        private List<Brand> items;

        public OurBrands(@Nullable String title, @Nullable List<Brand> items) {
            this.title = title;
            this.items = items;
        }

        @Nullable
        public String getTitle() {
            return title;
        }

        public void setTitle(@Nullable String title) {
            this.title = title;
        }

        @Nullable
        public List<Brand> getItems() {
            return items;
        }

        public void setItems(@Nullable List<Brand> items) {
            this.items = items;
        }
    }


    public class OurShops {

        @SerializedName("title")
        @Nullable
        private String title;

        @SerializedName("items")
        @Nullable
        private List<Shop> items;

        public OurShops(@Nullable String title, @Nullable List<Shop> items) {
            this.title = title;
            this.items = items;
        }

        @Nullable
        public String getTitle() {
            return title;
        }

        public void setTitle(@Nullable String title) {
            this.title = title;
        }

        @Nullable
        public List<Shop> getItems() {
            return items;
        }

        public void setItems(@Nullable List<Shop> items) {
            this.items = items;
        }
    }


    public class TopSelling {

        @SerializedName("title")
        @Nullable
        private String title;

        @SerializedName("items")
        @Nullable
        private List<Product> items;

        public TopSelling(@Nullable String title, @Nullable List<Product> items) {
            this.title = title;
            this.items = items;
        }

        @Nullable
        public String getTitle() {
            return title;
        }

        public void setTitle(@Nullable String title) {
            this.title = title;
        }

        @Nullable
        public List<Product> getItems() {
            return items;
        }

        public void setItems(@Nullable List<Product> items) {
            this.items = items;
        }
    }


    public class FeaturedProducts {

        @SerializedName("title")
        @Nullable
        private String title;

        @SerializedName("items")
        @Nullable
        private List<Product> items;

        public FeaturedProducts(@Nullable String title, @Nullable List<Product> items) {
            this.title = title;
            this.items = items;
        }

        @Nullable
        public String getTitle() {
            return title;
        }

        public void setTitle(@Nullable String title) {
            this.title = title;
        }

        @Nullable
        public List<Product> getItems() {
            return items;
        }

        public void setItems(@Nullable List<Product> items) {
            this.items = items;
        }
    }


    public class NewArrival {

        @SerializedName("title")
        @Nullable
        private String title;

        @SerializedName("items")
        @Nullable
        private List<Product> items;

        public NewArrival(@Nullable String title, @Nullable List<Product> items) {
            this.title = title;
            this.items = items;
        }

        @Nullable
        public String getTitle() {
            return title;
        }

        public void setTitle(@Nullable String title) {
            this.title = title;
        }

        @Nullable
        public List<Product> getItems() {
            return items;
        }

        public void setItems(@Nullable List<Product> items) {
            this.items = items;
        }
    }
}
