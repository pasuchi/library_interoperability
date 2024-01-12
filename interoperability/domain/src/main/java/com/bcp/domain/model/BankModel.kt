package com.bcp.domain.model

data class BankModel (
    var financialEntityId: String? = null,
    var formattedFinancialEntityId: String? = null,
    var shortName: String? = null,
    var fullName: String? = null,
    var icon: Int? = null
)