package com.yomicepa.pharmacy.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yomicepa.pharmacy.data.model.CreateReturnRequest
import com.yomicepa.pharmacy.data.model.CreateReturnRequestResponse
import com.yomicepa.pharmacy.data.model.PharmacyDetailResponse
import com.yomicepa.pharmacy.data.model.ReturnRequest
import com.yomicepa.pharmacy.data.model.ReturnRequestsResponse
import com.yomicepa.pharmacy.data.model.Wholesaler
import com.yomicepa.pharmacy.data.repository.PharmacyRepository
import com.yomicepa.pharmacy.data.repository.ReturnRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PharmacyDetailViewModel @Inject constructor(
    private val repository: PharmacyRepository,
    private val returnRepository: ReturnRepository
) : ViewModel() {

    private val _pharmacyDetail = MutableStateFlow<PharmacyDetailResponse?>(null)
    val pharmacyDetail: StateFlow<PharmacyDetailResponse?> get() = _pharmacyDetail

    private val _pharmacyWholesalers = MutableStateFlow<Response<List<Wholesaler>>?>(null)
    val pharmacyWholesalers: StateFlow<Response<List<Wholesaler>>?> get() = _pharmacyWholesalers


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage


    private val _returnRequests = MutableStateFlow<Response<ReturnRequestsResponse>?>(null)
    val returnRequests: StateFlow<Response<ReturnRequestsResponse>?> get() = _returnRequests

    private val _createReturnRequests = MutableStateFlow<Response<CreateReturnRequestResponse>?>(null)
    val createReturnRequests: StateFlow<Response<CreateReturnRequestResponse>?> get() = _createReturnRequests

    private val _nextPage = MutableStateFlow(0)
    val nextPage: StateFlow<Int> get() = _nextPage

    fun fetchPharmacyDetail(pharmacyId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response: Response<PharmacyDetailResponse> = repository.getPharmacyDetail(pharmacyId)
                if (response.isSuccessful) {
                    _pharmacyDetail.value = response.body()
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Error: ${response.code()}"
                    _pharmacyDetail.value = null
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _pharmacyDetail.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun listPharmacyWholesalers(pharmacyId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response: Response<List<Wholesaler>> = repository.listWholesalers(pharmacyId)
                if (response.isSuccessful) {
                    _pharmacyWholesalers.value = response
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Error: ${response.code()}"
                    _pharmacyWholesalers.value = null
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _pharmacyWholesalers.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }



    fun getReturnRequests(pharmacyId: String, page: Int = 0) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = returnRepository.listReturnRequests(pharmacyId, pageNumber = page)
                val currentRequests = _returnRequests.value?.body()?.content ?: emptyList() // Get existing items
                _returnRequests.value = response.also {
                    // Append new items to existing list
                    it.body()?.content = (currentRequests + it.body()?.content.orEmpty()).toMutableList()
                }

                if (response.body()?.last == false) {
                    _nextPage.value++
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun createReturnRequests(request: CreateReturnRequest, pharmacyId: String,onResponse : (Int)->Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val requests : Response<CreateReturnRequestResponse> = returnRepository.createReturnRequest(pharmacyId = pharmacyId, request = request)
                _createReturnRequests.value = requests
                onResponse(requests.body()?.id?:0)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _createReturnRequests.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }


}
