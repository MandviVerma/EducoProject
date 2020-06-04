package com.owescm.educo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.PostViewHolder> {

    private ArrayList<SubjectListModel> values;
    private ClickListener clickListener;

    public SubjectListAdapter(ArrayList<SubjectListModel> values, ClickListener clickListener) {
        this.values = values;
        this.clickListener = clickListener;
    }

    public SubjectListAdapter() {

    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, final int position) {

        holder.subjectName.setText(values.get(position).getSubject_name());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.subjectOnClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName;
        CardView ivEdit;

        private PostViewHolder(@NonNull View itemView) {

            super(itemView);
            subjectName = itemView.findViewById(R.id.subject_item);
            ivEdit = itemView.findViewById(R.id.card);

        }
    }

    public interface ClickListener {
        void subjectOnClick(int Position);

    }
}


