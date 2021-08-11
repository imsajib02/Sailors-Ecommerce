package com.b2gsoft.sailorsexpress.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.b2gsoft.sailorsexpress.R;
import com.b2gsoft.sailorsexpress.contract.ItemClickListener;
import com.b2gsoft.sailorsexpress.model.SubCategory;

import java.util.List;

public class SubCategoryByAdapter extends RecyclerView.Adapter<SubCategoryByAdapter.ViewHolder> {

    private Context context;
    private List<SubCategory> subCategoryList;
    private SubCategory selectedSubCategory;
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

    public SubCategoryByAdapter(Context context, List<SubCategory> subCategoryList, SubCategory selectedSubCategory, ItemClickListener clickListener) {
        this.context = context;
        this.subCategoryList = subCategoryList;
        this.selectedSubCategory = selectedSubCategory;
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

        final SubCategory subCategory = subCategoryList.get(i);

        viewHolder.name.setText(subCategory.getName());

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickListener.onSubCategoryClicked(subCategory);

                selectedSubCategory = subCategory;
                notifyDataSetChanged();
            }
        });

        if (selectedSubCategory != null && TextUtils.equals(selectedSubCategory.getId(), subCategory.getId())) {

            viewHolder.selectionMark.setVisibility(View.VISIBLE);
        } else {

            viewHolder.selectionMark.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }
}