package com.b2gsoft.sailorsexpress.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.b2gsoft.sailorsexpress.R;
import com.b2gsoft.sailorsexpress.adapter.BrandAdapter;
import com.b2gsoft.sailorsexpress.contract.BrandContract;
import com.b2gsoft.sailorsexpress.contract.Connectivity;
import com.b2gsoft.sailorsexpress.contract.ItemClickListener;
import com.b2gsoft.sailorsexpress.model.Brand;
import com.b2gsoft.sailorsexpress.model.Category;
import com.b2gsoft.sailorsexpress.model.Product;
import com.b2gsoft.sailorsexpress.model.Shop;
import com.b2gsoft.sailorsexpress.model.Size;
import com.b2gsoft.sailorsexpress.model.SubCategory;
import com.b2gsoft.sailorsexpress.presenter.DataPresenter;
import com.b2gsoft.sailorsexpress.utils.Constants;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.nshmura.snappysmoothscroller.SnappyGridLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BrandFragment extends Fragment implements BrandContract, Connectivity, ItemClickListener {

    private Context context;
    private DataPresenter presenter;

    private Connectivity connectivity;
    private BrandContract brandContract;
    private ItemClickListener clickListener;

    private LottieAnimationView progress;
    private RelativeLayout rootView, backView;
    private ImageView connectivityImage;
    private View connectivityView;
    private TextView connectivityMessage, tryAgain;
    private ShimmerRecyclerView brandRecycler;
    private EditText etSearch;

    private Animation animation;

    private BrandAdapter brandAdapter;

    private List<Brand> brandList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_brand, container, false);

        this.context = view.getContext();
        animation = AnimationUtils.loadAnimation(context, R.anim.bounce);

        brandContract = this;
        connectivity = this;
        clickListener = this;

        presenter = new DataPresenter(context, connectivity, brandContract);

        progress = (LottieAnimationView) view.findViewById(R.id.progress);
        rootView = (RelativeLayout) view.findViewById(R.id.root_view);
        backView = (RelativeLayout) view.findViewById(R.id.lay_back);
        connectivityView = (View) view.findViewById(R.id.container_not_connected);
        etSearch = (EditText) view.findViewById(R.id.et_search);

        brandRecycler = (ShimmerRecyclerView) view.findViewById(R.id.recycler_brand);

        connectivityImage = (ImageView) connectivityView.findViewById(R.id.img_error);
        connectivityMessage = (TextView) connectivityView.findViewById(R.id.txt_error);
        tryAgain = (TextView) connectivityView.findViewById(R.id.try_again);

        presenter.getAllBrand();

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

                brandRecycler.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                connectivityView.setVisibility(View.GONE);

                presenter.getAllBrand();
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(brandAdapter != null) {

                    brandAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
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
    public void showAll(List<Brand> brandList) {

        for(int i=0; i<brandList.size(); i++) {

            if(brandList.get(i).isActive()) {

                this.brandList.add(brandList.get(i));
            }
        }

        Collections.sort(this.brandList);

        progress.setVisibility(View.GONE);
        etSearch.setVisibility(View.VISIBLE);
        brandRecycler.setVisibility(View.VISIBLE);

        brandRecycler.hideShimmerAdapter();

        SnappyGridLayoutManager snappyGridLayoutManager = new SnappyGridLayoutManager(context, 3);
        brandRecycler.setLayoutManager(snappyGridLayoutManager);

        brandAdapter = new BrandAdapter(context, this.brandList, clickListener, R.layout.our_brand_layout_2);
        brandRecycler.setAdapter(brandAdapter);
    }


    @Override
    public void failedToGetBrands() {

        connectivityImage.setImageResource(R.drawable.failure);
        connectivityMessage.setText(R.string.could_not_load_data);

        onFailure();
    }


    private void onFailure() {

        etSearch.setVisibility(View.GONE);
        brandRecycler.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        connectivityView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onCategoryClicked(Category category) {

    }


    @Override
    public void onSubCategoryClicked(SubCategory subCategory) {

    }


    @Override
    public void onProductClicked(Product product) {

    }


    @Override
    public void onBrandClicked(Brand brand) {

        hideKeyboard();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.productBy, Constants.productByBrand);
        bundle.putSerializable(Constants.modelType, brand);

        Fragment fragment = new ProductsFragment();
        fragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                .add(R.id.frame_layout, fragment)
                .addToBackStack(Constants.allBrandFragment)
                .commit();
    }


    private void hideKeyboard() {

        View view = getActivity().getCurrentFocus();

        if(view != null) {

            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    public void onShopClicked(Shop shop) {

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
}
