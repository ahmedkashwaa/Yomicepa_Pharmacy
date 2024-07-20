package com.yomicepa.pharmacy.data.model

data class CreateReturnRequestResponse(
    val createdAt: String,
    val dateDispatched: Any,
    val dateFulfilled: Any,
    val disbursements: Any,
    val id: Int,
    val pharmacy: PharmacyX,
    val preferredDate: Any,
    val returnRequestStatus: String,
    val returnRequestStatusLabel: String,
    val serviceFee: Any,
    val serviceType: String,
    val updatedAt: String
)