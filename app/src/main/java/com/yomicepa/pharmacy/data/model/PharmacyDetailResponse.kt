package com.yomicepa.pharmacy.data.model

data class PharmacyDetailResponse(
    val pharmacy: Pharmacy,
    val pharmacyCompanyAddressInfo: AddressInfo,
    val pharmacyMailingAddressInfo: AddressInfo,
    val pharmacyContactInfo: ContactInfo,
    val promoCode: String?
)
