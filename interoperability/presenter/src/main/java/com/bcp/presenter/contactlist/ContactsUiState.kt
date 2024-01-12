package com.bcp.presenter.contactlist

import com.bcp.domain.model.ContactModel

sealed class ContactsUiState {
    data class RenderContacts(val contacts: List<ContactModel>) : ContactsUiState()
    data class RenderFilterContacts(val contacts: List<ContactModel>) : ContactsUiState()
    data class Error(val message: String) : ContactsUiState()
}