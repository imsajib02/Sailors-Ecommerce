package com.b2gsoft.sailorsexpress.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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

import java.util.List;

public class PriceByAdapter extends RecyclerView.Adapter<PriceByAdapter.ViewHolder> {

    private Context context;
    private List<String> priceByList;
    private int checkedPosition;
    private ItemClickListener clickListener;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView selectionMark;
        private RelativeLayout container;

        ViewHolder(View v) {
            super(v);

            name = (TextView) v.findViewById(R.id.txt_name);
            selectionMark = (ImageView) v.findViewById(R.id.check_mark);
            container = (RelativeLayout) v.findViewById(R.id.rel_container);
        }
    }

    public PriceByAdapter(Context context, List<String> priceByList, int checkedPosition, ItemClickListener clickListener) {
        this.context = context;
        this.priceByList = priceByList;
        this.checkedPosition = checkedPosition;
        this.clickListener = clickListener;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.brand_by_cell, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final String name = priceByList.get(i);

        viewHolder.name.setText(name);

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickListener.onPriceBySelected(i);

                checkedPosition = i;
                notifyDataSetChanged();
            }
        });

        if (checkedPosition == i) {

            viewHolder.selectionMark.setVisibility(View.VISIBLE);
        } else {

            viewHolder.selectionMark.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return priceByList.size();
    }
}