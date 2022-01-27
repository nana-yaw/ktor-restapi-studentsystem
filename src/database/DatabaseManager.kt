package com.turntabl.database

import com.turntabl.model.Student
import com.turntabl.model.StudentDraft
import com.turntabl.model.StudentUpdateDto
import org.ktorm.database.Database
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insertAndGenerateKey
import org.ktorm.dsl.update
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class DatabaseManager {

    // config
    private val hostname = "localhost"
    private val databaseName = "fullstack"
    private val username = "root"
    private val password = ""

    //database
    private val ktormDatabase: Database

    init {
        val jdbcUrl = "jdbc:mysql://$hostname:3306/$databaseName?user=$username&password=$password&useSSL=false&serverTimezone=UTC"
        ktormDatabase = Database.connect(jdbcUrl)
    }

    fun getAllStudents(): List<DBStudentEntity> {
        return ktormDatabase.sequenceOf(DBStudentTable).toList()
    }

    fun getStudent(id: Int): DBStudentEntity? {
        return ktormDatabase.sequenceOf(DBStudentTable).firstOrNull{ it.id eq id }
    }

    fun addStudent(draft: StudentDraft): Student {
       val insertedId =  ktormDatabase.insertAndGenerateKey(DBStudentTable) {
            set(DBStudentTable.name, draft.name)
            set(DBStudentTable.address, draft.address)
        } as Int

        return Student(insertedId, draft.name, draft.address)
    }

    fun updateStudent(id: Int, draft: StudentDraft): Boolean {
        val updatedRows = ktormDatabase.update(DBStudentTable) {
            set(DBStudentTable.name, draft.name)
            set(DBStudentTable.address, draft.address)
            where {
                it.id eq id
            }
        }

        return updatedRows > 0
    }

    fun patchStudent(id: Int, draft: StudentUpdateDto): Boolean {
        val record = getStudent(id) ?: return false
        val updatedRows = ktormDatabase.update(DBStudentTable) {
            val name = if (draft.name == "") record.name else draft.name
            val address = if (draft.address == "") record.address else draft.address
            set(DBStudentTable.name, name)
            set(DBStudentTable.address, address)
            where {
                it.id eq id
            }
        }

        return updatedRows > 0
    }

    fun removeStudent(id: Int): Boolean {
        val deletedRows = ktormDatabase.delete(DBStudentTable) {
            it.id eq id
        }
        return deletedRows > 0
    }

}