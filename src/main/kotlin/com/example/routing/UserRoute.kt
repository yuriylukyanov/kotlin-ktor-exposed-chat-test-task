package com.example.routing

import com.example.dto.AddUser
import com.example.plugins.City
import com.example.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureUserRouting(service: UserService) {
    routing {
        route("/users") {
            post("/add") {
                val user = call.receive<AddUser>()
                val id = service.create(user)
                call.respond(HttpStatusCode.Created, id)
            }
        }
    }
}