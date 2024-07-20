package com.yomicepa.pharmacy.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ReturnRequestsResponse(
    var content: List<ReturnRequestItem>,
    val pageable: Pageable,
    val totalPages: Int,
    val totalElements: Int,
    val last: Boolean,
    val number: Int,
    val size: Int,
    val numberOfElements: Int,
    val first: Boolean,
    val empty: Boolean
)
@Serializable
data class ReturnRequestItem(
    val returnRequest: ReturnRequest,
    val numberOfItems: Int,
    val numberOfReports: Int,
    val numberOfShipments: Int
)
@Serializable
data class ReturnRequest(
    val id: Int,
    val createdAt: Long,
    val pharmacy: Pharmacy,
    val preferredDate: String?, // nullable if not present
    val returnRequestStatus: String,
    val returnRequestStatusLabel: String,
    val serviceType: String,

    val updatedAt: String,
    val dateDispatched: String?,
    val dateFulfilled: String?,
    val disbursements: String?,
    val serviceFee: Int?,


)




@Serializable
data class Pageable(
    val sort: Sort,
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val unpaged: Boolean
)
@Serializable
data class Sort(
    val sorted: Boolean,
    val unsorted: Boolean,
    val empty: Boolean
)