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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.owescm.CourseListActivity.courseDR;


public class SubjectListActivity extends AppCompatActivity {

    public Button add;
    private RecyclerView recyclerView;
    EditText subjectName;
    SubjectListAdapter.ClickListener clickListener;
    SubjectListAdapter subjectListAdapter;
    String courseId, courseName, semId, semName, departmentId, departmentName;
    ArrayList<SubjectListModel> subjectList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        courseId = getIntent().getStringExtra("courseId");
        courseName = getIntent().getStringExtra("courseName");
        semId = getIntent().getStringExtra("semesterId");
        semName = getIntent().getStringExtra("semesterName");
        departmentId = getIntent().getStringExtra("departmentId");
        departmentName = getIntent().getStringExtra("departmentName");


        recyclerView = findViewById(R.id.rv_subject_list);
        add = findViewById(R.id.btn_add);
        subjectName = findViewById(R.id.et_subject);

        onClick();
        getProducts();
        initRecyclerView();
        addSubject();
    }

    private void addSubject() {
        String subject = subjectName.getText().toString();
        if (!TextUtils.isEmpty(subject)) {
            String subjectId = courseDR.push().getKey();

            SubjectListModel subjectListModel = new SubjectListModel(subject, subjectId);
            courseDR.child(courseId).child("semester").child(semId).child("department").child(departmentId).setValue(subjectListModel);
        } else {
            Toast.makeText(SubjectListActivity.this, "Enter subject to add", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRecyclerView() {
        subjectListAdapter = new SubjectListAdapter(subjectList,
                clickListener = new SubjectListAdapter.ClickListener() {
                    @Override
                    public void subjectOnClick(int Position) {
                        Intent intent = new Intent(SubjectListActivity.this, UnitListActivity.class);
                        intent.putExtra("position", Position);
                        intent.putExtra("courseId", courseId);
                        intent.putExtra("courseName", courseName);
                        intent.putExtra("semesterId", semId);
                        intent.putExtra("semesterName", semName);
                        intent.putExtra("departmentId", departmentId);
                        intent.putExtra("departmentName", departmentName);
                        intent.putExtra("subjectId", subjectList.get(Position).getSubject_id());
                        intent.putExtra("subjectName", subjectList.get(Position).getSubject_name());
                        startActivity(intent);
                    }
                });
        recyclerView.setAdapter(subjectListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getProducts() {
        courseDR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                subjectList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SubjectListModel subjectModel = snapshot.getValue(SubjectListModel.class);
                    subjectList.add(subjectModel);
                }
                subjectListAdapter.notifyDataSetChanged();
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
                addSubject();
            }
        });

    }
}





