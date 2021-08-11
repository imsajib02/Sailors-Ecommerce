package com.b2gsoft.sailorsexpress.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.L;
import com.b2gsoft.sailorsexpress.R;
import com.b2gsoft.sailorsexpress.adapter.BrandAdapter;
import com.b2gsoft.sailorsexpress.adapter.CategoryAdapter;
import com.b2gsoft.sailorsexpress.adapter.ProductAdapter;
import com.b2gsoft.sailorsexpress.adapter.ShopAdapter;
import com.b2gsoft.sailorsexpress.adapter.SliderAdapter;
import com.b2gsoft.sailorsexpress.contract.Connectivity;
import com.b2gsoft.sailorsexpress.contract.HomeContract;
import com.b2gsoft.sailorsexpress.contract.ItemClickListener;
import com.b2gsoft.sailorsexpress.model.Brand;
import com.b2gsoft.sailorsexpress.model.Category;
import com.b2gsoft.sailorsexpress.model.HomeData;
import com.b2gsoft.sailorsexpress.model.Product;
import com.b2gsoft.sailorsexpress.model.Shop;
import com.b2gsoft.sailorsexpress.model.Size;
import com.b2gsoft.sailorsexpress.model.SubCategory;
import com.b2gsoft.sailorsexpress.presenter.DataPresenter;
import com.b2gsoft.sailorsexpress.utils.Constants;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.nshmura.snappysmoothscroller.SnappyGridLayoutManager;
import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator2;
import pl.droidsonroids.gif.GifImageView;

public class HomeFragment extends Fragment implements HomeContract, Connectivity, ItemClickListener {

    private DataPresenter presenter;
    private Context context;

    private HomeContract homeContract;
    private Connectivity connectivity;
    private ItemClickListener clickListener;

    private NestedScrollView nestedScrollView;
    private View connectivityView;
    private RelativeLayout rootView, featuredCategoryHeader, ourBrandHeader, ourShopHeader, topSellingHeader, featuredProductHeader, newArrivalHeader;
    private LinearLayout featuredCategorySeeAll, ourBrandSeeAll, ourShopSeeAll, topSellingSeeAll, featProductSeeAll, newArrivalSeeAll;
    private ShimmerRecyclerView slider, featuredCategory, ourBrands, ourShops, topSelling, featProducts, newArrivals;
    private GifImageView sliderLoading;
    private CircleIndicator2 circleIndicator;
    private ImageView noOffer, imgBack, connectivityImage;
    private TextView featuredCategoryTitle, ourBrandTitle, ourShopTitle, topSellingTitle, featuredProductTitle, newArrivalTitle, connectivityMessage, tryAgain;

    private HomeData homeData;
    private ProductAdapter topSellingAdapter, featuredProductAdapter, newArrivalAdapter;
    private BrandAdapter brandAdapter;
    private ShopAdapter shopAdapter;
    private CategoryAdapter categoryAdapter;
    private SliderAdapter sliderAdapter;

    private Animation animation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        this.context = view.getContext();
        animation = AnimationUtils.loadAnimation(context, R.anim.bounce);

        homeContract = this;
        connectivity = this;
        clickListener = this;

        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nested_view);

        connectivityView = (View) view.findViewById(R.id.container_not_connected);

        connectivityImage = (ImageView) connectivityView.findViewById(R.id.img_error);
        connectivityMessage = (TextView) connectivityView.findViewById(R.id.txt_error);
        tryAgain = (TextView) connectivityView.findViewById(R.id.try_again);

        slider = (ShimmerRecyclerView) view.findViewById(R.id.slider);
        featuredCategory = (ShimmerRecyclerView) view.findViewById(R.id.recycler_feat_cat);
        ourBrands = (ShimmerRecyclerView) view.findViewById(R.id.recycler_our_brand);
        ourShops = (ShimmerRecyclerView) view.findViewById(R.id.recycler_our_shop);
        topSelling = (ShimmerRecyclerView) view.findViewById(R.id.recycler_top_selling);
        featProducts = (ShimmerRecyclerView) view.findViewById(R.id.recycler_feat_product);
        newArrivals = (ShimmerRecyclerView) view.findViewById(R.id.recycler_new_arrival);

        rootView = (RelativeLayout) view.findViewById(R.id.root_view);
        sliderLoading = (GifImageView) view.findViewById(R.id.slider_loading);
        circleIndicator = (CircleIndicator2) view.findViewById(R.id.circle_indicator);
        noOffer = (ImageView) view.findViewById(R.id.img_no_offer);

        featuredCategorySeeAll = (LinearLayout) view.findViewById(R.id.ft_cat_see_all);
        ourBrandSeeAll = (LinearLayout) view.findViewById(R.id.brand_see_all);
        ourShopSeeAll = (LinearLayout) view.findViewById(R.id.shop_see_all);
        topSellingSeeAll = (LinearLayout) view.findViewById(R.id.top_sell_see_all);
        featProductSeeAll = (LinearLayout) view.findViewById(R.id.feat_product_see_all);
        newArrivalSeeAll = (LinearLayout) view.findViewById(R.id.new_arrival_see_all);

        featuredCategoryHeader = (RelativeLayout) view.findViewById(R.id.feat_category);
        ourBrandHeader = (RelativeLayout) view.findViewById(R.id.our_brand);
        ourShopHeader = (RelativeLayout) view.findViewById(R.id.our_shop);
        topSellingHeader = (RelativeLayout) view.findViewById(R.id.top_selling);
        featuredProductHeader = (RelativeLayout) view.findViewById(R.id.feat_product);
        newArrivalHeader = (RelativeLayout) view.findViewById(R.id.new_arrival);

        featuredCategoryTitle = (TextView) view.findViewById(R.id.ft_cate_title);
        ourBrandTitle = (TextView) view.findViewById(R.id.our_brand_title);
        ourShopTitle = (TextView) view.findViewById(R.id.our_shop_title);
        topSellingTitle = (TextView) view.findViewById(R.id.top_sell_title);
        featuredProductTitle = (TextView) view.findViewById(R.id.ft_product_title);
        newArrivalTitle = (TextView) view.findViewById(R.id.new_arrival_title);

        presenter = new DataPresenter(context, connectivity, homeContract);
        presenter.getHomeData();

        featuredCategorySeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new CategoryFragment();
                showFragment(fragment);
            }
        });

        ourBrandSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new BrandFragment();
                showFragment(fragment);
            }
        });

        ourShopSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new ShopFragment();
                showFragment(fragment);
            }
        });

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(animation);

                nestedScrollView.setVisibility(View.VISIBLE);
                connectivityView.setVisibility(View.GONE);

                presenter.getHomeData();
            }
        });

        return view;
    }


    private void showFragment(Fragment fragment) {

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void notConnected() {

        connectivityImage.setImageResource(R.drawable.not_connected);
        connectivityMessage.setText(R.string.you_are_not_connected);

        showFailedView();
    }


    @Override
    public void noActiveConnection() {

        connectivityImage.setImageResource(R.drawable.connection_inactive_2);
        connectivityMessage.setText(R.string.you_connection_inactive);

        showFailedView();
    }


    @Override
    public void onConnectionTimeOut() {

        presenter.getHomeData();
    }


    @Override
    public void onSuccess(HomeData homeData) {

        if(homeData != null && homeData.getData() != null) {

            this.homeData = homeData;

            setSliders();
            setFeaturedCategory();
            setOurBrands();
            setOurShops();
            setTopSellingProducts();
            setFeaturedProducts();
            setNewArrivalProducts();
        }
    }


    @Override
    public void onFailure() {

        connectivityImage.setImageResource(R.drawable.failure);
        connectivityMessage.setText(R.string.could_not_load_data);

        showFailedView();
    }


    private void showFailedView() {

        nestedScrollView.setVisibility(View.GONE);
        connectivityView.setVisibility(View.VISIBLE);
    }


    private void setSliders() {

        slider.hideShimmerAdapter();

        sliderLoading.setVisibility(View.GONE);

        if(homeData.getData().getInfo().getSliders().size() > 0) {

            slider.setVisibility(View.VISIBLE);
            circleIndicator.setVisibility(View.VISIBLE);
            noOffer.setVisibility(View.GONE);

            final SnappyLinearLayoutManager verticalLayoutManager = new SnappyLinearLayoutManager(context, SnappyLinearLayoutManager.HORIZONTAL, false);
            slider.setLayoutManager(verticalLayoutManager);

            sliderAdapter = new SliderAdapter(context, homeData.getData().getInfo().getSliders());
            slider.setAdapter(sliderAdapter);

            PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
            pagerSnapHelper.attachToRecyclerView(slider);

            circleIndicator.attachToRecyclerView(slider, pagerSnapHelper);
            sliderAdapter.registerAdapterDataObserver(circleIndicator.getAdapterDataObserver());

            final Timer timer = new Timer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    if(verticalLayoutManager.findLastCompletelyVisibleItemPosition() < (sliderAdapter.getItemCount() - 1)) {

                        verticalLayoutManager.smoothScrollToPosition(slider, new RecyclerView.State(), verticalLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                    }
                    else if(verticalLayoutManager.findLastCompletelyVisibleItemPosition() == (sliderAdapter.getItemCount() - 1)) {

                        verticalLayoutManager.smoothScrollToPosition(slider, new RecyclerView.State(), 0);
                    }
                }
            }, 0, 5000);
        }
        else {

            slider.setVisibility(View.GONE);
            circleIndicator.setVisibility(View.GONE);

            noOffer.setVisibility(View.VISIBLE);
            noOffer.setImageResource(R.drawable.no_offer);
        }
    }


    private void setFeaturedCategory() {

        for(Category category : homeData.getData().getFeaturedCategory())
        {
            category.setActive(true);
            category.setStatus(true);
        }

        featuredCategory.hideShimmerAdapter();

        if(homeData.getData().getFeaturedCategory().size() > 0) {

            featuredCategoryHeader.setVisibility(View.VISIBLE);

            SnappyLinearLayoutManager verticalLayoutManager = new SnappyLinearLayoutManager(context, SnappyLinearLayoutManager.HORIZONTAL, false);
            featuredCategory.setLayoutManager(verticalLayoutManager);

            categoryAdapter = new CategoryAdapter(context, homeData.getData().getFeaturedCategory(), clickListener, false);
            featuredCategory.setAdapter(categoryAdapter);
        }
        else {

            featuredCategory.setVisibility(View.GONE);
            featuredCategoryHeader.setVisibility(View.GONE);
        }
    }


    private void setOurBrands() {

        List<Brand> brandList = new ArrayList<>();

        if(homeData.getData().getOurBrands() !=  null && homeData.getData().getOurBrands().getItems() != null) {

            for(int i=0; i<homeData.getData().getOurBrands().getItems().size(); i++) {

                if(homeData.getData().getOurBrands().getItems().get(i).isActive()) {

                    brandList.add(homeData.getData().getOurBrands().getItems().get(i));
                }
            }
        }

        ourBrands.hideShimmerAdapter();

        ourBrandTitle.setText(homeData.getData().getOurBrands().getTitle());

        if(brandList.size() > 0) {

            ourBrandHeader.setVisibility(View.VISIBLE);

            SnappyLinearLayoutManager verticalLayoutManager = new SnappyLinearLayoutManager(context, SnappyLinearLayoutManager.HORIZONTAL, false);
            ourBrands.setLayoutManager(verticalLayoutManager);

            brandAdapter = new BrandAdapter(context, brandList, clickListener, R.layout.our_brand_layout);
            ourBrands.setAdapter(brandAdapter);
        }
        else {

            ourBrands.setVisibility(View.GONE);
            ourBrandHeader.setVisibility(View.GONE);
        }
    }


    private void setOurShops() {

        ourShops.hideShimmerAdapter();

        ourShopTitle.setText(homeData.getData().getOurShops().getTitle());

        if(homeData.getData().getOurShops().getItems().size() > 0) {

            ourShopHeader.setVisibility(View.VISIBLE);

            SnappyLinearLayoutManager verticalLayoutManager = new SnappyLinearLayoutManager(context, SnappyLinearLayoutManager.HORIZONTAL, false);
            ourShops.setLayoutManager(verticalLayoutManager);

            shopAdapter = new ShopAdapter(context, homeData.getData().getOurShops().getItems(), clickListener, R.layout.our_brand_layout);
            ourShops.setAdapter(shopAdapter);
        }
        else {

            ourShops.setVisibility(View.GONE);
            ourShopHeader.setVisibility(View.GONE);
        }
    }


    private void setTopSellingProducts() {

        topSelling.hideShimmerAdapter();
        topSellingSeeAll.setVisibility(View.GONE);

        topSellingTitle.setText(homeData.getData().getTopSelling().getTitle());

        if(homeData.getData().getTopSelling().getItems().size() > 0) {

            topSellingHeader.setVisibility(View.VISIBLE);

            SnappyGridLayoutManager snappyGridLayoutManager = new SnappyGridLayoutManager(context, 2);
            topSelling.setLayoutManager(snappyGridLayoutManager);

            topSellingAdapter = new ProductAdapter(context, homeData.getData().getTopSelling().getItems(), clickListener);
            topSelling.setAdapter(topSellingAdapter);
        }
        else {

            topSelling.setVisibility(View.GONE);
            topSellingHeader.setVisibility(View.GONE);
        }
    }


    private void setFeaturedProducts() {

        featProducts.hideShimmerAdapter();
        featProductSeeAll.setVisibility(View.GONE);

        featuredProductTitle.setText(homeData.getData().getFeaturedProducts().getTitle());

        if(homeData.getData().getFeaturedProducts().getItems().size() > 0) {

            featuredProductHeader.setVisibility(View.VISIBLE);

            SnappyGridLayoutManager snappyGridLayoutManager = new SnappyGridLayoutManager(context, 2);
            featProducts.setLayoutManager(snappyGridLayoutManager);

            featuredProductAdapter = new ProductAdapter(context, homeData.getData().getFeaturedProducts().getItems(), clickListener);
            featProducts.setAdapter(featuredProductAdapter);
        }
        else {

            featProducts.setVisibility(View.GONE);
            featuredProductHeader.setVisibility(View.GONE);
        }
    }


    private void setNewArrivalProducts() {

        newArrivals.hideShimmerAdapter();
        newArrivalSeeAll.setVisibility(View.GONE);

        newArrivalTitle.setText(homeData.getData().getNewArrival().getTitle());

        if(homeData.getData().getNewArrival().getItems().size() > 0) {

            newArrivalHeader.setVisibility(View.VISIBLE);

            SnappyGridLayoutManager snappyGridLayoutManager = new SnappyGridLayoutManager(context, 2);
            newArrivals.setLayoutManager(snappyGridLayoutManager);

            newArrivalAdapter = new ProductAdapter(context, homeData.getData().getNewArrival().getItems(), clickListener);
            newArrivals.setAdapter(newArrivalAdapter);
        }
        else {

            newArrivals.setVisibility(View.GONE);
            newArrivalHeader.setVisibility(View.GONE);
        }
    }


    @Override
    public void onCategoryClicked(Category category) {

        Bundle bundle = new Bundle();
        bundle.putString(Constants.productBy, Constants.productByCategory);
        bundle.putSerializable(Constants.modelType, category);

        showProductFragment(bundle);
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

        showFragment(fragment);
    }


    @Override
    public void onBrandClicked(Brand brand) {

        Bundle bundle = new Bundle();
        bundle.putString(Constants.productBy, Constants.productByBrand);
        bundle.putSerializable(Constants.modelType, brand);

        showProductFragment(bundle);
    }


    @Override
    public void onShopClicked(Shop shop) {

        Bundle bundle = new Bundle();
        bundle.putString(Constants.productBy, Constants.productByShop);
        bundle.putSerializable(Constants.modelType, shop);

        showProductFragment(bundle);
    }


    @Override
    public void onSizeClicked(Size size) {

    }


    @Override
    public void onSortBySelected(int position) {

    }


    @Override
    public void onPriceBySelected(int position) {

    }


    private void showProductFragment(Bundle bundle) {

        Fragment fragment = new ProductsFragment();
        fragment.setArguments(bundle);

        showFragment(fragment);
    }


    @Override
    public void onDestroy() {

        System.exit(0);
        super.onDestroy();
    }
}
