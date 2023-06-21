package com.example.repository

import com.example.database.UserTable
import com.example.dto.UpdateUser
import com.example.model.User
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

class UserRepository {
    suspend fun create(user: User) {
        transaction {
            UserTable.insert {
                it[id] = user.id
                it[username] = user.username
                it[createdAt] = user.createdAt.toInstant()
            }
        }
    }

    suspend fun update(user: UpdateUser) {
        transaction {
            val usernameLowercase = user.username!!.lowercase()
            UserTable.update({UserTable.id eq user.id!!})
            {
                it[username] = usernameLowercase
            }
        }
    }

    suspend fun existsByUsername(username: String): Boolean {
        return transaction {
            val usernameLowercase = username.lowercase()
            UserTable.select { UserTable.username.lowerCase() eq usernameLowercase }.count() != 0L;
        }
    }

    suspend fun existsById(id: UUID): Boolean {
        return transaction {
            UserTable.select { UserTable.id eq id }.count() != 0L;
        }
    }
}