package com.yomicepa.pharmacy.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(val username: String, val password: String)
