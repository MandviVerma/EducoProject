package com.owescm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.owescm.educo.CourseListModel;
import com.owescm.educo.R;
import com.owescm.educo.SemesterListActivity;

import java.util.ArrayList;

public class CourseListActivity extends AppCompatActivity {

    public FloatingActionButton fab;
    private RecyclerView recyclerView;
    CourseListAdapter.ClickListener clickListener;
    CourseListAdapter courseListAdapter;
    public static DatabaseReference courseDR;
    ArrayList<CourseListModel> courseList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        courseDR = FirebaseDatabase.getInstance().getReference("course");
        recyclerView = findViewById(R.id.rv_course_list);
        fab = findViewById(R.id.floatingButton);
        onClick();
        getProducts();
        initRecyclerView();

    }
    private void initRecyclerView() {
        courseListAdapter = new CourseListAdapter(courseList,
                clickListener = new CourseListAdapter.ClickListener() {
            @Override
            public void courseOnClick(int Position) {
                Intent intent = new Intent(CourseListActivity.this, SemesterListActivity.class);
                intent.putExtra("position", Position);
                intent.putExtra("courseId",courseList.get(Position).getCourse_id());
                intent.putExtra("courseName",courseList.get(Position).getCourse_name());

                startActivity(intent);
            }
        });
        recyclerView.setAdapter(courseListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getProducts() {
        courseDR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courseList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CourseListModel courseModel = snapshot.getValue(CourseListModel.class);
                    courseList.add(courseModel);
                }
                courseListAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void onClick() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseListActivity.this, AddCourseActivity.class);
                startActivity(intent);
            }
        });
    }
}



