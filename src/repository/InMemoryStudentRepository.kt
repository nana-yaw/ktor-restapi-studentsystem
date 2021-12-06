package com.turntabl.repository

import com.turntabl.model.Student
import com.turntabl.model.StudentDraft

class InMemoryStudentRepository: StudentRepository {

    val students = mutableListOf<Student>()

    override fun getAllStudents(): List<Student> {
        return students
    }

    override fun getStudent(id: Int): Student? {
        return students.firstOrNull { it.id == id }
    }

    override fun addStudent(draft: StudentDraft): Student {

        val student = Student(
            id = students.size + 1,
            name = draft.name,
            address = draft.address
        )
        students.add(student)

        return student
    }

    override fun removeStudent(id: Int): Boolean {
        return students.removeIf { it.id == id }
    }

    override fun updateStudent(id: Int, draft: StudentDraft): Boolean {
        var student = students.firstOrNull { it.id == id }

        if (student == null){
            return false
        }

        student.name = draft.name
        student.address = draft.address

        return true
    }
}