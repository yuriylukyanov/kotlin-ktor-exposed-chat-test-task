package com.example.repository

import com.example.database.ChatMemberTable
import com.example.database.ChatTable
import com.example.database.UserTable
import com.example.dto.GetChat
import com.example.model.Chat
import com.example.model.User
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.util.*

class ChatRepository {
    suspend fun save(name: String, users: Iterable<User>): Chat {
        return transaction {
            Chat.new(UUID.randomUUID()) {
                this.name = name
                createdAt = DateTime.now(DateTimeZone.UTC)
                this.users = SizedCollection(users.toList())
            }
        }
    }

    suspend fun getByMemberUserId(dto: GetChat): List<Chat> {
        return transaction {
            val query = ChatMemberTable.innerJoin(ChatTable)
                .slice(ChatTable.columns)
                .select {
                    ChatMemberTable.userId eq dto.user
                }
                .withDistinct()
            Chat.wrapRows(query).toList()
        }
    }

    fun findAllIdsByIds(ids: List<UUID>): List<UUID> {
        return transaction {
            ChatTable.slice(ChatTable.id)
                .select {
                    ChatTable.id inList(ids)
                }
                .map { it[ChatTable.id].value }
                .toList()
        }
    }

    suspend fun getMemberIdsByChatIdAndUserIds(chatId: UUID, userIds: Collection<UUID>): List<UUID> {
        return transaction {
            ChatMemberTable.slice(ChatMemberTable.id)
                .select {
                    ChatMemberTable.chatId eq chatId
                    ChatMemberTable.userId inList(userIds)
                }
                .map { it[ChatMemberTable.id].value }
                .toList()
        }
    }
}

