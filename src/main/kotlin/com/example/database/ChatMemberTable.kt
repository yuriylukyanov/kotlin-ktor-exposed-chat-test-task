package com.example.database

import org.jetbrains.exposed.sql.Table

object ChatMemberTable: Table() {
    val userId = reference("user_id", UserTable)
    val chatId = reference("chat_id", ChatTable)

    val usernameUniqueIndex = uniqueIndex(userId, chatId)
}