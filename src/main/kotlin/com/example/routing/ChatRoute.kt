package com.example.routing

import com.example.dto.AddChat
import com.example.dto.GetChat
import com.example.service.ChatService
import com.example.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.inject

fun Application.configureChatRouting() {
    routing {
        route("chats") {
            val service: ChatService by inject(ChatService::class.java)
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