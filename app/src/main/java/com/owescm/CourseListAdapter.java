package com.owescm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.owescm.educo.CourseListModel;
import com.owescm.educo.R;

import java.util.ArrayList;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.PostViewHolder> {

    private ArrayList<CourseListModel> values;
    private ClickListener clickListener;

    public CourseListAdapter(ArrayList<CourseListModel> values, ClickListener clickListener) {
        this.values = values;
        this.clickListener = clickListener;
    }

    public CourseListAdapter() {

    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, final int position) {

        holder.course_name.setText(values.get(position).getCourse_name());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.courseOnClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        TextView course_name;
        CardView ivEdit;

        private PostViewHolder(@NonNull View itemView) {

            super(itemView);
            course_name = itemView.findViewById(R.id.course_name_item);
            ivEdit = itemView.findViewById(R.id.card);

        }
    }

    public interface ClickListener {
        void courseOnClick(int Position);


    }


}
