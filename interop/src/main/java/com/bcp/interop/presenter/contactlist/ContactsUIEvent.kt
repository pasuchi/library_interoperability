package com.bcp.interop.presenter.contactlist

sealed class ContactsUIEvent {
    class SearchContact(val input: String) : ContactsUIEvent()
}