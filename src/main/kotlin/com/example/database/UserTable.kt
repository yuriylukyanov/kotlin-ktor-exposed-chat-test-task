package com.example.database

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.jodatime.datetime

object UserTable: UUIDTable() {
    val username = varchar("username", 255)
    val createdAt = datetime("created_at")

    val usernameUniqueIndex = uniqueIndex(username)
}

