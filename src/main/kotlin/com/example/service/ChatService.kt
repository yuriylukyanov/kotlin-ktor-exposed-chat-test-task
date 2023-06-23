package com.example.service

import com.example.dto.AddChat
import com.example.dto.ChatResponse
import com.example.dto.GetChat
import com.example.exception.BadRequestException
import com.example.model.Chat
import com.example.repository.ChatRepository
import com.example.repository.UserRepository
import java.util.*

class ChatService(val userRepository: UserRepository, val chatRepository: ChatRepository) {
    suspend fun create(dto: AddChat): UUID {
        if (dto.name.isNullOrEmpty()) throw BadRequestException("empty name")
        if (dto.users.isNullOrEmpty()) throw BadRequestException("empty users")

        val users = userRepository.findByIdIn(dto.users)
        if (users.count() != dto.users.size) throw BadRequestException("users not found")

        val chat = chatRepository.save(dto.name, users)

        return chat.id.value;
    }

    suspend fun get(dto: GetChat): List<ChatResponse> {
        if(dto.user == null)
            throw BadRequestException("empty user")
        return chatRepository.getByMemberUserId(dto)
            .map {ChatResponse(it.id.value, it.name)}
            .toList();
    }
}