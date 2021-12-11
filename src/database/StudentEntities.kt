package com.turntabl.database

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object DBStudentTable: Table<DBStudentEntity>("student") {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val address = varchar("address").bindTo { it.address }
}

interface DBStudentEntity: Entity<DBStudentEntity> {

    companion object : Entity.Factory<DBStudentEntity>()

    val id: Int
    val name: String
    val address: String

}