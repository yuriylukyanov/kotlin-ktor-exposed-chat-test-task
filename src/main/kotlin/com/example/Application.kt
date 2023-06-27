package com.example

import com.example.database.DatabaseFactory
import com.example.exception.BadRequestException
import io.ktor.server.application.*
import com.example.plugins.*
import com.example.repository.ChatRepository
import com.example.repository.MessageRepository
import com.example.repository.UserRepository
import com.example.routing.configureChatRouting
import com.example.routing.configureMessageRouting
import com.example.routing.configureUserRouting
import com.example.service.ChatService
import com.example.service.MessageService
import com.example.service.UserService
import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module() {
    DatabaseFactory.init()


    configureHTTP()
    configureSerialization()
    configureRouting()
    configureUserRouting(UserService(UserRepository()))
    configureChatRouting(ChatService(UserRepository(), ChatRepository()))
    configureMessageRouting(MessageService(ChatRepository(), MessageRepository()))
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            if (cause is BadRequestException) {
                call.respondText(text = cause.message!!, status = HttpStatusCode.BadRequest)
            } else {
                call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
            }
        }
    }
}
