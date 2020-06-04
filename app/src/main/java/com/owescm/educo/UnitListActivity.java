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

public class UnitListActivity extends AppCompatActivity {

    public Button add;
    private RecyclerView recyclerView;
    EditText unitName;
    UnitListAdapter.ClickListener clickListener;
    UnitListAdapter unitListAdapter;
    String courseId, courseName, semId, semName, departmentId, departmentName, subjectId, subjectName;
    ArrayList<UnitListModel> unitList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_list);

        courseId = getIntent().getStringExtra("courseId");
        courseName = getIntent().getStringExtra("courseName");
        semId = getIntent().getStringExtra("semesterId");
        semName = getIntent().getStringExtra("semesterName");
        departmentId = getIntent().getStringExtra("departmentId");
        departmentName = getIntent().getStringExtra("departmentName");
        subjectId = getIntent().getStringExtra("subjectId");
        subjectName = getIntent().getStringExtra("subjectName");

        recyclerView = findViewById(R.id.rv_unit_list);
        add = findViewById(R.id.btn_add);
        unitName = findViewById(R.id.et_unit);

        onClick();
        getProducts();
        initRecyclerView();
        addUnit();
    }

    private void addUnit() {
        String unit = unitName.getText().toString();
        if (!TextUtils.isEmpty(unit)) {
            String unitId = courseDR.push().getKey();

            UnitListModel unitListModel = new UnitListModel(unit, unitId);
            courseDR.child(courseId).child("semester").child(semId).child("department").child(departmentId).child("subject").child(subjectId).setValue(unitListModel);
        } else {
            Toast.makeText(UnitListActivity.this, "Enter unit to add", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRecyclerView() {
        recyclerView.setAdapter(unitListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getProducts() {
        courseDR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                unitList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UnitListModel unitModel = snapshot.getValue(UnitListModel.class);
                    unitList.add(unitModel);
                }
                unitListAdapter.notifyDataSetChanged();
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
                addUnit();
            }
        });

    }
}





