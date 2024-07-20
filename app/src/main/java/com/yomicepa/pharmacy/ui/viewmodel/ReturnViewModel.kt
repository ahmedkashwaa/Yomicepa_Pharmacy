package com.yomicepa.pharmacy.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yomicepa.pharmacy.data.model.Item
import com.yomicepa.pharmacy.data.model.ReturnRequest
import com.yomicepa.pharmacy.data.model.ReturnRequestDetail
import com.yomicepa.pharmacy.data.repository.ReturnRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class ReturnViewModel @Inject constructor(private val repository: ReturnRepository) : ViewModel() {
    private val _returnRequests = MutableStateFlow<Response<ReturnRequestDetail>?>(null)
    val returnRequests: StateFlow<Response<ReturnRequestDetail>?> get() = _returnRequests

    private val _items = mutableStateOf<List<Item>>(emptyList())
    val items: State<List<Item>> get() = _items

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> get() = _errorMessage

    fun getReturnRequests(pharmacyId: String,returnRequestId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val requests = repository.getReturnRequests(pharmacyId,returnRequestId)
                _returnRequests.value = requests
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _returnRequests.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

}