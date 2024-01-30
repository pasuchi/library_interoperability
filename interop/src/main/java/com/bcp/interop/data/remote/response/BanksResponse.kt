package com.bcp.interop.data.remote.response

data class BanksResponse(
    var financialEntityId: String? = null,
    var formattedFinancialEntityId: String? = null,
    var shortName: String? = null,
    var fullName: String? = null,
)