package com.yomicepa.pharmacy.data.model

data class ContactInfo(
    val id: Int,
    val createdAt: String,
    val updatedAt: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val title: String,
    val phone: String,
    val fax: String,
    val additionalContact1: String?,
    val additionalContact2: String?
)
