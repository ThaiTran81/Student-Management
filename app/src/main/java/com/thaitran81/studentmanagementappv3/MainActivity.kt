package com.thaitran81.studentmanagementappv3

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thaitran81.studentmanagementappv3.model.DbModeApp
import com.thaitran81.studentmanagementappv3.model.StudentDatabase
import com.thaitran81.studentmanagementappv3.model.StudentModel
import java.lang.Math.abs

class MainActivity : AppCompatActivity(), StudentAdapter.OnItemClickListener {
    lateinit var db: StudentDatabase
    lateinit var rvStudentList: RecyclerView
    lateinit var btnAddStudent: Button
    lateinit var btnChangeLayout: Button
    lateinit var atFindStudent: AutoCompleteTextView
    var studentList: List<StudentModel>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_list)
        db = StudentDatabase.getInstance(this)
//kokoko

        atFindStudent = findViewById(R.id.atFindStudent)
        atFindStudent.threshold = 0
        atFindStudent.setOnItemClickListener { adapterView, view, i, l ->
            val selected = adapterView.getItemAtPosition(i) as StudentModel
            openDetailActivity(selected.id)
        }

        rvStudentList = findViewById(R.id.rvStudent)


        bindData()

        btnAddStudent = findViewById(R.id.btnOpenAddStudent)
        btnAddStudent.setOnClickListener {
            val intent = Intent(applicationContext, AddStudentActivity::class.java)
            startActivityForResult(intent, 1111)

        }

        btnChangeLayout = findViewById(R.id.btnChangeLayout)
        btnChangeLayout.setOnClickListener {
            DbModeApp.LAYOUT_MODE = abs(DbModeApp.LAYOUT_MODE - 1)
            bindData()
        }
    }

    override fun onItemClick(position: Int) {
        openDetailActivity(studentList!![position].id)
    }

    fun bindData() {
        studentList = db.studentDao().getStudentList()
        // Adapter class is initialized and list is passed in the param.
        val itemAdapter = StudentAdapter(this, studentList!!, this)
        // adapter instance is set to the recyclerview to inflate the items.
        rvStudentList.adapter = itemAdapter
        if (DbModeApp.LAYOUT_MODE == 0) rvStudentList.layoutManager = LinearLayoutManager(this)
        else rvStudentList.layoutManager = GridLayoutManager(this, 2)

        val nameSugestion = ArrayList<String>()
        for (i in 0 until studentList!!.size) {
            nameSugestion.add(studentList!![i].fullname)
        }
        val adapter = StudentListSearchAdapter(this, db.studentDao().getStudentList())

//        atFindStudent.setAdapter( ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nameSugestion))
        atFindStudent.setAdapter(adapter)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1111) {
            if (resultCode == Activity.RESULT_OK) {
                bindData()
            }
        } else if (requestCode == 1112) {
            if (resultCode == Activity.RESULT_OK) {
                bindData()
            }
        }
    }
    fun openDetailActivity(studentId: Int){
        val intent = Intent(applicationContext, StudentDetailActivity::class.java)
//            intent.putExtra("students", DAO.studentLst)
        intent.putExtra("studentId", studentId)
        startActivityForResult(intent, 1111)
    }
}