package com.yomicepa.pharmacy.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Wholesaler(
    val id: Int,
    val name: String,
    val accountNumber: String?,
    val pharmaciesLinks: List<PharmacyLink>
)
@Serializable
data class PharmacyLink(
    val address: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val pharmacyId: Int,
    val wholesalerId: Int,
    val primary: Boolean
)