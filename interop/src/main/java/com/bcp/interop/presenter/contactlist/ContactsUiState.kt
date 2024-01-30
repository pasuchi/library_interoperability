package com.bcp.interop.presenter.contactlist

import com.bcp.interop.domain.model.ContactModel

data class ContactsUiState(
    val contacts: List<ContactModel> = emptyList(),
)