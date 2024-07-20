package com.yomicepa.pharmacy.data.model

data class PharmacyX(
    val companyType: String,
    val createdAt: String,
    val dea: String,
    val directDepositInfo: Any,
    val doingBusinessAs: String,
    val enabled: Boolean,
    val id: Int,
    val legalBusinessName: String,
    val licenseState: String,
    val licenseStateCode: String,
    val npi: String,
    val reimbursementType: String,
    val updatedAt: String,
    val user: UserX
)