package com.example.database

import net.perfectdreams.exposedpowerutils.sql.javatime.timestampWithTimeZone
import org.jetbrains.exposed.sql.Table

object UserTable: Table() {
    val id = uuid("id")
    val username = varchar("username", 255)
    val createdAt = timestampWithTimeZone("created_at")

    override val primaryKey = PrimaryKey(id)
    val usernameUniqueIndex = uniqueIndex(username)
}