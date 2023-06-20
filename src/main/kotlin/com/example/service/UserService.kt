package com.example.service

import com.example.dto.AddUser
import com.example.exception.BadRequestException
import com.example.model.User
import com.example.repository.UserRepository
import java.time.OffsetDateTime
import java.util.*

class UserService(val repository: UserRepository) {
    suspend fun create(dto: AddUser): UUID {
        if (dto.username.isNullOrEmpty()) {
            throw BadRequestException("empty username")
        }

        if (!repository.existsByUsername(dto.username)){
            val user = User(
                UUID.randomUUID(),
                dto.username.lowercase(),
                OffsetDateTime.now()
            );
            repository.create(user);
            return user.id;
        } else {
            throw BadRequestException("username " + dto.username + " already exists");
        }

    }
}