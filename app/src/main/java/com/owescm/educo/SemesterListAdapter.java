package com.owescm.educo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SemesterListAdapter extends RecyclerView.Adapter<SemesterListAdapter.PostViewHolder> {

    private ArrayList<SemesterListModel> values;
    private ClickListener clickListener;

    public SemesterListAdapter(ArrayList<SemesterListModel> values, ClickListener clickListener) {
        this.values = values;
        this.clickListener = clickListener;
    }

    public SemesterListAdapter() {

    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sem_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, final int position) {

        holder.semester.setText(values.get(position).getSemester_name());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.semesterOnClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        TextView semester;
        CardView ivEdit;

        private PostViewHolder(@NonNull View itemView) {

            super(itemView);
            semester = itemView.findViewById(R.id.semester_item);
            ivEdit = itemView.findViewById(R.id.card);

        }
    }

    public interface ClickListener {
        void semesterOnClick(int Position);

    }
}

