package com.bcp.domain.model

data class ContactModel(
    var name: String,
    val number: String,
    val normalizeNumber: String? = null,
    val newContact: Boolean = false,
    var beneficiaryName: String? = null
)