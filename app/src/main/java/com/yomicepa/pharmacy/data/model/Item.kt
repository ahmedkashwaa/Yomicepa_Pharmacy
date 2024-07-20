package com.yomicepa.pharmacy.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val id: String?=null,
    val ndc: String,
    val description: String,
    val manufacturer: String,
    val fullQuantity: Int,
    val partialQuantity: Int,
    val expirationDate: String,

    val name: String="Best Item Name",
    val requestType: String="csc",
    val strength: String="strong",
    val dosage: String="alssot",
    val status: String="PENDING",
    val packageSize: String="200",

    val lotNumber: String
)
