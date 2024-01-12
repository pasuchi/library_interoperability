package com.bcp.presenter.contactlist

import android.database.Cursor
import com.bcp.domain.model.ContactModel

sealed class ContactsAction {
    data class RequirePermissionContact(val data: Cursor?) : ContactsAction()
    data class FilterContacts(val data: List<ContactModel>): ContactsAction()
}