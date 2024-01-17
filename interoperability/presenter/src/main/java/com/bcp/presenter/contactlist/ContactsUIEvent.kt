package com.bcp.presenter.contactlist

sealed class ContactsUIEvent {
    class SearchContact(val input: String) : ContactsUIEvent()
}