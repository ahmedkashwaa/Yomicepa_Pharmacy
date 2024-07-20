package com.yomicepa.pharmacy.ui.viewmodel



import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yomicepa.pharmacy.data.model.PharmacyItem
import com.yomicepa.pharmacy.data.repository.PharmacyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PharmaciesViewModel @Inject constructor(
    private val repository: PharmacyRepository
) : ViewModel() {

    private val _pharmacies = MutableStateFlow<List<PharmacyItem>>(emptyList())
    val pharmacies: StateFlow<List<PharmacyItem>> get() = _pharmacies

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    init {
        fetchPharmacies()
    }

    private fun fetchPharmacies() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response: Response<List<PharmacyItem>> = repository.listPharmacies()
                if (response.isSuccessful) {
                    _pharmacies.value = response.body() ?: emptyList()
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Error: ${response.code()}"
                    _pharmacies.value = emptyList()
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _pharmacies.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
