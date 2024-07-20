package com.yomicepa.pharmacy.data.model

data class AddressInfo(
    val id: Int,
    val createdAt: String,
    val updatedAt: String,
    val address1: String,
    val address2: String?,
    val city: String,
    val state: String,
    val zip: String
)
