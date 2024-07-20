package com.yomicepa.pharmacy.data.repository

import com.yomicepa.pharmacy.data.model.AuthResponse
import com.yomicepa.pharmacy.data.model.SignInRequest
import com.yomicepa.pharmacy.data.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

// AuthRepository.kt
@Singleton
class AuthRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun login(user: SignInRequest): AuthResponse = apiService.login(user)
}
