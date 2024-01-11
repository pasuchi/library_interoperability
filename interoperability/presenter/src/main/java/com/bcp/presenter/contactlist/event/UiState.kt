package com.bcp.presenter.contactlist.event

import com.bcp.domain.model.ContactModel

data class UiState(
    val contactsList: List<ContactModel> = emptyList()
)