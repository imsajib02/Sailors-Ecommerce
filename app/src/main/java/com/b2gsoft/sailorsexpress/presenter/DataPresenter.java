package com.b2gsoft.sailorsexpress.presenter;

import android.content.Context;
import android.util.Log;

import com.b2gsoft.sailorsexpress.contract.BrandContract;
import com.b2gsoft.sailorsexpress.contract.CategoryContract;
import com.b2gsoft.sailorsexpress.contract.Connectivity;
import com.b2gsoft.sailorsexpress.contract.HomeContract;
import com.b2gsoft.sailorsexpress.contract.ProductContract;
import com.b2gsoft.sailorsexpress.contract.SearchContract;
import com.b2gsoft.sailorsexpress.contract.ShopContract;
import com.b2gsoft.sailorsexpress.model.Brand;
import com.b2gsoft.sailorsexpress.model.BrandProductData;
import com.b2gsoft.sailorsexpress.model.Category;
import com.b2gsoft.sailorsexpress.model.CategoryProductData;
import com.b2gsoft.sailorsexpress.model.HomeData;
import com.b2gsoft.sailorsexpress.model.Product;
import com.b2gsoft.sailorsexpress.model.SearchData;
import com.b2gsoft.sailorsexpress.model.Shop;
import com.b2gsoft.sailorsexpress.model.ShopProductData;
import com.b2gsoft.sailorsexpress.model.SubCategory;
import com.b2gsoft.sailorsexpress.model.SubCategoryProductData;
import com.b2gsoft.sailorsexpress.model.SubSubCategory;
import com.b2gsoft.sailorsexpress.model.SubSubCategoryProductData;
import com.b2gsoft.sailorsexpress.network.ApiService;
import com.b2gsoft.sailorsexpress.network.RetrofitInstance;
import com.b2gsoft.sailorsexpress.utils.InternetCheck;
import com.b2gsoft.sailorsexpress.utils.NetworkUtils;
import com.b2gsoft.sailorsexpress.utils.ProgressDialog;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPresenter {

    private Context context;
    Gson gson = new Gson();

    private Connectivity connectivity;
    private HomeContract homeContract;
    private CategoryContract categoryContract;
    private ProductContract productContract;
    private BrandContract brandContract;
    private ShopContract shopContract;
    private SearchContract searchContract;

    private RetrofitInstance retrofitInstance = new RetrofitInstance();
    private ApiService service = retrofitInstance.getRetrofitInstance(retrofitInstance.BASE_URL + retrofitInstance.API_VERSION_1).create(ApiService.class);

    public DataPresenter(Context context, Connectivity connectivity, HomeContract homeContract) {
        this.context = context;
        this.homeContract = homeContract;
        this.connectivity = connectivity;
    }

    public DataPresenter(Context context, Connectivity connectivity, CategoryContract categoryContract) {
        this.context = context;
        this.categoryContract = categoryContract;
        this.connectivity = connectivity;
    }

    public DataPresenter(Context context, Connectivity connectivity, ProductContract productContract) {
        this.context = context;
        this.productContract = productContract;
        this.connectivity = connectivity;
    }

    public DataPresenter(Context context, Connectivity connectivity, BrandContract brandContract) {
        this.context = context;
        this.brandContract = brandContract;
        this.connectivity = connectivity;
    }

    public DataPresenter(Context context, Connectivity connectivity, ShopContract shopContract) {
        this.context = context;
        this.shopContract = shopContract;
        this.connectivity = connectivity;
    }

    public DataPresenter(Context context, Connectivity connectivity, SearchContract searchContract) {
        this.context = context;
        this.searchContract = searchContract;
        this.connectivity = connectivity;
    }


    public void getHomeData() {

        if(NetworkUtils.isConnected(context)) {

            new InternetCheck(new InternetCheck.Consumer() {
                @Override
                public void accept(Boolean active) {

                    if(active) {

                        Call<HomeData> call = service.getHomeData();

                        call.enqueue(new Callback<HomeData>() {

                            @Override
                            public void onResponse(Call<HomeData> call, Response<HomeData> response) {

                                if(response.isSuccessful())
                                {
                                    Log.e("Home ", new Gson().toJson(response.body()));

                                    homeContract.onSuccess(response.body());
                                }
                                else
                                {
                                    homeContract.onFailure();
                                    Log.e("RetrofitResponseError ", ""+response.raw());
                                }
                            }

                            @Override
                            public void onFailure(Call<HomeData> call, Throwable t) {

                                if(t instanceof SocketTimeoutException) {

                                    connectivity.onConnectionTimeOut();
                                }
                                else {

                                    homeContract.onFailure();
                                }

                                Log.e("RetrofitFailure ", ""+t.getMessage());
                            }
                        });
                    }
                    else {

                        connectivity.noActiveConnection();
                    }
                }
            });
        }
        else {

            connectivity.notConnected();
        }
    }


    public void getAllCategory() {

        if(NetworkUtils.isConnected(context)) {

            new InternetCheck(new InternetCheck.Consumer() {
                @Override
                public void accept(Boolean active) {

                    if(active) {

                        Call<JsonObject> call = service.allCategory();

                        call.enqueue(new Callback<JsonObject>() {

                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                if(response.isSuccessful())
                                {
                                    Log.e("All Category ", new Gson().toJson(response.body()));

                                    try {

                                        Boolean success = response.body().get("success").getAsBoolean();

                                        if(success) {

                                            List<Category> categoryList = new ArrayList<>();
                                            JsonArray array = response.body().get("data").getAsJsonArray();

                                            for(int i=0; i<array.size(); i++) {

                                                Category category = gson.fromJson(array.get(i).toString(), Category.class);
                                                categoryList.add(category);
                                            }

                                            categoryContract.showAll(categoryList);
                                        }
                                        else {

                                            categoryContract.failedToGetCategories();
                                        }
                                    }
                                    catch (Exception e) {

                                        e.printStackTrace();
                                        categoryContract.failedToGetCategories();
                                    }
                                }
                                else
                                {
                                    Log.e("RetrofitResponseError ", ""+response.raw());
                                    categoryContract.failedToGetCategories();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                if(t instanceof SocketTimeoutException) {

                                    connectivity.onConnectionTimeOut();
                                }
                                else {

                                    categoryContract.failedToGetCategories();
                                }

                                Log.e("RetrofitFailure ", ""+t.getMessage());
                            }
                        });
                    }
                    else {

                        connectivity.noActiveConnection();
                    }
                }
            });
        }
        else {

            connectivity.notConnected();
        }
    }


    public void getAllSubCategory() {

        Call<JsonObject> call = service.allSubCategory();

        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.isSuccessful())
                {
                    Log.e("All Sub Category ", new Gson().toJson(response.body()));

                    try {

                        Boolean success = response.body().get("success").getAsBoolean();

                        if(success) {

                            List<SubCategory> subCategoryList = new ArrayList<>();
                            JsonArray array = response.body().get("data").getAsJsonArray();

                            for(int i=0; i<array.size(); i++) {

                                SubCategory subCategory = gson.fromJson(array.get(i).toString(), SubCategory.class);
                                subCategoryList.add(subCategory);
                            }

                            Log.d("Length ", "" +subCategoryList.size());
                        }
                        else {


                        }
                    }
                    catch (Exception e) {

                        e.printStackTrace();
                    }
                }
                else
                {
                    Log.e("RetrofitResponseError ", ""+response.raw());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                if(t instanceof SocketTimeoutException) {

                }
                else {


                }

                Log.e("RetrofitFailure ", ""+t.getMessage());
            }
        });
    }


    public void getAllSubSubCategory() {

        Call<JsonObject> call = service.allSubSubCategory();

        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.isSuccessful())
                {
                    Log.e("All Sub-Sub Category ", new Gson().toJson(response.body()));

                    try {

                        Boolean success = response.body().get("success").getAsBoolean();

                        if(success) {

                            List<SubSubCategory> subSubCategoryList = new ArrayList<>();
                            JsonArray array = response.body().get("data").getAsJsonArray();

                            for(int i=0; i<array.size(); i++) {

                                SubSubCategory SubSubCategory = gson.fromJson(array.get(i).toString(), SubSubCategory.class);
                                subSubCategoryList.add(SubSubCategory);
                            }

                            Log.d("Length ", "" +subSubCategoryList.size());
                        }
                        else {


                        }
                    }
                    catch (Exception e) {

                        e.printStackTrace();
                    }
                }
                else
                {
                    Log.e("RetrofitResponseError ", ""+response.raw());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                if(t instanceof SocketTimeoutException) {

                }
                else {


                }

                Log.e("RetrofitFailure ", ""+t.getMessage());
            }
        });
    }


    public void getAllBrand() {

        if(NetworkUtils.isConnected(context)) {

            new InternetCheck(new InternetCheck.Consumer() {
                @Override
                public void accept(Boolean active) {

                    if(active) {

                        Call<JsonObject> call = service.allBrand();

                        call.enqueue(new Callback<JsonObject>() {

                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                if(response.isSuccessful())
                                {
                                    Log.e("All Brand ", new Gson().toJson(response.body()));

                                    try {

                                        Boolean success = response.body().get("success").getAsBoolean();

                                        if(success) {

                                            List<Brand> brandList = new ArrayList<>();
                                            JsonArray array = response.body().get("data").getAsJsonArray();

                                            for(int i=0; i<array.size(); i++) {

                                                Brand brand = gson.fromJson(array.get(i).toString(), Brand.class);
                                                brandList.add(brand);
                                            }

                                            brandContract.showAll(brandList);
                                        }
                                        else {

                                            brandContract.failedToGetBrands();
                                        }
                                    }
                                    catch (Exception e) {

                                        e.printStackTrace();
                                        brandContract.failedToGetBrands();
                                    }
                                }
                                else
                                {
                                    Log.e("RetrofitResponseError ", ""+response.raw());
                                    brandContract.failedToGetBrands();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                if(t instanceof SocketTimeoutException) {

                                    connectivity.onConnectionTimeOut();
                                }
                                else {

                                    brandContract.failedToGetBrands();
                                }

                                Log.e("RetrofitFailure ", ""+t.getMessage());
                            }
                        });
                    }
                    else {

                        connectivity.noActiveConnection();
                    }
                }
            });
        }
        else {

            connectivity.notConnected();
        }
    }


    public void getAllShops() {

        if(NetworkUtils.isConnected(context)) {

            new InternetCheck(new InternetCheck.Consumer() {
                @Override
                public void accept(Boolean active) {

                    if(active) {

                        Call<JsonObject> call = service.allShop();

                        call.enqueue(new Callback<JsonObject>() {

                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                if(response.isSuccessful())
                                {
                                    Log.e("All Shop ", new Gson().toJson(response.body()));

                                    try {

                                        Boolean success = response.body().get("success").getAsBoolean();

                                        if(success) {

                                            List<Shop> shopList = new ArrayList<>();
                                            JsonArray array = response.body().get("data").getAsJsonArray();

                                            for(int i=0; i<array.size(); i++) {

                                                Shop shop = gson.fromJson(array.get(i).toString(), Shop.class);
                                                shopList.add(shop);
                                            }

                                            shopContract.showAll(shopList);
                                        }
                                        else {

                                            shopContract.failedToGetShops();
                                        }
                                    }
                                    catch (Exception e) {

                                        e.printStackTrace();
                                        shopContract.failedToGetShops();
                                    }
                                }
                                else
                                {
                                    Log.e("RetrofitResponseError ", ""+response.raw());
                                    shopContract.failedToGetShops();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                if(t instanceof SocketTimeoutException) {

                                    connectivity.onConnectionTimeOut();
                                }
                                else {

                                    shopContract.failedToGetShops();
                                }

                                Log.e("RetrofitFailure ", ""+t.getMessage());
                            }
                        });
                    }
                    else {

                        connectivity.noActiveConnection();
                    }
                }
            });
        }
        else {

            connectivity.notConnected();
        }
    }


    public void getProductByCategory(final String categoryId) {

        if(NetworkUtils.isConnected(context)) {

            new InternetCheck(new InternetCheck.Consumer() {
                @Override
                public void accept(Boolean active) {

                    if(active) {

                        Call<JsonObject> call = service.productByCategory(categoryId);

                        call.enqueue(new Callback<JsonObject>() {

                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                if(response.isSuccessful())
                                {
                                    Log.e("Product By Category ", new Gson().toJson(response.body()));

                                    try {

                                        Boolean success = response.body().get("success").getAsBoolean();

                                        if(success) {

                                            CategoryProductData categoryProductData = gson.fromJson(response.body().toString(), CategoryProductData.class);
                                            productContract.showAllByCategory(categoryProductData);
                                        }
                                        else {

                                            productContract.failedToGetData();
                                        }
                                    }
                                    catch (Exception e) {

                                        e.printStackTrace();
                                        productContract.failedToGetData();
                                    }
                                }
                                else
                                {
                                    Log.e("RetrofitResponseError ", ""+response.raw());
                                    productContract.failedToGetData();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                if(t instanceof SocketTimeoutException) {

                                    connectivity.onConnectionTimeOut();
                                }
                                else {

                                    productContract.failedToGetData();
                                }

                                Log.e("RetrofitFailure ", ""+t.getMessage());
                            }
                        });
                    }
                    else {

                        connectivity.noActiveConnection();
                    }
                }
            });
        }
        else {

            connectivity.notConnected();
        }
    }


    public void getProductBySubCategory(final String subCategoryId) {

        if(NetworkUtils.isConnected(context)) {

            new InternetCheck(new InternetCheck.Consumer() {
                @Override
                public void accept(Boolean active) {

                    if(active) {

                        Call<JsonObject> call = service.productBySubCategory(subCategoryId);

                        call.enqueue(new Callback<JsonObject>() {

                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                if(response.isSuccessful())
                                {
                                    Log.e("Product By SubCategory ", new Gson().toJson(response.body()));

                                    try {

                                        Boolean success = response.body().get("success").getAsBoolean();

                                        if(success) {

                                            SubCategoryProductData subCategoryProductData = gson.fromJson(response.body().toString(), SubCategoryProductData.class);
                                            productContract.showAllBySubCategory(subCategoryProductData);
                                        }
                                        else {

                                            productContract.failedToGetData();
                                        }
                                    }
                                    catch (Exception e) {

                                        e.printStackTrace();
                                        productContract.failedToGetData();
                                    }
                                }
                                else
                                {
                                    Log.e("RetrofitResponseError ", ""+response.raw());
                                    productContract.failedToGetData();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                if(t instanceof SocketTimeoutException) {

                                    connectivity.onConnectionTimeOut();
                                }
                                else {

                                    productContract.failedToGetData();
                                }

                                Log.e("RetrofitFailure ", ""+t.getMessage());
                            }
                        });
                    }
                    else {

                        connectivity.noActiveConnection();
                    }
                }
            });
        }
        else {

            connectivity.notConnected();
        }
    }


    public void getProductBySubSubCategory(String subSubCategoryId) {

        Call<JsonObject> call = service.productBySubSubCategory(subSubCategoryId);

        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.isSuccessful())
                {
                    Log.e("Product By SubSubCat ", new Gson().toJson(response.body()));

                    try {

                        Boolean success = response.body().get("success").getAsBoolean();

                        if(success) {

                            SubSubCategoryProductData subSubCategoryProductData = gson.fromJson(response.body().toString(), SubSubCategoryProductData.class);

                            Log.d("Length ", "" +subSubCategoryProductData.getData().getProducts().size());
                        }
                        else {


                        }
                    }
                    catch (Exception e) {

                        e.printStackTrace();
                    }
                }
                else
                {
                    Log.e("RetrofitResponseError ", ""+response.raw());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                if(t instanceof SocketTimeoutException) {

                }
                else {


                }

                Log.e("RetrofitFailure ", ""+t.getMessage());
            }
        });
    }


    public void getProductByBrand(final String brandId) {

        if(NetworkUtils.isConnected(context)) {

            new InternetCheck(new InternetCheck.Consumer() {
                @Override
                public void accept(Boolean active) {

                    if(active) {

                        Call<JsonObject> call = service.productByBrand(brandId);

                        call.enqueue(new Callback<JsonObject>() {

                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                if(response.isSuccessful())
                                {
                                    Log.e("Product By Brand ", new Gson().toJson(response.body()));

                                    try {

                                        Boolean success = response.body().get("success").getAsBoolean();

                                        if(success) {

                                            BrandProductData brandProductData = gson.fromJson(response.body().toString(), BrandProductData.class);
                                            productContract.showAllByBrand(brandProductData);
                                        }
                                        else {

                                            productContract.failedToGetData();
                                        }
                                    }
                                    catch (Exception e) {

                                        e.printStackTrace();
                                        productContract.failedToGetData();
                                    }
                                }
                                else
                                {
                                    Log.e("RetrofitResponseError ", ""+response.raw());
                                    productContract.failedToGetData();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                if(t instanceof SocketTimeoutException) {

                                    connectivity.onConnectionTimeOut();
                                }
                                else {

                                    productContract.failedToGetData();
                                }

                                Log.e("RetrofitFailure ", ""+t.getMessage());
                            }
                        });
                    }
                    else {

                        connectivity.noActiveConnection();
                    }
                }
            });
        }
        else {

            connectivity.notConnected();
        }
    }


    public void getProductByShop(final String shopId) {

        if(NetworkUtils.isConnected(context)) {

            new InternetCheck(new InternetCheck.Consumer() {
                @Override
                public void accept(Boolean active) {

                    if(active) {

                        Call<JsonObject> call = service.productByShop(shopId);

                        call.enqueue(new Callback<JsonObject>() {

                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                if(response.isSuccessful())
                                {
                                    Log.e("SimilarProduct By Shop ", new Gson().toJson(response.body()));

                                    try {

                                        Boolean success = response.body().get("success").getAsBoolean();

                                        if(success) {

                                            ShopProductData shopProductData = gson.fromJson(response.body().toString(), ShopProductData.class);
                                            productContract.showAllByShop(shopProductData);
                                        }
                                        else {

                                            productContract.failedToGetData();
                                        }
                                    }
                                    catch (Exception e) {

                                        e.printStackTrace();
                                        productContract.failedToGetData();
                                    }
                                }
                                else
                                {
                                    Log.e("RetrofitResponseError ", ""+response.raw());
                                    productContract.failedToGetData();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                if(t instanceof SocketTimeoutException) {

                                    connectivity.onConnectionTimeOut();
                                }
                                else {

                                    productContract.failedToGetData();
                                }

                                Log.e("RetrofitFailure ", ""+t.getMessage());
                            }
                        });
                    }
                    else {

                        connectivity.noActiveConnection();
                    }
                }
            });
        }
        else {

            connectivity.notConnected();
        }
    }


    public void search(final String pattern) {

        ProgressDialog.show(context);

        if(NetworkUtils.isConnected(context)) {

            new InternetCheck(new InternetCheck.Consumer() {
                @Override
                public void accept(Boolean active) {

                    if(active) {

                        Call<JsonObject> call = service.search(pattern);

                        call.enqueue(new Callback<JsonObject>() {

                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                ProgressDialog.dismiss();

                                if(response.isSuccessful())
                                {
                                    Log.e("Search ", new Gson().toJson(response.body()));

                                    try {

                                        Boolean success = response.body().get("success").getAsBoolean();

                                        if(success) {

                                            SearchData searchData = gson.fromJson(response.body().toString(), SearchData.class);
                                            searchContract.showData(searchData);
                                        }
                                        else {

                                            searchContract.onFailure();
                                        }
                                    }
                                    catch (Exception e) {

                                        e.printStackTrace();
                                        searchContract.onFailure();
                                    }
                                }
                                else
                                {
                                    Log.e("RetrofitResponseError ", ""+response.raw());
                                    searchContract.onFailure();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                ProgressDialog.dismiss();

                                if(t instanceof SocketTimeoutException) {

                                    connectivity.onConnectionTimeOut();
                                }
                                else {

                                    searchContract.onFailure();
                                }

                                Log.e("RetrofitFailure ", ""+t.getMessage());
                            }
                        });
                    }
                    else {

                        ProgressDialog.dismiss();
                        connectivity.noActiveConnection();
                    }
                }
            });
        }
        else {

            ProgressDialog.dismiss();
            connectivity.notConnected();
        }
    }


    public void getProductDetails(final String productId) {

        ProgressDialog.show(context);

        if(NetworkUtils.isConnected(context)) {

            new InternetCheck(new InternetCheck.Consumer() {
                @Override
                public void accept(Boolean active) {

                    if(active) {

                        Call<JsonObject> call = service.productDetails(productId);

                        call.enqueue(new Callback<JsonObject>() {

                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                ProgressDialog.dismiss();

                                if(response.isSuccessful())
                                {
                                    Log.e("Product Details ", new Gson().toJson(response.body()));

                                    try {

                                        Boolean success = response.body().get("success").getAsBoolean();

                                        if(success) {

                                            Product product = gson.fromJson(response.body().get("data").toString(), Product.class);

                                            List<Product> similarProductList = new ArrayList<>();
                                            JsonArray array = response.body().get("similerProducts").getAsJsonArray();

                                            for(int i=0; i<array.size(); i++) {

                                                Product similar = gson.fromJson(array.get(i).toString(), Product.class);
                                                similarProductList.add(similar);
                                            }

                                            productContract.showProductDetails(product, similarProductList);
                                        }
                                        else {

                                            productContract.failedToGetData();
                                        }
                                    }
                                    catch (Exception e) {

                                        e.printStackTrace();
                                        productContract.failedToGetData();
                                    }
                                }
                                else
                                {
                                    Log.e("RetrofitResponseError ", ""+response.raw());
                                    productContract.failedToGetData();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                ProgressDialog.dismiss();

                                if(t instanceof SocketTimeoutException) {

                                    connectivity.onConnectionTimeOut();
                                }
                                else {

                                    productContract.failedToGetData();
                                }

                                Log.e("RetrofitFailure ", ""+t.getMessage());
                            }
                        });
                    }
                    else {

                        ProgressDialog.dismiss();
                        connectivity.noActiveConnection();
                    }
                }
            });
        }
        else {

            ProgressDialog.dismiss();
            connectivity.notConnected();
        }
    }
}
