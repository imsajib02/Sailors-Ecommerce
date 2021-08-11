package com.b2gsoft.sailorsexpress.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.b2gsoft.sailorsexpress.R;
import com.b2gsoft.sailorsexpress.contract.ItemClickListener;
import com.b2gsoft.sailorsexpress.model.Category;
import com.b2gsoft.sailorsexpress.network.RetrofitInstance;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<Category> categoryList;
    private RetrofitInstance retrofitInstance;
    private ItemClickListener clickListener;
    private int selectedIndex = 0;
    private boolean listenForSelection = false;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView imageView;
        private RelativeLayout container;
        private CardView cardView;

        ViewHolder(View v)
        {
            super(v);

            name = (TextView) v.findViewById(R.id.txt_cate_name);
            imageView = (ImageView) v.findViewById(R.id.image_view);
            container = (RelativeLayout) v.findViewById(R.id.container);
            cardView = (CardView) v.findViewById(R.id.card_view);
        }
    }

    public CategoryAdapter(Context context, List<Category> categoryList, ItemClickListener clickListener, boolean listenForSelection) {
        this.context = context;
        this.categoryList = categoryList;
        this.clickListener = clickListener;
        this.listenForSelection = listenForSelection;
        retrofitInstance = new RetrofitInstance();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.featured_category_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        final Category category = categoryList.get(i);

        if(category.isActive() && category.isStatus()) {

            viewHolder.container.setVisibility(View.VISIBLE);

            viewHolder.name.setText(category.getName());

            Glide.with(context).load(retrofitInstance.BASE_URL + category.getImage()).addListener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                    viewHolder.imageView.setImageResource(R.drawable.ic_image_black_24dp);
                    viewHolder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.grey_20), android.graphics.PorterDuff.Mode.MULTIPLY);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                    return false;
                }
            }).into(viewHolder.imageView);

            viewHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(clickListener != null) {

                        if(listenForSelection) {

                            selectedIndex = i;
                            notifyDataSetChanged();
                        }

                        clickListener.onCategoryClicked(category);
                    }
                }
            });

            if(listenForSelection && selectedIndex == i) {

                viewHolder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }
            else {

                viewHolder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            }
        }
        else {

            viewHolder.container.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}