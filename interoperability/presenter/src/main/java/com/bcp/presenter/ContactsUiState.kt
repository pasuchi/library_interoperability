package com.bcp.presenter

import com.bcp.domain.model.ContactModel

sealed class ContactsUiState {
    data class RenderContacts(val contacts: List<ContactModel>) : ContactsUiState()
    data class Error(val message: String) : ContactsUiState()
}