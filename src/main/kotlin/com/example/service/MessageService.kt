package com.example.service

import com.example.dto.AddMessage
import com.example.dto.EntryCountMessage
import com.example.dto.GetMessage
import com.example.dto.MessageResponse
import com.example.exception.BadRequestException
import com.example.repository.ChatRepository
import com.example.repository.MessageRepository
import java.util.*

class MessageService(
    val chatRepository: ChatRepository,
    val messageRepository: MessageRepository
) {
    suspend fun create(dto: AddMessage): UUID {
        if (dto.author == null) throw BadRequestException("empty author")
        if (dto.chat == null) throw BadRequestException("empty chat")
        if (dto.text.isNullOrEmpty()) throw BadRequestException("empty text")
        val chatIds = chatRepository.findAllIdsByIds(listOf(dto.chat))
        if (chatIds.isEmpty()) throw BadRequestException("chat not found")
        val memberIds = chatRepository.getMemberIdsByChatIdAndUserIds(dto.chat, listOf(dto.author))
        if (memberIds.isEmpty()) throw BadRequestException("author not found")
        val message = messageRepository.save(dto, memberIds.first())
        return message.id.value
    }

    suspend fun get(dto: GetMessage): Collection<MessageResponse> {
        if (dto.chat == null) throw BadRequestException("empty chat")
        val messages = messageRepository.get(dto);
        return messages
    }

    suspend fun entryCount(dto: EntryCountMessage): Long {
        if (dto.chat == null) throw BadRequestException("empty chat")

        val text = if (dto.text.isNullOrEmpty()) "" else dto.text;
        return messageRepository.countByChatIdAndText(dto.chat, text)
    }
}