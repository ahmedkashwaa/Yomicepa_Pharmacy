package com.yomicepa.pharmacy.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yomicepa.pharmacy.data.model.AuthResponse
import com.yomicepa.pharmacy.data.model.SignInRequest
import com.yomicepa.pharmacy.data.repository.AuthRepository
import com.yomicepa.pharmacy.interceptor.AuthInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository,private val authInterceptor: AuthInterceptor) : ViewModel() {
    private val _authResponse = mutableStateOf<AuthResponse?>(null)
    val authResponse: State<AuthResponse?> get() = _authResponse

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> get() = _errorMessage

    fun login(user: SignInRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.login(user)
                authInterceptor.setToken(response.token)
                _authResponse.value = response
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _authResponse.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
}
