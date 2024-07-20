package com.yomicepa.pharmacy.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yomicepa.pharmacy.data.model.AddItemResponse
import com.yomicepa.pharmacy.data.model.Item
import com.yomicepa.pharmacy.data.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(private val repository: ItemRepository) : ViewModel() {

    private val _items = MutableStateFlow<Response<List<Item>>?>(null)
    val items: StateFlow<Response<List<Item>>?> get() = _items

    private val _addItem = MutableStateFlow<Response<AddItemResponse>?>(null)
    val addItem: StateFlow<Response<AddItemResponse>?> get() = _addItem

    private val _deleteItem = MutableStateFlow<Response<ResponseBody>?>(null)
    val deleteItem: StateFlow<Response<ResponseBody>?> get() = _deleteItem


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage


    fun listItems(pharmacyId: String,returnRequestId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response: Response<List<Item>> = repository.listItems(pharmacyId,returnRequestId)
                if (response.isSuccessful) {
                    _items.value = response
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Error: ${response.code()}"
                    _items.value = null
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _items.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addItem(pharmacyId: String, returnRequestId: String,item: Item) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response :Response<AddItemResponse> = repository.addItem(pharmacyId = pharmacyId, returnRequestId = returnRequestId, item = item)
                if (response.isSuccessful) {
                    _addItem.value = response
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Error: ${response.code()}"
                    _addItem.value = null
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _addItem.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun updateItem(pharmacyId: String, returnRequestId: String,item: Item) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response :Response<AddItemResponse> = repository.updateItem(pharmacyId = pharmacyId, returnRequestId = returnRequestId, item = item)
                if (response.isSuccessful) {
                    _addItem.value = response
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Error: ${response.code()}"
                    _addItem.value = null
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _items.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun deleteItem(pharmacyId: String, returnRequestId: String,item: Item) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response :Response<ResponseBody> = repository.deleteItem(pharmacyId = pharmacyId, returnRequestId = returnRequestId, item = item)
                if (response.isSuccessful) {
                    _deleteItem.value = response
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Error: ${response.code()}"
                    _deleteItem.value = null
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _deleteItem.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }






}
