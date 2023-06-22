package com.example.database

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.jodatime.datetime

object ChatTable: UUIDTable() {
    val name = varchar("name", 255)
    val createdAt = datetime("created_at")
}