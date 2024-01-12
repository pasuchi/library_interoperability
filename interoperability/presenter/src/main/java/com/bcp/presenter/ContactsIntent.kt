package com.bcp.presenter

import android.database.Cursor

sealed class ContactsIntent {
    data class InitLoad(val data: Cursor?) : ContactsIntent()
}