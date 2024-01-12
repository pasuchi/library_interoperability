package com.bcp.presenter

import android.Manifest
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.bcp.domain.model.ContactModel
import com.bcp.presenter.component.ListContactInteroperability
import com.bcp.presenter.component.SearchBox

@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel = hiltViewModel(),
    componentActivity: ComponentActivity
) {

    HandlerEffect(componentActivity) { initLoader ->
        viewModel.loadContactsIntent(initLoader)
    }

    val uiState: ContactsUiState by viewModel.uiState.collectAsStateWithLifecycle()
    var textSearchBox by rememberSaveable { mutableStateOf("") }
    val contactsList by rememberSaveable { mutableStateOf(mutableListOf<ContactModel>()) }

    when(uiState) {
        is ContactsUiState.RenderContacts -> {
            contactsList.clear()
            contactsList.addAll((uiState as ContactsUiState.RenderContacts).contacts)

            Column(modifier = Modifier) {
                SearchBox(onTexChange = {
                    textSearchBox = it
                }, textValue = textSearchBox)

                ListContactInteroperability(contactsList)
            }
        }
        is ContactsUiState.Error -> Unit
    }
}

@Composable
private fun HandlerEffect(
    componentActivity: ComponentActivity,
    loadContactsIntent: (ContactsIntent) -> Unit
) {

    val content = LocalContext.current

    val projectionFields = arrayOf(
        ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.TYPE,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
    )

    val launchContactPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isAccess ->
        if (isAccess) {
            LoaderManager.getInstance(componentActivity)
                .restartLoader(0, null, object : LoaderManager.LoaderCallbacks<Cursor?> {
                    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor?> {

                        return CursorLoader(
                            content,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            projectionFields,
                            null,
                            null,
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME.plus(" ASC")
                        )
                    }

                    override fun onLoadFinished(loader: Loader<Cursor?>, data: Cursor?) {
                        loadContactsIntent.invoke(ContactsIntent.InitLoad(data))
                    }

                    override fun onLoaderReset(loader: Loader<Cursor?>) = Unit
                })
        }
    }

    LaunchedEffect(key1 = true) {
        launchContactPermission.launch(Manifest.permission.READ_CONTACTS)
    }
}

private infix fun String.matchTextInput(valueToMatch: String): Boolean {
    return this.contains(valueToMatch, ignoreCase = true)
}

