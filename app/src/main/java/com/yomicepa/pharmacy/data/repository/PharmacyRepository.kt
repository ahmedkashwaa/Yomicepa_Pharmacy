package com.yomicepa.pharmacy.data.repository

import com.yomicepa.pharmacy.data.model.PharmacyDetailResponse
import com.yomicepa.pharmacy.data.model.PharmacyItem
import com.yomicepa.pharmacy.data.model.Wholesaler
import com.yomicepa.pharmacy.data.network.ApiService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class PharmacyRepository@Inject constructor(private val apiService: ApiService) {
    suspend fun listPharmacies(): Response<List<PharmacyItem>> = apiService.listPharmacies()
    suspend fun listWholesalers(pharmacyId: String): Response<List<Wholesaler>> = apiService.listWholesalers(pharmacyId)
    suspend fun getPharmacyDetail(pharmacyId: String): Response<PharmacyDetailResponse> = apiService.getPharmacyDetail(pharmacyId)
}
