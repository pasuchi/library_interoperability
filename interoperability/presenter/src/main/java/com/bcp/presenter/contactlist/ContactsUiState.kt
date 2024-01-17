package com.bcp.presenter.contactlist

import com.bcp.domain.model.ContactModel

data class ContactsUiState(
    val contacts: List<ContactModel> = emptyList(),
)