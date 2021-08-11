package com.b2gsoft.sailorsexpress.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.b2gsoft.sailorsexpress.R;
import com.b2gsoft.sailorsexpress.network.RetrofitInstance;
import com.bumptech.glide.Glide;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Context context;
    private List<String> list;
    private RetrofitInstance retrofitInstance;

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        ViewHolder(View v)
        {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.img_view);
        }
    }

    public ImageAdapter(Context context, List<String> list) {
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

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_image_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final String path = list.get(i);
        Glide.with(context).load(retrofitInstance.BASE_URL + path).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}