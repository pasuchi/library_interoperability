package com.bcp.presenter.contactlist

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bcp.domain.model.ContactModel
import com.bcp.presenter.contactlist.component.ListContactInteroperability
import com.bcp.presenter.contactlist.component.SearchBox


@Composable
fun ListConstactsScreen(
    onTexChange: (String) -> Unit,
    listContacts: List<ContactModel>,
    textValue: String,
    initLoaderManager: () -> Unit,
    nextScreen: () -> Unit
) {

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
        SearchBox(onTexChange = onTexChange, textValue = textValue)
        ListContactInteroperability(listContacts,nextScreen)
    }


}