package com.thaitran81.studentmanagementappv3.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(StudentModel::class), version = 1)
abstract class StudentDatabase: RoomDatabase() {
    abstract fun studentDao(): StudentDAO
    companion object{
        val DB_NAME = "student_db"
        private var instance: StudentDatabase? = null

        fun getInstance(context: Context): StudentDatabase {
            return StudentDatabase.instance ?: StudentDatabase.buildDatabase(context)
                .also { StudentDatabase.instance = it}
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, StudentDatabase::class.java,
                StudentDatabase.DB_NAME
            ).allowMainThreadQueries().build()

    }
}
