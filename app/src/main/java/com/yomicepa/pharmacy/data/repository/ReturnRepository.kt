package com.yomicepa.pharmacy.data.repository

import com.yomicepa.pharmacy.data.model.CreateReturnRequest
import com.yomicepa.pharmacy.data.model.CreateReturnRequestResponse
import com.yomicepa.pharmacy.data.model.Item
import com.yomicepa.pharmacy.data.model.ReturnRequest
import com.yomicepa.pharmacy.data.model.ReturnRequestDetail
import com.yomicepa.pharmacy.data.model.ReturnRequestsResponse
import com.yomicepa.pharmacy.data.network.ApiService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReturnRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun listReturnRequests(pharmacyId: String,pageNumber: Int): Response<ReturnRequestsResponse> = apiService.listReturnRequests(pharmacyId, pageNumber =pageNumber )
    suspend fun createReturnRequest(pharmacyId: String, request: CreateReturnRequest): Response<CreateReturnRequestResponse> = apiService.createReturnRequest(pharmacyId, request)
    suspend fun getReturnRequests(pharmacyId: String,returnRequestId: String): Response<ReturnRequestDetail> {
        // Call to the API service to get return requests
        return apiService.getReturnRequest(pharmacyId,returnRequestId)
    }

}