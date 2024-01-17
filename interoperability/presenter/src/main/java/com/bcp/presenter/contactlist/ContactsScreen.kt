package com.bcp.presenter.contactlist

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.bcp.presenter.contactlist.component.ListContactInteroperability
import com.bcp.presenter.contactlist.component.SearchBox

@Composable
fun ContactsScreen(
    onUIEvent: (ContactsUIEvent) -> Unit,
    uiState: ContactsUiState,
    initLoaderManager: () -> Unit,
    nextScreen: (route: String) -> Unit
) {

    var textValue by rememberSaveable {
        mutableStateOf("")
    }

    val launchContactPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isAccess ->
        if (isAccess) {
            initLoaderManager()
        }
    }
    LaunchedEffect(key1 = true, {
        launchContactPermission.launch(Manifest.permission.READ_CONTACTS)

    })

    Column(modifier = Modifier) {
        SearchBox(onTexChange = {
            onUIEvent(ContactsUIEvent.SearchContact(it))
            textValue = it
        }, textValue)
        ListContactInteroperability(uiState.contacts, nextScreen)
    }
}