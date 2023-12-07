package com.bcp.presenter

import android.Manifest
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


@Composable
fun ListConstactsScreen(
    onTexChange: (String) -> Unit,
    listContacts: List<ContactModel>,
    textValue: String,
    initLoaderManager: () -> Unit
) {

    val launchContactPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isAccess ->
        if (isAccess) {
            initLoaderManager()
        }
    }

    LaunchedEffect(key1 = true, block = {
        launchContactPermission.launch(Manifest.permission.READ_CONTACTS)
    })

    Column {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp),
                    value = textValue,
            onValueChange = onTexChange
        )
        LazyColumn {

            items(listContacts) {
                Column {
                    Text(text = it.name, modifier = Modifier.padding(top = 5.dp))
                    Text(text = it.number, modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
                    Divider(thickness = 0.5.dp)
                }

            }
        }
    }


}