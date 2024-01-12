package com.bcp.presenter.contactlist

fun ContactsIntent.mapToAction(): ContactsAction {
    return when(this){
        is ContactsIntent.InitLoad -> ContactsAction.RequirePermissionContact(data = data)
    }
}