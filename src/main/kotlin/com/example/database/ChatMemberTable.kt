package com.example.database

import org.jetbrains.exposed.dao.id.UUIDTable

object ChatMemberTable: UUIDTable() {
    val userId = reference("user_id", UserTable)
    val chatId = reference("chat_id", ChatTable)

    val usernameUniqueIndex = uniqueIndex(userId, chatId)
}

