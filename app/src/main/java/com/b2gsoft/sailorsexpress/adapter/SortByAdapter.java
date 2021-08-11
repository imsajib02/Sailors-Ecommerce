package com.b2gsoft.sailorsexpress.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.b2gsoft.sailorsexpress.R;
import com.b2gsoft.sailorsexpress.contract.ItemClickListener;

import java.util.List;

public class SortByAdapter extends RecyclerView.Adapter<SortByAdapter.ViewHolder> {

    private Context context;
    private List<String> sortByList;
    private int checkedPosition;
    private ItemClickListener clickListener;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private RadioButton radioButton;
        private RelativeLayout container;

        ViewHolder(View v)
        {
            super(v);

            name = (TextView) v.findViewById(R.id.txt_name);
            radioButton = (RadioButton) v.findViewById(R.id.radio_button);
            container = (RelativeLayout) v.findViewById(R.id.rel_container);
        }
    }

    public SortByAdapter(Context context, List<String> sortByList, int checkedPosition, ItemClickListener clickListener) {
        this.context = context;
        this.sortByList = sortByList;
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

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sort_by_cell, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final String name = sortByList.get(i);

        viewHolder.name.setText(name);

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickListener.onSortBySelected(i);

                checkedPosition = i;
                notifyDataSetChanged();
            }
        });

        if(checkedPosition == i) {

            viewHolder.radioButton.setChecked(true);
        }
        else {

            viewHolder.radioButton.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return sortByList.size();
    }
}