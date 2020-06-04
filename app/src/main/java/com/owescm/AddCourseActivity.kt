package com.owescm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.owescm.CourseListActivity.courseDR
import com.owescm.educo.CourseListModel
import com.owescm.educo.R
import kotlinx.android.synthetic.main.activity_add_course.*

class AddCourseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        btn_add.setOnClickListener {
            val courseId = CourseListActivity.courseDR.push().key.toString()
            if (et_course_name.text.toString() != "") {
                val courseModel = CourseListModel(et_course_name.text.toString(), courseId)
                courseDR.child(courseId).setValue(courseModel).addOnCompleteListener {
                    finish()
                }
            }
        }
    }
}