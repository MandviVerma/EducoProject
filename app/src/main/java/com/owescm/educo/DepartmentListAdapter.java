package com.owescm.educo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DepartmentListAdapter extends RecyclerView.Adapter<DepartmentListAdapter.PostViewHolder> {

    private ArrayList<DepartmentListModel> values;
    private ClickListener clickListener;

    public DepartmentListAdapter(ArrayList<DepartmentListModel> values, ClickListener clickListener) {
        this.values = values;
        this.clickListener = clickListener;
    }

    public DepartmentListAdapter() {

    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_department_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, final int position) {

        holder.departmentName.setText(values.get(position).getDepartment_name());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.departmentOnClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        TextView departmentName;
        CardView ivEdit;

        private PostViewHolder(@NonNull View itemView) {

            super(itemView);
            departmentName = itemView.findViewById(R.id.department_item);
            ivEdit = itemView.findViewById(R.id.card);

        }
    }

    public interface ClickListener {
        void departmentOnClick(int Position);

    }
}


