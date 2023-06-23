package com.example.routing

import com.example.dto.AddChat
import com.example.dto.GetChat
import com.example.service.ChatService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureChatRouting(service: ChatService) {
    routing {
        route("chats") {
            post("add") {
                val dto = call.receive<AddChat>()
                val id = service.create(dto)
                call.respond(HttpStatusCode.OK, id)
            }

            post("get") {
                val dto = call.receive<GetChat>()
                val result = service.get(dto)
                call.respond(HttpStatusCode.OK, result)
            }
        }
    }
}