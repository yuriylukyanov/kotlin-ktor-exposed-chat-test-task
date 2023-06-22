package com.example.model

import com.example.database.ChatMemberTable
import com.example.database.ChatTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import java.util.*

class Chat(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<Chat>(ChatTable)
    var name by ChatTable.name
    var createdAt by ChatTable.createdAt

    var users by User via ChatMemberTable
}

