package com.yomicepa.pharmacy.data.model

data class ReturnRequestDetail(
    val id: Int,
    val createdAt: String,
    val updatedAt: String,
    val pharmacy: Pharmacy,
    val dateDispatched: String?,
    val dateFulfilled: String?,
    val disbursements: Any?,
    val serviceFee: Any?,
    val returnRequestStatus: String,
    val returnRequestStatusLabel: String,
    val serviceType: String,
    val preferredDate: String?
)


