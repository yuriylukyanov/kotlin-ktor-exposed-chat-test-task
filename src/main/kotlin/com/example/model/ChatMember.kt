package com.example.model

import com.example.database.ChatMemberTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ChatMember(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<ChatMember>(ChatMemberTable)
    var userId by ChatMemberTable.userId
    var chatId by ChatMemberTable.chatId

    var user by User referencedOn ChatMemberTable.userId
}