package com.turntabl.repository

import com.turntabl.database.DatabaseManager
import com.turntabl.model.Student
import com.turntabl.model.StudentDraft

class MySQLStudentRepository : StudentRepository {

    private val database = DatabaseManager()

    override fun getAllStudents(): List<Student> {
       return database.getAllStudents().map {
           Student(it.id, it.name, it.address)
       }
    }

    override fun getStudent(id: Int): Student? {
        return database.getStudent(id)?.let { Student(it.id, it.name, it.address) }
    }

    override fun addStudent(draft: StudentDraft): Student {
        return database.addStudent(draft)
    }

    override fun removeStudent(id: Int): Boolean {
        return database.removeStudent(id)
    }

    override fun updateStudent(id: Int, draft: StudentDraft): Boolean {
        return database.updateStudent(id, draft)
    }
}