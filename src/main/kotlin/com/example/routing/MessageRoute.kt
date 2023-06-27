package com.example.routing

import com.example.database.ChatMemberTable
import com.example.database.ChatTable
import com.example.dto.*
import com.example.service.MessageService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureMessageRouting(service: MessageService) {
    routing {
        route("messages") {
            post("add") {
                val dto = call.receive<AddMessage>()
                val id = service.create(dto)
                call.respond(HttpStatusCode.OK, id)
            }

            post("get") {
                val dto = call.receive<GetMessage>()
                val id = service.get(dto)
                call.respond(HttpStatusCode.OK, id)
            }

            post("entry/count") {
                val dto = call.receive<EntryCountMessage>()
                val id = service.entryCount(dto)
                call.respond(HttpStatusCode.OK, id)
            }
        }
    }
}