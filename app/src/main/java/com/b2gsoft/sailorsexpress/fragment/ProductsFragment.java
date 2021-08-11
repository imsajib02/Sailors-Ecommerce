package com.b2gsoft.sailorsexpress.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.b2gsoft.sailorsexpress.R;
import com.b2gsoft.sailorsexpress.adapter.BrandByAdapter;
import com.b2gsoft.sailorsexpress.adapter.CategoryByAdapter;
import com.b2gsoft.sailorsexpress.adapter.PriceByAdapter;
import com.b2gsoft.sailorsexpress.adapter.ProductAdapter;
import com.b2gsoft.sailorsexpress.adapter.SortByAdapter;
import com.b2gsoft.sailorsexpress.adapter.SubCategoryByAdapter;
import com.b2gsoft.sailorsexpress.contract.Connectivity;
import com.b2gsoft.sailorsexpress.contract.ItemClickListener;
import com.b2gsoft.sailorsexpress.contract.ProductContract;
import com.b2gsoft.sailorsexpress.model.Brand;
import com.b2gsoft.sailorsexpress.model.BrandProductData;
import com.b2gsoft.sailorsexpress.model.Category;
import com.b2gsoft.sailorsexpress.model.CategoryProductData;
import com.b2gsoft.sailorsexpress.model.Product;
import com.b2gsoft.sailorsexpress.model.Shop;
import com.b2gsoft.sailorsexpress.model.ShopProductData;
import com.b2gsoft.sailorsexpress.model.Size;
import com.b2gsoft.sailorsexpress.model.SubCategory;
import com.b2gsoft.sailorsexpress.model.SubCategoryProductData;
import com.b2gsoft.sailorsexpress.presenter.DataPresenter;
import com.b2gsoft.sailorsexpress.utils.Constants;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.nshmura.snappysmoothscroller.SnappyGridLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductsFragment extends Fragment implements ProductContract, Connectivity, ItemClickListener {

    private Context context;
    private DataPresenter presenter;

    private RelativeLayout rootView, backView;
    private ImageView connectivityImage;
    private TextView title, connectivityMessage, tryAgain;
    private LinearLayout filterLayout;
    private ShimmerRecyclerView recyclerProducts;
    private LottieAnimationView progress;
    private View connectivityView;

    private View filterView;
    private ImageView close;
    private TextView clearFilter, showResult, txtSortBy, txtBrand, txtCategory, txtSubCategory, txtPrice;
    private RecyclerView sortByRecycler, brandRecycler, categoryRecycler, subCategoryRecycler, priceRecycler;

    private ProductContract productContract;
    private Connectivity connectivity;
    private ItemClickListener clickListener;

    private ProductAdapter productAdapter;

    private Category category;
    private SubCategory subCategory;
    private Brand brand;
    private Shop shop;

    private SubCategoryProductData subCategoryProductData;
    private CategoryProductData categoryProductData;
    private BrandProductData brandProductData;
    private ShopProductData shopProductData;

    private SortByAdapter sortByAdapter;
    private BrandByAdapter brandByAdapter;
    private CategoryByAdapter categoryByAdapter;
    private SubCategoryByAdapter subCategoryByAdapter;
    private PriceByAdapter priceByAdapter;

    private int sortByPosition = -1, priceByPosition = -1;
    private Brand selectedBrand;
    private Category selectedCategory;
    private SubCategory selectedSubCategory;

    private Animation animation;

    private List<Product> productList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_products, container, false);

        this.context = view.getContext();
        animation = AnimationUtils.loadAnimation(context, R.anim.bounce);

        productContract = this;
        connectivity = this;
        clickListener = this;

        presenter = new DataPresenter(context, connectivity, productContract);

        rootView = (RelativeLayout) view.findViewById(R.id.root_view);
        progress = (LottieAnimationView) view.findViewById(R.id.progress);
        backView = (RelativeLayout) view.findViewById(R.id.lay_back);
        connectivityView = (View) view.findViewById(R.id.container_not_connected);
        filterLayout = (LinearLayout) view.findViewById(R.id.lay_filter);

        filterView = (View) view.findViewById(R.id.container_filter);
        close = (ImageView) filterView.findViewById(R.id.ic_close);
        clearFilter = (TextView) filterView.findViewById(R.id.txt_clear);
        showResult = (TextView) filterView.findViewById(R.id.txt_show_result);

        txtSortBy = (TextView) filterView.findViewById(R.id.txt_sort_by);
        txtBrand = (TextView) filterView.findViewById(R.id.txt_brand);
        txtCategory = (TextView) filterView.findViewById(R.id.txt_category);
        txtSubCategory = (TextView) filterView.findViewById(R.id.txt_sub_category);
        txtPrice = (TextView) filterView.findViewById(R.id.txt_price);

        sortByRecycler = (RecyclerView) filterView.findViewById(R.id.recycler_sort_by);
        brandRecycler = (RecyclerView) filterView.findViewById(R.id.recycler_brand);
        categoryRecycler = (RecyclerView) filterView.findViewById(R.id.recycler_category);
        subCategoryRecycler = (RecyclerView) filterView.findViewById(R.id.recycler_sub_category);
        priceRecycler = (RecyclerView) filterView.findViewById(R.id.recycler_price);

        recyclerProducts = (ShimmerRecyclerView) view.findViewById(R.id.recycler_products);

        title = (TextView) view.findViewById(R.id.txt_title);

        connectivityImage = (ImageView) connectivityView.findViewById(R.id.img_error);
        connectivityMessage = (TextView) connectivityView.findViewById(R.id.txt_error);
        tryAgain = (TextView) connectivityView.findViewById(R.id.try_again);

        Bundle args = getArguments();
        init(args);

        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();
            }
        });

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(animation);

                recyclerProducts.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                connectivityView.setVisibility(View.GONE);

                if(category != null && category.getId() != null) {

                    presenter.getProductByCategory(category.getId());
                }

                if(subCategory != null && subCategory.getId() != null) {

                    presenter.getProductBySubCategory(subCategory.getId());
                }

                if(brand != null && brand.getId() != null) {

                    presenter.getProductByBrand(brand.getId());
                }

                if(shop != null && shop.getId() != null) {

                    presenter.getProductByShop(shop.getId());
                }
            }
        });

        filterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setSortBy();
                setByBrand();
                setByCategory();
                setBySubCategory();
                setByPrice();

                filterView.setVisibility(View.VISIBLE);
                filterLayout.setVisibility(View.INVISIBLE);
                backView.setVisibility(View.INVISIBLE);

                TranslateAnimation animate = new TranslateAnimation(0, 0, filterView.getHeight(), 0);
                animate.setDuration(500);
                animate.setFillAfter(true);
                filterView.startAnimation(animate);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                closeFilterView();
            }
        });

        clearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                closeFilterView();

                sortByPosition = -1;
                priceByPosition = -1;
                selectedBrand = null;
                selectedCategory = null;
                selectedSubCategory = null;

                if(productList != null) {

                    showProductList(productList);
                }
            }
        });

        showResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                applyFilter();
                closeFilterView();
            }
        });

        return view;
    }


    private void applyFilter() {

        if(productList != null) {

            List<Product> firstFilterList = new ArrayList<>();
            List<Product> secondFilterList = new ArrayList<>();

            firstFilterList.addAll(productList);

            if(sortByPosition != -1) {

                if(sortByPosition != 0) {

                    Collections.sort(firstFilterList);

                    if(sortByPosition == 2) {

                        Collections.reverse(firstFilterList);
                    }
                }
            }

            if(selectedBrand != null) {

                for(int i=0; i<firstFilterList.size(); i++) {

                    if(firstFilterList.get(i).getBrand() != null && TextUtils.equals(firstFilterList.get(i).getBrand().getId(), selectedBrand.getId())) {

                        secondFilterList.add(firstFilterList.get(i));
                    }
                }
            }
            else {

                secondFilterList.addAll(firstFilterList);
            }

            firstFilterList.clear();

            if(selectedCategory != null) {

                for(int i=0; i<secondFilterList.size(); i++) {

                    if(secondFilterList.get(i).getCategory() != null && TextUtils.equals(secondFilterList.get(i).getCategory().getId(), selectedCategory.getId())) {

                        firstFilterList.add(secondFilterList.get(i));
                    }
                }
            }
            else {

                firstFilterList.addAll(secondFilterList);
            }

            secondFilterList.clear();

            if(selectedSubCategory != null) {

                for(int i=0; i<firstFilterList.size(); i++) {

                    if(firstFilterList.get(i).getSubCategory() != null && TextUtils.equals(firstFilterList.get(i).getSubCategory().getId(), selectedSubCategory.getId())) {

                        secondFilterList.add(firstFilterList.get(i));
                    }
                }
            }
            else {

                secondFilterList.addAll(firstFilterList);
            }

            firstFilterList.clear();

            if(priceByPosition != -1) {

                double minPrice = 0.0;
                double maxPrice = 0.0;

                switch (priceByPosition) {

                    case 0:
                        minPrice = 0.0;
                        maxPrice = 1000.0;
                        break;

                    case 1:
                        minPrice = 1000.0;
                        maxPrice = 10000.0;
                        break;

                    case 2:
                        minPrice = 10000.0;
                        maxPrice = 50000.0;
                        break;

                    case 3:
                        minPrice = 50000.0;
                        maxPrice = 250000.0;
                        break;

                    case 4:
                        minPrice = 250000.0;
                        maxPrice = 5000000.0;
                        break;
                }

                for(int i=0; i<secondFilterList.size(); i++) {

                    if(secondFilterList.get(i).getCurrentPrice() >= minPrice && secondFilterList.get(i).getCurrentPrice() <= maxPrice) {

                        firstFilterList.add(secondFilterList.get(i));
                    }
                }
            }
            else {

                firstFilterList.addAll(secondFilterList);
            }

            showProductList(firstFilterList);
        }
    }


    private void setSortBy() {

        List<String> sortByList = new ArrayList<>();

        sortByList.add(getString(R.string.relevance));
        sortByList.add(getString(R.string.low_2_high));
        sortByList.add(getString(R.string.high_2_low));

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        sortByRecycler.setLayoutManager(layoutManager);

        sortByAdapter = new SortByAdapter(context, sortByList, sortByPosition, clickListener);
        sortByRecycler.setAdapter(sortByAdapter);
    }


    private void setByBrand() {

        List<Brand> brandList = new ArrayList<>();

        for(int i=0; i<productList.size(); i++) {

            if(productList.get(i).getBrand() != null && productList.get(i).getBrand().getName() != null) {

                boolean matchFound = false;

                for(int j=0; j<brandList.size(); j++) {

                    if(TextUtils.equals(productList.get(i).getBrand().getId(), brandList.get(j).getId())) {

                        matchFound = true;
                        break;
                    }
                }

                if(!matchFound) {

                    brandList.add(productList.get(i).getBrand());
                }
            }
        }

        if(brandList.size() > 0) {

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            brandRecycler.setLayoutManager(layoutManager);

            brandByAdapter = new BrandByAdapter(context, brandList, selectedBrand, clickListener);
            brandRecycler.setAdapter(brandByAdapter);
        }
        else {

            txtBrand.setVisibility(View.GONE);
            brandRecycler.setVisibility(View.GONE);
        }
    }


    private void setByCategory() {

        List<Category> categoryList = new ArrayList<>();

        for(int i=0; i<productList.size(); i++) {

            if(productList.get(i).getCategory() != null && productList.get(i).getCategory().getName() != null) {

                boolean matchFound = false;

                for(int j=0; j<categoryList.size(); j++) {

                    if(TextUtils.equals(productList.get(i).getCategory().getId(), categoryList.get(j).getId())) {

                        matchFound = true;
                        break;
                    }
                }

                if(!matchFound) {

                    categoryList.add(productList.get(i).getCategory());
                }
            }
        }

        if(categoryList.size() > 0) {

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            categoryRecycler.setLayoutManager(layoutManager);

            categoryByAdapter = new CategoryByAdapter(context, categoryList, selectedCategory, clickListener);
            categoryRecycler.setAdapter(categoryByAdapter);
        }
        else {

            txtCategory.setVisibility(View.GONE);
            categoryRecycler.setVisibility(View.GONE);
        }
    }


    private void setBySubCategory() {

        List<SubCategory> subCategoryList = new ArrayList<>();

        for(int i=0; i<productList.size(); i++) {

            if(productList.get(i).getSubCategory() != null && productList.get(i).getSubCategory().getName() != null) {

                boolean matchFound = false;

                for(int j=0; j<subCategoryList.size(); j++) {

                    if(TextUtils.equals(productList.get(i).getSubCategory().getId(), subCategoryList.get(j).getId())) {

                        matchFound = true;
                        break;
                    }
                }

                if(!matchFound) {

                    subCategoryList.add(productList.get(i).getSubCategory());
                }
            }
        }

        if(subCategoryList.size() > 0) {

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            subCategoryRecycler.setLayoutManager(layoutManager);

            subCategoryByAdapter = new SubCategoryByAdapter(context, subCategoryList, selectedSubCategory, clickListener);
            subCategoryRecycler.setAdapter(subCategoryByAdapter);
        }
        else {

            txtSubCategory.setVisibility(View.GONE);
            subCategoryRecycler.setVisibility(View.GONE);
        }
    }


    private void setByPrice() {

        List<String> priceList = new ArrayList<>();

        priceList.add(getString(R.string.zero_to_1k));
        priceList.add(getString(R.string.from_1k_to_10k));
        priceList.add(getString(R.string.from_10k_to_50k));
        priceList.add(getString(R.string.from_50k_to_250k));
        priceList.add(getString(R.string.from_250k_to_5m));

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        priceRecycler.setLayoutManager(layoutManager);

        priceByAdapter = new PriceByAdapter(context, priceList, priceByPosition, clickListener);
        priceRecycler.setAdapter(priceByAdapter);
    }


    private void closeFilterView() {

        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, filterView.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(false);
        filterView.startAnimation(animate);
        filterView.setVisibility(View.INVISIBLE);
        filterLayout.setVisibility(View.VISIBLE);
        backView.setVisibility(View.VISIBLE);
    }


    private void init(Bundle args) {

        if(args != null) {

            String type = args.getString(Constants.productBy);

            switch(type) {

                case Constants.productByCategory:
                    initByCategory(args);
                    break;

                case Constants.productBySubCategory:
                    initBySubCategory(args);
                    break;

                case Constants.productByBrand:
                    initByBrand(args);
                    break;

                case Constants.productByShop:
                    initByShop(args);
                    break;

                default:
                    break;
            }
        }
    }


    private void initByCategory(Bundle args) {

        category = (Category) args.getSerializable(Constants.modelType);

        if(category != null && category.getId() != null) {

            title.setText(category.getName());
            presenter.getProductByCategory(category.getId());
        }
    }


    private void initBySubCategory(Bundle args) {

        subCategory = (SubCategory) args.getSerializable(Constants.modelType);

        if(subCategory != null && subCategory.getId() != null) {

            title.setText(subCategory.getName());
            presenter.getProductBySubCategory(subCategory.getId());
        }
    }


    private void initByBrand(Bundle args) {

        brand = (Brand) args.getSerializable(Constants.modelType);

        if(brand != null && brand.getId() != null) {

            title.setText(brand.getName());
            presenter.getProductByBrand(brand.getId());
        }
    }


    private void initByShop(Bundle args) {

        shop = (Shop) args.getSerializable(Constants.modelType);

        if(shop != null && shop.getId() != null) {

            title.setText(shop.getName());
            presenter.getProductByShop(shop.getId());
        }
    }


    @Override
    public void notConnected() {

        connectivityImage.setImageResource(R.drawable.not_connected);
        connectivityMessage.setText(R.string.you_are_not_connected);

        onFailure();
    }


    @Override
    public void noActiveConnection() {

        connectivityImage.setImageResource(R.drawable.connection_inactive_2);
        connectivityMessage.setText(R.string.you_connection_inactive);

        onFailure();
    }


    @Override
    public void onConnectionTimeOut() {

        connectivityImage.setImageResource(R.drawable.time_out);
        connectivityMessage.setText(R.string.timed_out);

        onFailure();
    }


    @Override
    public void showAllBySubCategory(SubCategoryProductData subCategoryProductData) {

        this.subCategoryProductData = subCategoryProductData;
        onSuccess(this.subCategoryProductData.getData().getProducts());
    }


    @Override
    public void showAllByCategory(CategoryProductData categoryProductData) {

        this.categoryProductData = categoryProductData;
        onSuccess(this.categoryProductData.getData().getProducts());
    }


    @Override
    public void showAllByBrand(BrandProductData brandProductData) {

        this.brandProductData = brandProductData;
        onSuccess(this.brandProductData.getData().getProducts());
    }


    @Override
    public void showAllByShop(ShopProductData shopProductData) {

        this.shopProductData = shopProductData;
        onSuccess(this.shopProductData.getData().getProducts());
    }


    private void onSuccess(List<Product> productList) {

        for(int i=0; i<productList.size(); i++) {

            try {

                if(productList.get(i).getDiscount() != null && productList.get(i).getDiscount().getAmount() != null
                        && productList.get(i).getDiscount().getAmount().getAsDouble() != 0.0) {

                    if(productList.get(i).getDiscount().getType() == Constants.Discount_IN_AMOUNT) {

                        productList.get(i).setCurrentPrice(productList.get(i).getPrice().getAsDouble() - productList.get(i).getDiscount().getAmount().getAsDouble());
                    }
                    else {

                        productList.get(i).setCurrentPrice(productList.get(i).getPrice().getAsDouble() -
                                ((productList.get(i).getPrice().getAsDouble() * productList.get(i).getDiscount().getAmount().getAsDouble()) / 100));
                    }
                }
                else {

                    productList.get(i).setCurrentPrice(productList.get(i).getPrice().getAsDouble());
                }
            }
            catch (Exception e) {

                e.printStackTrace();
                productList.get(i).setCurrentPrice(productList.get(i).getPrice().getAsDouble());
            }
        }

        this.productList = productList;

        recyclerProducts.hideShimmerAdapter();

        progress.setVisibility(View.GONE);
        recyclerProducts.setVisibility(View.VISIBLE);
        filterLayout.setVisibility(View.VISIBLE);

        showProductList(this.productList);
    }


    private void showProductList(List<Product> productList) {

        SnappyGridLayoutManager snappyGridLayoutManager = new SnappyGridLayoutManager(context, 2);
        recyclerProducts.setLayoutManager(snappyGridLayoutManager);

        productAdapter = new ProductAdapter(context, productList, clickListener);
        recyclerProducts.setAdapter(productAdapter);
    }


    @Override
    public void failedToGetData() {

        connectivityImage.setImageResource(R.drawable.failure);
        connectivityMessage.setText(R.string.could_not_load_data);

        onFailure();
    }


    @Override
    public void showProductDetails(Product product, List<Product> similarProductList) {

    }


    @Override
    public void onCategoryClicked(Category category) {

        selectedCategory = category;
    }


    @Override
    public void onSubCategoryClicked(SubCategory subCategory) {

        selectedSubCategory = subCategory;
    }


    @Override
    public void onProductClicked(Product product) {

        Bundle bundle = new Bundle();
        bundle.putString(Constants.productId, product.getId());

        Fragment fragment = new ProductDetailsFragment();
        fragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                .add(R.id.frame_layout, fragment)
                .addToBackStack(Constants.productsFragment)
                .commit();
    }


    @Override
    public void onBrandClicked(Brand brand) {

        selectedBrand = brand;
    }


    @Override
    public void onShopClicked(Shop shop) {

    }


    @Override
    public void onSizeClicked(Size size) {

    }


    @Override
    public void onSortBySelected(int position) {

        sortByPosition = position;
    }


    @Override
    public void onPriceBySelected(int position) {

        priceByPosition = position;
    }


    private void onFailure() {

        recyclerProducts.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        connectivityView.setVisibility(View.VISIBLE);
    }
}
