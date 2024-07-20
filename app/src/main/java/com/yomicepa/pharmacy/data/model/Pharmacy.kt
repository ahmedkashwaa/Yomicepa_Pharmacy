package com.yomicepa.pharmacy.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Pharmacy(
    val id: Int,
    val createdAt: String?,
    val updatedAt: String?,
    val user: SignInRequest,
    val enabled: Boolean,
    val licenseState: String,
    val licenseStateCode: String,
    val npi: String,
    val doingBusinessAs: String,
    val legalBusinessName: String,
    val companyType: String,
    val reimbursementType: String,
    val directDepositInfo: String?,
    val wholesalersLinks: List<PharmacyLink>,
    val dea: String
)
