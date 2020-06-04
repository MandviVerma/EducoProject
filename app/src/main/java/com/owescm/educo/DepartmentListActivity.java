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

public class DepartmentListActivity extends AppCompatActivity {

    public Button add;
    private RecyclerView recyclerView;
    EditText departmentName;
    DepartmentListAdapter.ClickListener clickListener;
    DepartmentListAdapter departmentListAdapter;
    String courseId, courseName,semId,semName;
    ArrayList<DepartmentListModel> departmentList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_list);

        courseId = getIntent().getStringExtra("courseId");
        courseName = getIntent().getStringExtra("courseName");
        semId=getIntent().getStringExtra("semesterId");
        semName=getIntent().getStringExtra("semesterName");


        recyclerView = findViewById(R.id.rv_department_list);
        add = findViewById(R.id.btn_add);
        departmentName = findViewById(R.id.et_department);

        onClick();
        getProducts();
        initRecyclerView();
        addDepartment();
    }
    private void addDepartment() {
        String department = departmentName.getText().toString();
        if (!TextUtils.isEmpty(department)) {
            String departmentId = courseDR.push().getKey();

            DepartmentListModel departmentListModel = new DepartmentListModel(department, departmentId);
            courseDR.child(courseId).child("semester").child(semId).setValue(departmentListModel);
        } else {
            Toast.makeText(DepartmentListActivity.this, "Enter department to add", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRecyclerView() {
        departmentListAdapter = new DepartmentListAdapter(departmentList,
                clickListener = new DepartmentListAdapter.ClickListener() {
                    @Override
                    public void departmentOnClick(int Position) {
                        Intent intent = new Intent(DepartmentListActivity.this, SubjectListActivity.class);
                        intent.putExtra("position", Position);
                        intent.putExtra("courseId",courseId);
                        intent.putExtra("courseName",courseName);
                        intent.putExtra("semesterId",semId);
                        intent.putExtra("semesterName",semName);
                        intent.putExtra("departmentId",departmentList.get(Position).getDepartment_id());
                        intent.putExtra("departmentName",departmentList.get(Position).getDepartment_name());
                        startActivity(intent);
                    }
                });
        recyclerView.setAdapter(departmentListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getProducts() {
        courseDR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                departmentList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DepartmentListModel departmentModel = snapshot.getValue(DepartmentListModel.class);
                    departmentList.add(departmentModel);
                }
                departmentListAdapter.notifyDataSetChanged();
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
                addDepartment();
            }
        });

    }
}





