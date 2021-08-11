package com.b2gsoft.sailorsexpress.fragment;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.b2gsoft.sailorsexpress.R;
import com.b2gsoft.sailorsexpress.adapter.ImageAdapter;
import com.b2gsoft.sailorsexpress.adapter.ProductAdapter;
import com.b2gsoft.sailorsexpress.adapter.SizeAdapter;
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
import com.b2gsoft.sailorsexpress.network.RetrofitInstance;
import com.b2gsoft.sailorsexpress.presenter.DataPresenter;
import com.b2gsoft.sailorsexpress.utils.Constants;
import com.b2gsoft.sailorsexpress.utils.DBHelper;
import com.b2gsoft.sailorsexpress.utils.DecimalFormatter;
import com.b2gsoft.sailorsexpress.utils.MyNestedScrollView;
import com.b2gsoft.sailorsexpress.utils.MySnack;
import com.bumptech.glide.Glide;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.nshmura.snappysmoothscroller.SnappyGridLayoutManager;
import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;

import java.util.List;

import me.relex.circleindicator.CircleIndicator2;

import static com.b2gsoft.sailorsexpress.view.MainActivity.txtCartItem;

public class ProductDetailsFragment extends Fragment implements ProductContract, Connectivity, ItemClickListener {

    private Context context;
    private DataPresenter dataPresenter;

    private ProductContract productContract;
    private Connectivity connectivity;
    private ItemClickListener clickListener;

    private RelativeLayout header, indicatorContainer, rootView, productImageContainer, productDataContainer, priceContainer,
            stockContainer, sizeContainer, quantityContainer, detailsContainer, backContainer;

    private CoordinatorLayout coordinatorLayout;
    private LinearLayout addToCartContainer;
    private ImageView imgBack, brandImage, connectivityImage, line4;
    private View connectivityView;
    private CardView brandDetail;
    private CircleIndicator2 indicator;
    private RecyclerView imageRecycler, sizeRecycler;
    private ShimmerRecyclerView similarProductsRecycler;
    private MyNestedScrollView nestedScrollView;

    private TextView txtProductName, txtDiscount, txtVat, txtPrice, txtOriginalPrice, txtStockSize, txtIncrease, txtDecrease, txtQuantity, addToCart,
            txtDescription, txtSku, txtBrandName, txtCategoryName, txtSubCategoryName, txtFeatured, txtNewArrival, txtTopSelling, txtFreeDelivery, brandName,
            connectivityMessage, tryAgain, similarProductTitle;

    private RetrofitInstance retrofitInstance = new RetrofitInstance();

    private ImageAdapter imageAdapter;
    private SizeAdapter sizeAdapter;
    private ProductAdapter productAdapter;

    private String productId;
    private Product product;
    private List<Product> similarProductList;

    private DBHelper dbHelper;

    private Animation animation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_details, container, false);

        this.context = view.getContext();
        dbHelper = new DBHelper(context);
        animation = AnimationUtils.loadAnimation(context, R.anim.bounce);

        productContract = this;
        connectivity = this;
        clickListener = this;

        dataPresenter = new DataPresenter(context, connectivity, productContract);

        connectivityView = (View) view.findViewById(R.id.container_not_connected);

        connectivityImage = (ImageView) connectivityView.findViewById(R.id.img_error);
        connectivityMessage = (TextView) connectivityView.findViewById(R.id.txt_error);
        tryAgain = (TextView) connectivityView.findViewById(R.id.try_again);

        imgBack = (ImageView) view.findViewById(R.id.ic_close);
        brandImage = (ImageView) view.findViewById(R.id.img_brand);
        line4 = (ImageView) view.findViewById(R.id.img_line_4);

        indicator = (CircleIndicator2) view.findViewById(R.id.indicator);

        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.lay_coordinator);

        imageRecycler = (RecyclerView) view.findViewById(R.id.recycler_image);
        sizeRecycler = (RecyclerView) view.findViewById(R.id.recycler_category);

        similarProductsRecycler = (ShimmerRecyclerView) view.findViewById(R.id.recycler_similar_products);

        nestedScrollView = (MyNestedScrollView) view.findViewById(R.id.nested_view);

        addToCartContainer = (LinearLayout) view.findViewById(R.id.lay_add_cart);

        brandDetail = (CardView) view.findViewById(R.id.lay_brand_detail);

        txtProductName = (TextView) view.findViewById(R.id.txt_product_name);
        txtDiscount = (TextView) view.findViewById(R.id.txt_discount);
        txtVat = (TextView) view.findViewById(R.id.txt_vat);
        txtPrice = (TextView) view.findViewById(R.id.txt_price);
        txtOriginalPrice = (TextView) view.findViewById(R.id.txt_original);
        txtStockSize = (TextView) view.findViewById(R.id.txt_stock_size);
        txtIncrease = (TextView) view.findViewById(R.id.txt_increase);
        txtDecrease = (TextView) view.findViewById(R.id.txt_decrease);
        txtQuantity = (TextView) view.findViewById(R.id.txt_quantity);
        addToCart = (TextView) view.findViewById(R.id.add_to_cart);
        txtDescription = (TextView) view.findViewById(R.id.txt_description);
        txtSku = (TextView) view.findViewById(R.id.txt_sku);
        txtBrandName = (TextView) view.findViewById(R.id.txt_brand_name);
        txtCategoryName = (TextView) view.findViewById(R.id.txt_cat_name);
        txtSubCategoryName = (TextView) view.findViewById(R.id.txt_sub_cat_name);
        txtFeatured = (TextView) view.findViewById(R.id.txt_featured);
        txtNewArrival = (TextView) view.findViewById(R.id.txt_new);
        txtTopSelling = (TextView) view.findViewById(R.id.txt_top_sell);
        txtFreeDelivery = (TextView) view.findViewById(R.id.txt_free_delivery);
        brandName = (TextView) view.findViewById(R.id.txt_brand_name_2);
        similarProductTitle = (TextView) view.findViewById(R.id.txt_similar_products);

        rootView = (RelativeLayout) view.findViewById(R.id.root_view);
        header = (RelativeLayout) view.findViewById(R.id.header);
        indicatorContainer = (RelativeLayout) view.findViewById(R.id.indicator_container);
        productImageContainer = (RelativeLayout) view.findViewById(R.id.image_container);
        productDataContainer = (RelativeLayout) view.findViewById(R.id.product_data_container);
        priceContainer = (RelativeLayout) view.findViewById(R.id.price_container);
        stockContainer = (RelativeLayout) view.findViewById(R.id.lay_stocks);
        sizeContainer = (RelativeLayout) view.findViewById(R.id.lay_size);
        quantityContainer = (RelativeLayout) view.findViewById(R.id.lay_quantity);
        detailsContainer = (RelativeLayout) view.findViewById(R.id.lay_details);
        backContainer = (RelativeLayout) view.findViewById(R.id.lay_back);

        Bundle args = getArguments();
        productId = args.getString(Constants.productId);

        if(productId != null && !productId.isEmpty()) {

            dataPresenter.getProductDetails(productId);
        }

        backContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();
            }
        });

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(animation);

                if(productId != null && !productId.isEmpty()) {

                    coordinatorLayout.setVisibility(View.VISIBLE);
                    connectivityView.setVisibility(View.GONE);

                    dataPresenter.getProductDetails(productId);
                }
            }
        });

        brandDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(product.getBrand() != null && product.getBrand().getId() != null) {

                    onBrandClicked(product.getBrand());
                }
            }
        });

        txtIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(animation);
                increaseQuantity();
            }
        });

        txtDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(animation);
                decreaseQuantity();
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(animation);
                dbHelper.addProduct(product);
                txtCartItem.setText("" + dbHelper.getProductsCount());
                MySnack.showGeneralSnackBar(rootView, getString(R.string.added_to_cart), 1500);
            }
        });

        return view;
    }


    private void increaseQuantity() {

        try {

            if(product.getSize() == null) {

                if(product.getQuantity() < product.getCurrentStock()) {

                    product.setQuantity(product.getQuantity() + 1);
                }
            }
            else {

                if(product.getQuantity() < product.getSize().getStock()) {

                    product.setQuantity(product.getQuantity() + 1);
                }
            }

            txtQuantity.setText("" +product.getQuantity());
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }


    private void decreaseQuantity() {

        if(product.getQuantity() > 1) {

            product.setQuantity(product.getQuantity() - 1);
            txtQuantity.setText("" +product.getQuantity());
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

    }


    @Override
    public void showAllByCategory(CategoryProductData categoryProductData) {

    }


    @Override
    public void showAllByBrand(BrandProductData brandProductData) {

    }


    @Override
    public void showAllByShop(ShopProductData shopProductData) {

    }


    @Override
    public void failedToGetData() {

        connectivityImage.setImageResource(R.drawable.failure);
        connectivityMessage.setText(R.string.could_not_load_data);

        onFailure();
    }


    private void onFailure() {

        coordinatorLayout.setVisibility(View.GONE);
        connectivityView.setVisibility(View.VISIBLE);
    }


    @Override
    public void showProductDetails(Product product, List<Product> similarProductList) {

        this.product = product;
        this.similarProductList = similarProductList;

        this.product.setQuantity(1);

        imageRecycler.setVisibility(View.VISIBLE);
        indicatorContainer.setVisibility(View.VISIBLE);

        final SnappyLinearLayoutManager verticalLayoutManager = new SnappyLinearLayoutManager(context, SnappyLinearLayoutManager.HORIZONTAL, false);
        imageRecycler.setLayoutManager(verticalLayoutManager);

        imageRecycler.setItemAnimator(new DefaultItemAnimator());

        imageAdapter = new ImageAdapter(context, this.product.getImages());
        imageRecycler.setAdapter(imageAdapter);

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(imageRecycler);
        indicator.attachToRecyclerView(imageRecycler, pagerSnapHelper);
        imageAdapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());

        txtProductName.setText(this.product.getName());

        try {

            if(this.product.getDiscount().getAmount() != null && this.product.getDiscount().getAmount().getAsDouble() != 0.0) {

                txtOriginalPrice.setText(getString(R.string.bdt_sign) + DecimalFormatter.formatUpToOnePosition(this.product.getPrice().getAsDouble()));
                txtOriginalPrice.setPaintFlags(txtOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                double discountPrice = 0.0;

                if(this.product.getDiscount().getType() == Constants.Discount_IN_AMOUNT) {

                    discountPrice = this.product.getPrice().getAsDouble() - this.product.getDiscount().getAmount().getAsDouble();
                    txtDiscount.setText("" + DecimalFormatter.formatUpToOnePosition(this.product.getDiscount().getAmount().getAsDouble()));
                }
                else {

                    discountPrice = this.product.getPrice().getAsDouble() - ((this.product.getPrice().getAsDouble() * this.product.getDiscount().getAmount().getAsDouble()) / 100);
                    txtDiscount.setText("" + DecimalFormatter.formatUpToOnePosition(this.product.getDiscount().getAmount().getAsDouble()) + "%");
                }

                txtPrice.setText(getString(R.string.bdt_sign) + DecimalFormatter.formatUpToOnePosition(discountPrice));
                this.product.setCurrentPrice(discountPrice);
            }
            else {

                onNoDiscount();
            }
        }
        catch (Exception e) {

            e.printStackTrace();
            onNoDiscount();
        }

        try {

            if(this.product.getVat() != null && !TextUtils.isEmpty(this.product.getVat().getAsString()) &&
                    !TextUtils.equals(this.product.getVat().getAsString(), "null") && this.product.getVat().getAsDouble() != 0.0) {

                txtVat.setText(getString(R.string.vat_app) + " " + this.product.getVat().getAsString() + "%");
            }
            else {

                txtVat.setVisibility(View.GONE);
            }
        }
        catch (Exception e) {

            e.printStackTrace();
            txtVat.setVisibility(View.GONE);
        }

        try {

            if(this.product.getSizes().size() > 0) {

                onSizeClicked(this.product.getSizes().get(0));
            }
            else {

                onStockOut();
            }
        }
        catch (Exception e) {

            e.printStackTrace();
            onStockOut();
        }

        txtQuantity.setText("" +this.product.getQuantity());

        try {

            if(this.product.getSizes().size() > 0) {

                FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
                layoutManager.setFlexDirection(FlexDirection.ROW);
                layoutManager.setJustifyContent(JustifyContent.FLEX_START);

                sizeRecycler.setLayoutManager(layoutManager);
                sizeAdapter = new SizeAdapter(context, this.product.getSizes(), clickListener);
                sizeRecycler.setAdapter(sizeAdapter);
            }
            else {

                sizeContainer.setVisibility(View.GONE);
            }
        }
        catch (Exception e) {

            e.printStackTrace();
            sizeContainer.setVisibility(View.GONE);
        }

        try {

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                txtDescription.setText(Html.fromHtml(this.product.getDescription(), Html.FROM_HTML_MODE_COMPACT));
            }
            else {

                txtDescription.setText(Html.fromHtml(this.product.getDescription()));
            }
        }
        catch (Exception e) {

            e.printStackTrace();
            txtDescription.setText("");
        }

        try {

            txtSku.setText("" +this.product.getSku());
        }
        catch (Exception e) {

            e.printStackTrace();
            txtSku.setText("");
        }

        try {

            txtBrandName.setText("" +this.product.getBrand().getName());
        }
        catch (Exception e) {

            e.printStackTrace();
            txtBrandName.setText("");
        }

        try {

            txtCategoryName.setText("" +this.product.getCategory().getName());
        }
        catch (Exception e) {

            e.printStackTrace();
            txtCategoryName.setText("");
        }

        try {

            txtSubCategoryName.setText("" +this.product.getSubCategory().getName());
        }
        catch (Exception e) {

            e.printStackTrace();
            txtSubCategoryName.setText("");
        }

        try {

            if(this.product.isFeatured()) {

                txtFeatured.setText(getString(R.string.yes));
            }
            else {

                txtFeatured.setText(getString(R.string.no));
            }
        }
        catch (Exception e) {

            e.printStackTrace();
            txtFeatured.setText("");
        }

        try {

            if(this.product.isNewArrival()) {

                txtNewArrival.setText(getString(R.string.yes));
            }
            else {

                txtNewArrival.setText(getString(R.string.no));
            }
        }
        catch (Exception e) {

            e.printStackTrace();
            txtNewArrival.setText("");
        }

        try {

            if(this.product.isTopSelling()) {

                txtTopSelling.setText(getString(R.string.yes));
            }
            else {

                txtTopSelling.setText(getString(R.string.no));
            }
        }
        catch (Exception e) {

            e.printStackTrace();
            txtTopSelling.setText("");
        }

        try {

            if(this.product.isFreeDelivery()) {

                txtFreeDelivery.setText(getString(R.string.yes));
            }
            else {

                txtFreeDelivery.setText(getString(R.string.no));
            }
        }
        catch (Exception e) {

            e.printStackTrace();
            txtFreeDelivery.setText("");
        }

        try {

            if(this.product.getBrand() != null) {

                Glide.with(context).load(retrofitInstance.BASE_URL + this.product.getBrand().getImage()).into(brandImage);
                brandName.setText("" +this.product.getBrand().getName());
            }
            else {

                brandDetail.setVisibility(View.GONE);
            }
        }
        catch (Exception e) {

            e.printStackTrace();
            brandDetail.setVisibility(View.GONE);
        }

        similarProductsRecycler.hideShimmerAdapter();

        try {

            if(this.similarProductList.size() > 0) {

                SnappyGridLayoutManager snappyGridLayoutManager = new SnappyGridLayoutManager(context, 2);
                similarProductsRecycler.setLayoutManager(snappyGridLayoutManager);

                productAdapter = new ProductAdapter(context, this.similarProductList, clickListener);
                similarProductsRecycler.setAdapter(productAdapter);
            }
            else {

                onNoSimilarProduct();
            }
        }
        catch (Exception e) {

            e.printStackTrace();
            onNoSimilarProduct();
        }
    }


    private void setSizeStock() {

        try {

            if(product.getSize().getStock() > 0) {

                txtStockSize.setText("" +product.getSize().getStock());

                if(product.getQuantity() > product.getSize().getStock()) {

                    product.setQuantity(product.getSize().getStock());
                    txtQuantity.setText("" +product.getQuantity());
                }
            }
            else {

                onStockOut();
            }
        }
        catch (Exception e) {

            e.printStackTrace();
            onStockOut();
        }
    }


    private void onNoSimilarProduct() {

        line4.setVisibility(View.GONE);
        similarProductTitle.setVisibility(View.GONE);
        similarProductsRecycler.setVisibility(View.GONE);
    }


    private void onStockOut() {

        txtIncrease.setEnabled(false);
        txtDecrease.setEnabled(false);
        addToCart.setEnabled(false);

        product.setQuantity(1);
        txtQuantity.setText("" +product.getQuantity());

        txtStockSize.setText(getString(R.string.stock_out));
        txtStockSize.setBackgroundResource(R.drawable.rounded_rectangle_2);
    }


    private void onNoDiscount() {

        txtDiscount.setVisibility(View.GONE);
        txtPrice.setText(getString(R.string.bdt_sign) + DecimalFormatter.formatUpToOnePosition(this.product.getPrice().getAsDouble()));
        this.product.setCurrentPrice(this.product.getPrice().getAsDouble());
        txtOriginalPrice.setVisibility(View.GONE);
    }


    @Override
    public void onCategoryClicked(Category category) {

    }


    @Override
    public void onSubCategoryClicked(SubCategory subCategory) {

    }


    @Override
    public void onProductClicked(Product product) {

        Bundle bundle = new Bundle();
        bundle.putString(Constants.productId, product.getId());

        Fragment fragment = new ProductDetailsFragment();
        fragment.setArguments(bundle);

        showFragment(fragment, product.getId());
    }


    @Override
    public void onBrandClicked(Brand brand) {

        Bundle bundle = new Bundle();
        bundle.putString(Constants.productBy, Constants.productByBrand);
        bundle.putSerializable(Constants.modelType, brand);

        Fragment fragment = new ProductsFragment();
        fragment.setArguments(bundle);

        showFragment(fragment, Constants.allBrandFragment);
    }


    @Override
    public void onShopClicked(Shop shop) {

    }


    @Override
    public void onSizeClicked(Size size) {

        product.setSize(size);
        setSizeStock();
    }


    @Override
    public void onSortBySelected(int position) {

    }


    @Override
    public void onPriceBySelected(int position) {

    }


    private void showFragment(Fragment fragment, String tag) {

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                .add(R.id.frame_layout, fragment)
                .addToBackStack(tag)
                .commit();
    }
}
