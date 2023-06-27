package com.example.model

import com.example.database.ChatMemberTable
import com.example.database.MessageTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class Message(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<Message>(MessageTable)
    var text by MessageTable.text
    var createdAt by MessageTable.createdAt
    var authorId by MessageTable.authorId
    var chatId by MessageTable.chatId

    var chatMember by ChatMember referencedOn MessageTable.authorId
}