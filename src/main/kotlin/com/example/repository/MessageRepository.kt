package com.example.repository

import com.example.database.MessageTable
import com.example.dto.AddMessage
import com.example.dto.GetMessage
import com.example.model.Message
import org.jetbrains.exposed.sql.SortOrder
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

    suspend fun get(dto: GetMessage): Collection<Message> {
        return transaction {
            MessageTable.select{
                MessageTable.chatId eq dto.chat!!
            }
                .map { Message.wrapRow(it) }
            .toList()
        }
    }

    suspend fun countByChatIdAndText(chat: UUID, text: String): Long {
        return transaction {
                MessageTable.select {
                    MessageTable.chatId eq chat
                    MessageTable.text like ("%${text.lowercase()}%")
            }
            .orderBy(MessageTable.createdAt, SortOrder.DESC)
            .count()
        }
    }
}