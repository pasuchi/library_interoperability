package com.bcp.presenter

fun ContactsIntent.mapToAction(): ContactsAction{
    return when(this){
        is ContactsIntent.InitLoad -> ContactsAction.RequirePermissionContact(data = data)
    }
}