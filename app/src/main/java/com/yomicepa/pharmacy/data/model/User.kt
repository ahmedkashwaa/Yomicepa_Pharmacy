package com.yomicepa.pharmacy.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val createdAt: String,
    val updatedAt: String?,
    val username: String,
    val email: String,
    val role: String,
    val phoneNumber: String?,
    val activated: Boolean
)
