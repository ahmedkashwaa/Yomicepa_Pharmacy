package com.yomicepa.pharmacy.data.repository

import com.yomicepa.pharmacy.data.model.AddItemResponse
import com.yomicepa.pharmacy.data.model.Item
import com.yomicepa.pharmacy.data.network.ApiService
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class ItemRepository@Inject constructor(private val apiService: ApiService) {
    suspend fun listItems(pharmacyId: String, returnRequestId: String)= apiService.listItems(pharmacyId =pharmacyId , returnRequestId = returnRequestId)
    suspend fun addItem(pharmacyId: String, returnRequestId: String, item: Item) : Response<AddItemResponse> = apiService.addItem(pharmacyId =pharmacyId , returnRequestId = returnRequestId, item = item)
    suspend fun updateItem(pharmacyId: String, returnRequestId: String, item: Item) : Response<AddItemResponse> = apiService.updateItem(pharmacyId =pharmacyId , returnRequestId = returnRequestId, itemId = item.id?:"",item = item)
    suspend fun deleteItem(pharmacyId: String, returnRequestId: String, item: Item) : Response<ResponseBody> = apiService.deleteItem(pharmacyId =pharmacyId , returnRequestId = returnRequestId, itemId = item.id?:"")
}