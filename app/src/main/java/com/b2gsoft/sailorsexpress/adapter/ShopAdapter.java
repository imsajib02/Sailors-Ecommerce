package com.b2gsoft.sailorsexpress.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.b2gsoft.sailorsexpress.R;
import com.b2gsoft.sailorsexpress.contract.ItemClickListener;
import com.b2gsoft.sailorsexpress.model.Brand;
import com.b2gsoft.sailorsexpress.model.Shop;
import com.b2gsoft.sailorsexpress.network.RetrofitInstance;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Shop> shopList;
    private List<Shop> fullShopList;
    private ItemClickListener clickListener;
    private RetrofitInstance retrofitInstance;
    private int layoutId;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView imageView;
        private ProgressBar progress;
        private CardView cardView;

        ViewHolder(View v)
        {
            super(v);

            name = (TextView) v.findViewById(R.id.txt_brand_name);
            imageView = (ImageView) v.findViewById(R.id.image_view);
            progress = (ProgressBar) v.findViewById(R.id.progress);
            cardView = (CardView) v.findViewById(R.id.card_view);
        }
    }

    public ShopAdapter(Context context, List<Shop> shopList, ItemClickListener clickListener, int layoutId) {
        this.context = context;
        this.shopList = shopList;
        this.fullShopList = shopList;
        this.clickListener = clickListener;
        this.layoutId = layoutId;
        retrofitInstance = new RetrofitInstance();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final Shop shop = shopList.get(i);

        viewHolder.name.setText(shop.getName());

        Glide.with(context).load(retrofitInstance.BASE_URL + shop.getImage()).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                viewHolder.progress.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                viewHolder.progress.setVisibility(View.GONE);
                return false;
            }
        }).into(viewHolder.imageView);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(clickListener != null) {

                    clickListener.onShopClicked(shop);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                List<Shop> filteredList = new ArrayList<>();

                if(charString.isEmpty()) {

                    filteredList = new ArrayList<>(fullShopList);
                }
                else {

                    for(Shop shop : fullShopList) {

                        if(shop.getName().toLowerCase().startsWith(charString.toLowerCase())) {

                            filteredList.add(shop);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.count = filteredList.size();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                shopList = (ArrayList<Shop>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}