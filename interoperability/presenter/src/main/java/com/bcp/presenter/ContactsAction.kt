package com.bcp.presenter

import android.database.Cursor

sealed class ContactsAction {
    data class RequirePermissionContact(val data: Cursor?) : ContactsAction()
}