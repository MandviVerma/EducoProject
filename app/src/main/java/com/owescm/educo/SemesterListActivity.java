package com.owescm.educo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.owescm.CourseListActivity.courseDR;

public class SemesterListActivity extends AppCompatActivity {

    public Button add;
    private RecyclerView recyclerView;
    EditText semesterName;
    SemesterListAdapter.ClickListener clickListener;
    SemesterListAdapter semesterListAdapter;
    String courseId, courseName;
    ArrayList<SemesterListModel> semesterList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester_list);

        courseId = getIntent().getStringExtra("courseId");
        courseName = getIntent().getStringExtra("courseName");

        recyclerView = findViewById(R.id.rv_semester_list);
        add = findViewById(R.id.btn_add);
        semesterName = findViewById(R.id.et_semester);

        onClick();
        getProducts();
        initRecyclerView();
        addSemester();
    }
    private void addSemester() {
        String semester = semesterName.getText().toString();
        if (!TextUtils.isEmpty(semester)) {
            String semId = courseDR.push().getKey();

            SemesterListModel semesterListModel = new SemesterListModel(semester, semId);
            courseDR.child(courseId).child("semester").setValue(semesterListModel);
        } else {
            Toast.makeText(SemesterListActivity.this, "Enter Semester to add", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRecyclerView() {
        semesterListAdapter = new SemesterListAdapter(semesterList,
                clickListener = new SemesterListAdapter.ClickListener() {
                    @Override
                    public void semesterOnClick(int Position) {
                        Intent intent = new Intent(SemesterListActivity.this, DepartmentListActivity.class);
                        intent.putExtra("position", Position);
                        intent.putExtra("courseId",courseId);
                        intent.putExtra("courseName",courseName);
                        intent.putExtra("semesterId",semesterList.get(Position).getSemester_id());
                        intent.putExtra("semesterName",semesterList.get(Position).getSemester_id());
                        startActivity(intent);
                    }
                });
        recyclerView.setAdapter(semesterListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getProducts() {
        courseDR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                semesterList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SemesterListModel semesterModel = snapshot.getValue(SemesterListModel.class);
                    semesterList.add(semesterModel);
                }
                semesterListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void onClick() {

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSemester();
            }
        });

    }
}




