package com.yomicepa.pharmacy.data.model

data class CreateReturnRequest(
    val serviceType: String,
    val wholesalerId: String
)