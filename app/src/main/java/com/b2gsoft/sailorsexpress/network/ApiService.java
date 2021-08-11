package com.b2gsoft.sailorsexpress.network;

import com.b2gsoft.sailorsexpress.model.HomeData;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @Headers({"Accept: application/json"})
    @GET("home")
    Call<HomeData> getHomeData();

    @Headers({"Accept: application/json"})
    @GET("category/all")
    Call<JsonObject> allCategory();

    @Headers({"Accept: application/json"})
    @GET("subcategory/all")
    Call<JsonObject> allSubCategory();

    @Headers({"Accept: application/json"})
    @GET("subsubcategory/all")
    Call<JsonObject> allSubSubCategory();

    @Headers({"Accept: application/json"})
    @GET("brand/all")
    Call<JsonObject> allBrand();

    @Headers({"Accept: application/json"})
    @GET("home/get/shops")
    Call<JsonObject> allShop();

    @Headers({"Accept: application/json"})
    @GET("product/category/{category_id}")
    Call<JsonObject> productByCategory(@Path(value = "category_id", encoded = true) String categoryId);

    @Headers({"Accept: application/json"})
    @GET("product/subcategory/{subcategory_id}")
    Call<JsonObject> productBySubCategory(@Path(value = "subcategory_id", encoded = true) String subCategoryId);

    @Headers({"Accept: application/json"})
    @GET("product/subsubcategory/{subsubcategory_id}")
    Call<JsonObject> productBySubSubCategory(@Path(value = "subsubcategory_id", encoded = true) String subSubCategoryId);

    @Headers({"Accept: application/json"})
    @GET("product/brand/{brand_id}")
    Call<JsonObject> productByBrand(@Path(value = "brand_id", encoded = true) String brandId);

    @Headers({"Accept: application/json"})
    @GET("product/shop/{shop_id}")
    Call<JsonObject> productByShop(@Path(value = "shop_id", encoded = true) String shopId);

    @Headers({"Accept: application/json"})
    @GET("product/search/{pattern}")
    Call<JsonObject> search(@Path(value = "pattern", encoded = true) String pattern);

    @Headers({"Accept: application/json"})
    @GET("product/view/{product_id}")
    Call<JsonObject> productDetails(@Path(value = "product_id", encoded = true) String productId);
}