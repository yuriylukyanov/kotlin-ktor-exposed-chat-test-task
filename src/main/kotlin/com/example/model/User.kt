package com.example.model

import java.time.OffsetDateTime
import java.util.UUID

data class User(val id: UUID, val username: String, val createdAt: OffsetDateTime)
