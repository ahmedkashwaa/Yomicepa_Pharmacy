package com.yomicepa.pharmacy.data.model

data class AddItemResponse(
    val actualReturnValue: Double,
    val adminComment: Any,
    val controlledSubstanceCode: Any,
    val createdAt: String,
    val description: String,
    val dosage: String,
    val expectedReturnValue: Double,
    val expirationDate: String,
    val fullQuantity: Double,
    val gtin14: Any,
    val id: Int,
    val lotNumber: String,
    val manufacturer: String,
    val name: String,
    val ndc: String,
    val packageSize: String,
    val partialQuantity: Double,
    val requestType: String,
    val serialNumber: Any,
    val status: String,
    val strength: String,
    val updatedAt: String
)