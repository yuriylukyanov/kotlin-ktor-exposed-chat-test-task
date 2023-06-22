package com.example.model

import com.example.database.ChatMemberTable
import com.example.database.UserTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.time.Instant
import java.time.OffsetDateTime
import java.util.*

class User(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object: UUIDEntityClass<User>(UserTable)
    var username by UserTable.username
    var createdAt by UserTable.createdAt
    var chats by Chat via ChatMemberTable
}