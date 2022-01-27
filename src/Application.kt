package com.turntabl

import com.turntabl.model.StudentDraft
import com.turntabl.model.StudentUpdateDto
import com.turntabl.repository.MySQLStudentRepository
import com.turntabl.repository.StudentRepository
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {

    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {

        val repository:StudentRepository = MySQLStudentRepository()

        get("/"){
            call.respondText("Hello StudentList")
        }

        get("/students") {
            call.respond(repository.getAllStudents())
        }

        get("/students/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "id parameter has to be a number."
                )
                return@get
            }

            val student = repository.getStudent(id)

            if (student == null) {
                call.respond(
                    HttpStatusCode.NotFound,
                    "Student not found for provided id $id"
                )
            } else{
                call.respond(student)
            }
        }

        post("/students") {
            val studentDraft = call.receive<StudentDraft>()
            val student = repository.addStudent(studentDraft)
            call.respond(student)

        }

        put("/students/{id}") {
            val studentDraft = call.receive<StudentDraft>()
            val studentId = call.parameters["id"]?.toIntOrNull()

            if (studentId == null){
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Id parameter is null or not a number! Check id $studentId"
                )
                return@put
            }
            val updated = repository.updateStudent(studentId, studentDraft)

            if (updated){
                call.respond(HttpStatusCode.OK)
            } else{
                call.respond(
                    HttpStatusCode.NotFound,
                    "Found no Student with the id $studentId"
                )
            }
        }

        patch("/students/{id}") {
            val studentDraft = call.receive<StudentUpdateDto>()
            val studentId = call.parameters["id"]?.toIntOrNull()

            if (studentId == null){
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Id parameter is null or not a number! Check id $studentId"
                )
                return@patch
            }
            val patched = repository.patchStudent(studentId, studentDraft)

            if (patched){
                call.respond(HttpStatusCode.OK)
            } else{
                call.respond(
                    HttpStatusCode.NotFound,
                    "Found no Student with the id $studentId"
                )
            }
        }

        delete("/students/{id}") {
            val studentId = call.parameters["id"]?.toIntOrNull()

            if (studentId == null){
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Id parameter is null or not a number! Check id $studentId"
                )
                return@delete
            }

            val removed = repository.removeStudent(studentId)

            if (removed){
                call.respond(HttpStatusCode.OK)
            } else{
                call.respond(
                    HttpStatusCode.NotFound,
                    "Found no Student with the id $studentId"
                )
            }
        }
    }
}

