package com.thaitran81.studentmanagementappv3.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update

@Dao
public interface StudentDAO {

    @Query("Select * from student")
    fun getStudentList() : List<StudentModel>

    @Insert
    fun insertStudent(student : StudentModel)

    @Update( onConflict = REPLACE)
    fun updateStudent(Student: StudentModel)

    @Query("DELETE FROM student WHERE id = :studentId")
    fun deleteByUserId(studentId: Int)

    @Query("SELECT * FROM student WHERE id = :studentId")
    fun getStudentById(studentId: Int): StudentModel
}