package com.bcp.interop.domain.model

data class Bank (
    var financialEntityId: String? = null,
    var formattedFinancialEntityId: String? = null,
    var shortName: String? = null,
    var fullName: String? = null,
    var icon: String? = null
)