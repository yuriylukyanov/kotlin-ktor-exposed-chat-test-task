package com.example.repository

import com.example.database.UserTable
import com.example.model.User
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
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

    suspend fun existsByUsername(username: String): Boolean {
        return transaction {
            val usernameLowercase = username.lowercase()
            UserTable.select { UserTable.username.lowerCase() like usernameLowercase }.count() != 0L;
        }
    }
}