package com.b2gsoft.sailorsexpress.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.b2gsoft.sailorsexpress.R;
import com.b2gsoft.sailorsexpress.model.Category;
import com.b2gsoft.sailorsexpress.model.Slider;
import com.b2gsoft.sailorsexpress.network.RetrofitInstance;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ViewHolder> {

    private Context context;
    private List<Slider> list;
    private RetrofitInstance retrofitInstance;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView imageView;

        ViewHolder(View v)
        {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.img_offer);
        }
    }

    public SliderAdapter(Context context, List<Slider> list) {
        this.context = context;
        this.list = list;
        retrofitInstance = new RetrofitInstance();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.slider_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final Slider slider = list.get(i);

        Glide.with(context).load(retrofitInstance.BASE_URL + slider.getImage()).addListener(new RequestListener<Drawable>() {
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}