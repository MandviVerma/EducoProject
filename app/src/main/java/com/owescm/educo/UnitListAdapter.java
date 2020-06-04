package com.owescm.educo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UnitListAdapter extends RecyclerView.Adapter<UnitListAdapter.PostViewHolder> {

    private ArrayList<UnitListModel> values;
    private ClickListener clickListener;

    public UnitListAdapter(ArrayList<UnitListModel> values, ClickListener clickListener) {
        this.values = values;
        this.clickListener = clickListener;
    }

    public UnitListAdapter() {

    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unit_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, final int position) {

        holder.unitName.setText(values.get(position).getUnit_name());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.unitOnClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        TextView unitName;
        CardView ivEdit;

        private PostViewHolder(@NonNull View itemView) {

            super(itemView);
            unitName = itemView.findViewById(R.id.unit_item);
            ivEdit = itemView.findViewById(R.id.card);

        }
    }

    public interface ClickListener {
        void unitOnClick(int Position);

    }
}


