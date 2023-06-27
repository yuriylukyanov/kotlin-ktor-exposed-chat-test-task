package com.example.repository

import com.example.database.ChatMemberTable
import com.example.database.MessageTable
import com.example.database.UserTable
import com.example.dto.AddMessage
import com.example.dto.GetMessage
import com.example.dto.MessageResponse
import com.example.model.Message
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.util.*

class MessageRepository {
    suspend fun save(dto: AddMessage, chatMemberId: UUID): Message {
        return transaction {
            Message.new(UUID.randomUUID()) {
                this.text = dto.text!!
                this.createdAt = DateTime.now(DateTimeZone.UTC)
                this.chatId = dto.chat!!
                this.authorId = chatMemberId
            }
        }
    }

    suspend fun get(dto: GetMessage): Collection<MessageResponse> {
        return transaction {
            MessageTable
                .innerJoin(ChatMemberTable)
                .innerJoin(UserTable)
                .slice(
                    MessageTable.id,
                    MessageTable.text,
                    MessageTable.createdAt,
                    UserTable.username,
                    UserTable.id
                )
                .select {
                    MessageTable.chatId eq dto.chat!!
                }
                .orderBy(MessageTable.createdAt, SortOrder.DESC)
                .map {
                    MessageResponse(
                        id = it[MessageTable.id].value,
                        text = it[MessageTable.text],
                        createdAt = it[MessageTable.createdAt],
                        userId = it[UserTable.id].value,
                        username = it[UserTable.username]
                    )
                }
            .toList()
        }
    }

    suspend fun countByChatIdAndText(chat: UUID, text: String): Long {
        return transaction {
                MessageTable.select {
                    MessageTable.chatId eq chat and(
                            MessageTable.text like ("%${text.lowercase()}%"))
            }
            .count()
        }
    }
}