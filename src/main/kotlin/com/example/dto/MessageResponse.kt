package com.example.dto

import org.joda.time.DateTime
import java.util.*

data class MessageResponse(
    val id: UUID,
    val text: String,
    val createdAt: DateTime,
    val username: String,
    val userId: UUID
)