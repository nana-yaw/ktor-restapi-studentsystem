package com.turntabl.repository

import com.turntabl.model.Student
import com.turntabl.model.StudentDraft

interface StudentRepository {

    fun getAllStudents(): List<Student>

    fun getStudent(id : Int): Student?

    fun addStudent(draft: StudentDraft): Student

    fun removeStudent(id: Int): Boolean

    fun updateStudent(id: Int, draft: StudentDraft): Boolean
}