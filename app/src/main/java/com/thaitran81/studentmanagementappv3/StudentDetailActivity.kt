package com.thaitran81.studentmanagementappv3

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.thaitran81.studentmanagementappv3.model.StudentDatabase
import com.thaitran81.studentmanagementappv3.model.StudentModel
import java.text.SimpleDateFormat
import java.util.*

class StudentDetailActivity : AppCompatActivity() {
    lateinit var spClass: Spinner
    lateinit var etDob: EditText
    lateinit var etFullname: EditText
    lateinit var rgGender: RadioGroup
    lateinit var btnDel: Button
    var studentId :Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_student)
        spClass = findViewById(R.id.spChooseClass)
        etDob = findViewById(R.id.dobEt)
        etFullname = findViewById(R.id.fullnameEt)
        rgGender = findViewById(R.id.genderRg)
        btnDel = findViewById(R.id.btnDelete)

        studentId = intent.extras?.getInt("studentId") ?: 1

        val classList = getResources().getStringArray(R.array.ClassList);
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, classList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spClass.setAdapter(adapter)

        etDob.setOnClickListener {
            clickDatePicker()
        }


        var db = StudentDatabase.getInstance(this)
        val btnSave: Button = findViewById(R.id.btnSave)
        btnSave.text = "Update"
        btnSave.setOnClickListener {
            val student = getInputStudent()
            if (validator(student!!)) {
                db.studentDao().updateStudent(student)
                Toast.makeText(
                    applicationContext,
                    "${student.fullname} is updated",
                    Toast.LENGTH_SHORT
                ).show()
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                Toast.makeText(applicationContext, "Please enter all field!!!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        btnDel.setOnClickListener {
            db.studentDao().deleteByUserId(studentId)
            setResult(Activity.RESULT_OK)
            finish()
        }

        initData()
    }

    fun validator(student: StudentModel): Boolean {
        if (student.fullname.isEmpty()
            || student.gender.isEmpty() || student.dob.isEmpty()
        ) {

            return false
        }
        return true
    }

    fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dp = DatePickerDialog(
            this,
            { view, _year, _month, _day ->
                val selectedDate = "$_day/${_month + 1}/$_year"
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                etDob?.setText(selectedDate)
                val theDate = sdf.parse(selectedDate)


            },
            year, month, day
        )

        dp.datePicker.maxDate = System.currentTimeMillis() - 84400000

        dp.show()
    }

    fun getInputStudent(): StudentModel? {
        val fullname = etFullname?.text.toString()
        val dob = etDob?.text.toString()
        val classId = spClass.selectedItem.toString()
        val gender = when (rgGender?.checkedRadioButtonId) {
            R.id.maleRb -> "Male"
            R.id.femaleRb -> "Female"
            R.id.otherRb -> "Other"
            else -> null.toString()
        }
        val student: StudentModel = StudentModel(fullname, classId, gender, dob)
        student.id = studentId
        return student
    }

    fun initData() {
        val student = StudentDatabase.getInstance(this).studentDao().getStudentById(studentId)
        etFullname!!.setText(student.fullname)
        etDob!!.setText(student.dob)
        spClass.setSelection(getClassId(student.classID))
        rgGender!!.check(R.id.maleRb)
        when(student.gender){
            "Male"-> rgGender!!.check(R.id.maleRb)
            "Female"-> rgGender!!.check(R.id.femaleRb)
            else -> rgGender!!.check(R.id.otherRb)
        }
    }

    fun getClassId(classId: String): Int{
        val lst = resources.getStringArray(R.array.ClassList)
        for (i in 0.. lst.size){
            if(lst[i].equals(classId)){
                return i
            }
        }
        return 0
    }
}