package com.bcp.presenter.contactlist

import android.database.Cursor
import com.bcp.domain.model.ContactModel

sealed class ContactsIntent {
    data class InitLoad(val data: Cursor?) : ContactsIntent()
    data class FilterContacts(val filter: List<ContactModel>):ContactsIntent()
}