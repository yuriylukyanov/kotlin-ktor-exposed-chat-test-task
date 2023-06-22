package com.example.service

import com.example.dto.AddUser
import com.example.dto.UpdateUser
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

            val user = repository.create(dto);
            return user.id.value;
        } else {
            throw BadRequestException("username " + dto.username + " already exists");
        }

    }

    suspend fun update(dto: UpdateUser): UUID {
        if (dto.username.isNullOrEmpty()) {
            throw BadRequestException("empty username")
        }

        if (dto.id == null) {
            throw BadRequestException("empty id")
        }

        if (!repository.existsById(dto.id)) {
            throw BadRequestException("user not found")
        }

        if (repository.existsByUsername(dto.username)) {
            throw BadRequestException("username " + dto.username + " already exists")
        }

        repository.update(dto)
        return dto.id
    }
}