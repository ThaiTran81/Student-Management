package com.thaitran81.studentmanagementappv3.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "student")
data class StudentModel(
    @ColumnInfo(name = "fullname")
    var fullname: String,
    @ColumnInfo(name = "class")
    var classID: String,
    @ColumnInfo(name = "gender")
    var gender: String,
    @ColumnInfo(name = "dob")
    var dob: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    override fun toString(): String {
        return fullname
    }
}