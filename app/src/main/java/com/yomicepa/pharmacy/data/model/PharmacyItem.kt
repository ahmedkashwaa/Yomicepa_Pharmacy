package com.yomicepa.pharmacy.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PharmacyItem(
    val doingBusinessAs: String,
    val enabled: Boolean,
    val numberOfReturns: Int,
    val pharmacyId: Int
)