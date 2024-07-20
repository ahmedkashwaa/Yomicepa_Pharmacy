package com.yomicepa.pharmacy.data.network

import com.yomicepa.pharmacy.data.model.AddItemResponse
import com.yomicepa.pharmacy.data.model.AuthResponse
import com.yomicepa.pharmacy.data.model.CreateReturnRequest
import com.yomicepa.pharmacy.data.model.CreateReturnRequestResponse
import com.yomicepa.pharmacy.data.model.Item
import com.yomicepa.pharmacy.data.model.PharmacyDetailResponse
import com.yomicepa.pharmacy.data.model.PharmacyItem
import com.yomicepa.pharmacy.data.model.ReturnRequest
import com.yomicepa.pharmacy.data.model.ReturnRequestDetail
import com.yomicepa.pharmacy.data.model.ReturnRequestsResponse
import com.yomicepa.pharmacy.data.model.SignInRequest
import com.yomicepa.pharmacy.data.model.Wholesaler
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("auth")
    suspend fun login(@Body user: SignInRequest): AuthResponse

    @GET("pharmacies/management")
    suspend fun listPharmacies() : Response<List<PharmacyItem>>

    @GET("pharmacies/{pharmacyId}/full")
    suspend fun getPharmacyDetail(@Path("pharmacyId") pharmacyId: String): Response<PharmacyDetailResponse>

    @POST("pharmacies/{pharmacyId}/returnrequests")
    suspend fun createReturnRequest(
        @Path("pharmacyId") pharmacyId: String,
        @Body request: CreateReturnRequest
    ): Response<CreateReturnRequestResponse>


    @GET("pharmacies/{pharmacyId}/returnrequests")
    suspend fun listReturnRequests(@Path("pharmacyId") pharmacyId: String,@Query("page") pageNumber: Int = 0): Response<ReturnRequestsResponse>

    @GET("pharmacies/{pharmacyId}/returnrequests/{returnRequestId}")
    suspend fun getReturnRequest(@Path("pharmacyId") pharmacyId: String, @Path("returnRequestId") returnRequestId: String): Response<ReturnRequestDetail>

    @GET("pharmacies/{pharmacyId}/wholesalers")
    suspend fun listWholesalers(@Path("pharmacyId") pharmacyId: String): Response<List<Wholesaler>>



    @POST("pharmacies/{pharmacyId}/returnrequests/{returnRequestId}/items")
    suspend fun addItem(@Path("pharmacyId") pharmacyId: String, @Path("returnRequestId") returnRequestId: String,@Body item: Item) : Response<AddItemResponse>

    @PUT("pharmacies/{pharmacyId}/returnrequests/{returnRequestId}/items/{itemId}")
    suspend fun updateItem(@Path("pharmacyId") pharmacyId: String, @Path("returnRequestId") returnRequestId: String,@Path("itemId") itemId: String,@Body item: Item) : Response<AddItemResponse>

    @DELETE("pharmacies/{pharmacyId}/returnrequests/{returnRequestId}/items/{itemId}")
    suspend fun deleteItem(@Path("pharmacyId") pharmacyId: String, @Path("returnRequestId") returnRequestId: String,@Path("itemId") itemId: String) : Response<ResponseBody>


    @GET("pharmacies/{pharmacyId}/returnrequests/{returnRequestId}/items")
    suspend fun listItems(@Path("pharmacyId") pharmacyId: String, @Path("returnRequestId") returnRequestId: String): Response<List<Item>>



}
