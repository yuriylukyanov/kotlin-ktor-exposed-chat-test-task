package com.example

import com.example.database.DatabaseFactory
import com.example.exception.BadRequestException
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.repository.UserRepository
import com.example.routing.configureUserRouting
import com.example.service.UserService
import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module() {
    DatabaseFactory.init()


    configureHTTP()
    configureSerialization()
    //configureDatabases()
    configureRouting()
    configureUserRouting(UserService(UserRepository()))
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
