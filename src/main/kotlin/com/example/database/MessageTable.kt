package com.example.database

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.jodatime.datetime

object MessageTable: UUIDTable() {
    val text = text("text")
    val createdAt = datetime("created_at")

    val authorId = uuid("author_id").references(ChatMemberTable.id)
    val chatId = uuid("chat_id").references(ChatTable.id)
}