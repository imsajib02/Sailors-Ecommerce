package com.b2gsoft.sailorsexpress.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.b2gsoft.sailorsexpress.R;
import com.b2gsoft.sailorsexpress.adapter.CartAdapter;
import com.b2gsoft.sailorsexpress.contract.CartClickListener;
import com.b2gsoft.sailorsexpress.model.Cart;
import com.b2gsoft.sailorsexpress.model.User;
import com.b2gsoft.sailorsexpress.utils.Constants;
import com.b2gsoft.sailorsexpress.utils.DBHelper;
import com.b2gsoft.sailorsexpress.utils.SharedPreference;
import com.b2gsoft.sailorsexpress.view.LoginActivity;
import com.b2gsoft.sailorsexpress.view.OrderActivity;

import java.util.ArrayList;
import java.util.List;

import static com.b2gsoft.sailorsexpress.view.MainActivity.homeView;
import static com.b2gsoft.sailorsexpress.view.MainActivity.profileView;
import static com.b2gsoft.sailorsexpress.view.MainActivity.searchView;
import static com.b2gsoft.sailorsexpress.view.MainActivity.txtCartItem;

public class CartFragment extends Fragment implements CartClickListener {

    private Context context;

    private CartClickListener clickListener;

    private RelativeLayout layBack;
    private TextView txtItems, txtApply, txtSubTotal, txtDeliveryCharge, txtDiscount, txtTotal, txtCheckout;
    private RecyclerView cartRecycler;
    private EditText etCoupon;
    private ImageView remove;

    private CartAdapter cartAdapter;

    private SharedPreference sharedPreference;
    private DBHelper dbHelper;

    private String previousTab;

    private List<Cart> cartList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        this.context = view.getContext();
        sharedPreference = new SharedPreference(context);
        dbHelper = new DBHelper(context);

        clickListener = this;

        layBack = (RelativeLayout) view.findViewById(R.id.lay_back);
        cartRecycler = (RecyclerView) view.findViewById(R.id.recycler_cart);
        etCoupon = (EditText) view.findViewById(R.id.et_code);
        remove = (ImageView) view.findViewById(R.id.ic_remove);

        txtItems = (TextView) view.findViewById(R.id.txt_items);
        txtApply = (TextView) view.findViewById(R.id.txt_apply);
        txtSubTotal = (TextView) view.findViewById(R.id.txt_subtotal);
        txtDeliveryCharge = (TextView) view.findViewById(R.id.txt_del_charge);
        txtDiscount = (TextView) view.findViewById(R.id.txt_discount_price);
        txtTotal = (TextView) view.findViewById(R.id.txt_total);
        txtCheckout = (TextView) view.findViewById(R.id.proceed_checkout);

        Bundle args = getArguments();
        previousTab = args.getString(Constants.previousTab);

        txtItems.setText(dbHelper.getProductsCount() + " " + getString(R.string.items));

        cartList = dbHelper.getCartItems();
        setCartList();

        layBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed(false);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkForItemsToRemove();
            }
        });

        txtCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                proceedToCheckOut();
            }
        });

        return view;
    }


    @Override
    public void onDestroy() {

        onBackPressed(true);
        super.onDestroy();
    }


    private void onBackPressed(boolean defaultButton) {

        if(previousTab != null) {

            switch (previousTab) {

                case Constants.homeTab:
                    homeView();
                    break;

                case Constants.searchTab:
                    searchView();
                    break;

                case Constants.profileTab:
                    profileView();
                    break;

                default:
                    homeView();
                    break;
            }
        }
        else {

            homeView();
        }

        if(!defaultButton) {

            getActivity().onBackPressed();
        }
    }


    private void setCartList() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        cartRecycler.setLayoutManager(layoutManager);

        cartAdapter = new CartAdapter(context, cartList, clickListener);
        cartRecycler.setAdapter(cartAdapter);
    }


    @Override
    public void onIncrease(int index) {

        cartList.get(index).getProduct().setQuantity(cartList.get(index).getProduct().getQuantity() + 1);
        dbHelper.updateRow(cartList.get(index).getPosition(), cartList.get(index).getProduct());
        cartAdapter.notifyDataSetChanged();
    }


    @Override
    public void onDecrease(int index) {

        cartList.get(index).getProduct().setQuantity(cartList.get(index).getProduct().getQuantity() - 1);
        dbHelper.updateRow(cartList.get(index).getPosition(), cartList.get(index).getProduct());
        cartAdapter.notifyDataSetChanged();
    }


    @Override
    public void onChecked(int index, boolean isChecked) {

        cartList.get(index).setChecked(isChecked);
    }


    private void proceedToCheckOut() {

        if(cartList.size() > 0) {

            List<Integer> checkedList = new ArrayList<>();

            for(int i=0; i<cartList.size(); i++) {

                if(cartList.get(i).isChecked()) {

                    checkedList.add(i);
                }
            }

            if(checkedList.size() > 0) {

                User user = sharedPreference.getCurrentUser();

                if(user.getId() != 0) {

                    //TODO:
                }
                else {

                    showLoginDialog();
                }
            }
        }
    }


    private void showLoginDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        MaterialButton btnClose = dialog.findViewById(R.id.btn_close);
        MaterialButton btnSubmit = dialog.findViewById(R.id.btn_submit);

        TextView txtTitle = dialog.findViewById(R.id.txt_title);
        TextView txtDesc = dialog.findViewById(R.id.txt_desc);

        txtTitle.setText(getString(R.string.warning));
        txtDesc.setText(getString(R.string.need_to_login));

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                openOrderActivity();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    private void checkForItemsToRemove() {

        if(cartList.size() > 0) {

            List<Integer> checkedList = new ArrayList<>();

            for(int i=0; i<cartList.size(); i++) {

                if(cartList.get(i).isChecked()) {

                    checkedList.add(i);
                }
            }

            if(checkedList.size() > 0) {

                showRemovalConfirmation(checkedList);
            }
            else {

                Toast.makeText(context, getString(R.string.select_items_2_delete), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void showRemovalConfirmation(List<Integer> checkedList) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        MaterialButton btnClose = dialog.findViewById(R.id.btn_close);
        MaterialButton btnSubmit = dialog.findViewById(R.id.btn_submit);

        TextView txtTitle = dialog.findViewById(R.id.txt_title);
        TextView txtDesc = dialog.findViewById(R.id.txt_desc);

        txtTitle.setText(getString(R.string.warning));
        txtDesc.setText(getString(R.string.item_removal_confirmation));

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                removeItems(checkedList);
            }
        });
    }


    private void removeItems(List<Integer> checkedList) {

        List<Integer> failedList = new ArrayList<>();

        for(int i=0; i<checkedList.size(); i++) {

            int result = dbHelper.deleteProduct(cartList.get(checkedList.get(i)).getPosition());

            if(result == 0) {

                failedList.add(checkedList.get(i));
            }
            else {

                cartList.remove(checkedList.get(i).intValue());
                cartAdapter.notifyDataSetChanged();
            }
        }

        txtItems.setText(dbHelper.getProductsCount() + " " + getString(R.string.items));
        txtCartItem.setText("" + dbHelper.getProductsCount());

        if(failedList.size() > 1) {

            Toast.makeText(context, getString(R.string.failed_2_remove) + " " + failedList.size() + " " + getString(R.string.items), Toast.LENGTH_SHORT).show();
        }
        else if(failedList.size() > 0) {

            Toast.makeText(context, getString(R.string.failed_2_remove) + " " + failedList.size() + " " + getString(R.string.item), Toast.LENGTH_SHORT).show();
        }
        else {

            Toast.makeText(context, getString(R.string.items_removed), Toast.LENGTH_SHORT).show();
        }
    }


    private void openOrderActivity() {

        Intent intent = new Intent(getActivity(), OrderActivity.class);
        startActivity(intent);
    }
}
