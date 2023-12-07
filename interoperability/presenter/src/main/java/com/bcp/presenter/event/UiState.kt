package com.bcp.presenter.event

import com.bcp.domain.model.ContactModel

data class UiState(
    val contactsList: List<ContactModel> = emptyList()
)