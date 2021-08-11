package com.b2gsoft.sailorsexpress.view;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.b2gsoft.sailorsexpress.R;
import com.b2gsoft.sailorsexpress.fragment.CartFragment;
import com.b2gsoft.sailorsexpress.fragment.SearchFragment;
import com.b2gsoft.sailorsexpress.fragment.HomeFragment;
import com.b2gsoft.sailorsexpress.fragment.ProfileFragment;
import com.b2gsoft.sailorsexpress.utils.Constants;
import com.b2gsoft.sailorsexpress.utils.DBHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static RelativeLayout homeTab, cartTab, searchTab, profileTab;
    private static ImageView icHome, icCart, icSearch, icProfile;
    private static TextView txtHome, txtCart, txtSearch, txtProfile;
    public static TextView txtCartItem;

    private String previousTab;
    private static String currentTab;

    private MainActivity context;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = this;
        dbHelper = new DBHelper(this);

        homeTab = (RelativeLayout) findViewById(R.id.tab_home);
        searchTab = (RelativeLayout) findViewById(R.id.tab_search);
        cartTab = (RelativeLayout) findViewById(R.id.tab_cart);
        profileTab = (RelativeLayout) findViewById(R.id.tab_profile);

        icHome = (ImageView) findViewById(R.id.ic_home);
        icSearch = (ImageView) findViewById(R.id.ic_search);
        icCart = (ImageView) findViewById(R.id.ic_cart);
        icProfile = (ImageView) findViewById(R.id.ic_profile);

        txtHome = (TextView) findViewById(R.id.txt_home);
        txtSearch = (TextView) findViewById(R.id.txt_search);
        txtCart = (TextView) findViewById(R.id.txt_cart);
        txtProfile = (TextView) findViewById(R.id.txt_profile);

        txtCartItem = (TextView) findViewById(R.id.txt_cart_item);

        txtCartItem.setText("" + dbHelper.getProductsCount());

        openHomeFragment();

        homeTab.setOnClickListener(this);
        searchTab.setOnClickListener(this);
        cartTab.setOnClickListener(this);
        profileTab.setOnClickListener(this);
    }


    private void openHomeFragment() {

        previousTab = Constants.homeTab;
        currentTab = Constants.homeTab;

        if(getSupportFragmentManager().findFragmentById(R.id.frame_layout) != null) {

            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.frame_layout)).commit();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
    }


    @Override
    public void onClick(View v) {

        previousTab = currentTab;

        switch(v.getId()) {

            case R.id.tab_home:
                homeView();
                openFragment(new HomeFragment());
                break;

            case R.id.tab_search:
                searchView();
                openFragment(new SearchFragment());
                break;

            case R.id.tab_cart:
                cartView();
                openCartFragment();
                break;

            case R.id.tab_profile:
                profileView();
                openFragment(new ProfileFragment());
                break;
        }
    }


    public static void homeView() {

        currentTab = Constants.homeTab;

        homeTab.setBackgroundResource(R.drawable.tab_item_background);
        searchTab.setBackgroundResource(0);
        cartTab.setBackgroundResource(0);
        profileTab.setBackgroundResource(0);

        txtHome.setVisibility(View.VISIBLE);
        txtSearch.setVisibility(View.GONE);
        txtCart.setVisibility(View.GONE);
        txtProfile.setVisibility(View.GONE);

        icHome.setBackgroundResource(R.drawable.home);
        icSearch.setBackgroundResource(R.drawable.search_black);
        icCart.setBackgroundResource(R.drawable.cart_black);
        icProfile.setBackgroundResource(R.drawable.user_black);
    }


    public static void searchView() {

        currentTab = Constants.searchTab;

        homeTab.setBackgroundResource(0);
        searchTab.setBackgroundResource(R.drawable.tab_item_background);
        cartTab.setBackgroundResource(0);
        profileTab.setBackgroundResource(0);

        txtHome.setVisibility(View.GONE);
        txtSearch.setVisibility(View.VISIBLE);
        txtCart.setVisibility(View.GONE);
        txtProfile.setVisibility(View.GONE);

        icHome.setBackgroundResource(R.drawable.home_black);
        icSearch.setBackgroundResource(R.drawable.search);
        icCart.setBackgroundResource(R.drawable.cart_black);
        icProfile.setBackgroundResource(R.drawable.user_black);
    }


    public static void cartView() {

        currentTab = Constants.cartTab;

        homeTab.setBackgroundResource(0);
        searchTab.setBackgroundResource(0);
        cartTab.setBackgroundResource(R.drawable.tab_item_background);
        profileTab.setBackgroundResource(0);

        txtHome.setVisibility(View.GONE);
        txtSearch.setVisibility(View.GONE);
        txtCart.setVisibility(View.VISIBLE);
        txtProfile.setVisibility(View.GONE);

        icHome.setBackgroundResource(R.drawable.home_black);
        icSearch.setBackgroundResource(R.drawable.search_black);
        icCart.setBackgroundResource(R.drawable.cart);
        icProfile.setBackgroundResource(R.drawable.user_black);
    }


    public static void profileView() {

        currentTab = Constants.profileTab;

        homeTab.setBackgroundResource(0);
        searchTab.setBackgroundResource(0);
        cartTab.setBackgroundResource(0);
        profileTab.setBackgroundResource(R.drawable.tab_item_background);

        txtHome.setVisibility(View.GONE);
        txtSearch.setVisibility(View.GONE);
        txtCart.setVisibility(View.GONE);
        txtProfile.setVisibility(View.VISIBLE);

        icHome.setBackgroundResource(R.drawable.home_black);
        icSearch.setBackgroundResource(R.drawable.search_black);
        icCart.setBackgroundResource(R.drawable.cart_black);
        icProfile.setBackgroundResource(R.drawable.user);
    }


    private void openCartFragment() {

        Bundle bundle = new Bundle();
        bundle.putString(Constants.previousTab, this.previousTab);

        Fragment fragment = new CartFragment();
        fragment.setArguments(bundle);

        openFragment(fragment);
    }


    private void openFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }
}