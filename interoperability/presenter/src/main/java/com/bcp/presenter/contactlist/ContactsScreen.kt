package com.bcp.presenter.contactlist

import android.Manifest
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.ComponentActivity
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
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.bcp.domain.model.ContactModel
import com.bcp.presenter.contactlist.component.ListContactInteroperability
import com.bcp.presenter.contactlist.component.SearchBox

@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel = hiltViewModel(),
    componentActivity: ComponentActivity,
    goToSelectBanks: () -> Unit
) {

    val uiState: ContactsUiState by viewModel.uiState.collectAsStateWithLifecycle()

    var textSearchBox by rememberSaveable { mutableStateOf("") }
    var contactsListAll by rememberSaveable { mutableStateOf(listOf<ContactModel>()) }

    HandlerEffect(
        componentActivity = componentActivity,
        textSearchBox = textSearchBox, contactsListAll = contactsListAll
    ) { initLoader ->
        viewModel.loadContactsIntent(initLoader)
    }

    when (uiState) {
        is ContactsUiState.RenderContacts -> {
            textSearchBox = ""
            contactsListAll = (uiState as ContactsUiState.RenderContacts).contacts
            RenderContacts(
                textSearchBox = textSearchBox,
                onTextChange = { textSearchBox = it },
                contacts = contactsListAll,
                goToSelectBanks = goToSelectBanks
            )
        }

        is ContactsUiState.RenderFilterContacts -> {
            val contacts = (uiState as ContactsUiState.RenderFilterContacts).contacts
            RenderContacts(
                textSearchBox = textSearchBox,
                onTextChange = { textSearchBox = it },
                contacts = contacts,
                goToSelectBanks = goToSelectBanks
            )
        }

        is ContactsUiState.Error -> Unit
    }
}

@Composable
private fun RenderContacts(
    textSearchBox: String,
    onTextChange: (String) -> Unit,
    contacts: List<ContactModel>,
    goToSelectBanks: () -> Unit
) {
    Column(modifier = Modifier) {
        SearchBox(onTexChange = onTextChange, textValue = textSearchBox)
        ListContactInteroperability(
            listContacts = contacts,
            nextScreen = goToSelectBanks
        )
    }
}


@Composable
private fun HandlerEffect(
    componentActivity: ComponentActivity,
    textSearchBox: String,
    contactsListAll: List<ContactModel>,
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

    LaunchedEffect(key1 = textSearchBox, block = {
        Log.i("TAGS", "ContactsScreen: " + textSearchBox)
        if (textSearchBox.isBlank() || textSearchBox.isEmpty()) {
            Log.i("TAGS", "ContactsScreen: " + textSearchBox)
            loadContactsIntent.invoke(ContactsIntent.FilterContacts(contactsListAll))
        } else {
            val filterContacts = contactsListAll.filter { contact ->
                contact.name matchTextInput textSearchBox || contact.number matchTextInput textSearchBox
            }
            Log.i("TAGS", "ContactsScreen: " + filterContacts)
            loadContactsIntent.invoke(ContactsIntent.FilterContacts(filterContacts))
        }
    })
}

private infix fun String.matchTextInput(valueToMatch: String): Boolean {
    return this.contains(valueToMatch, ignoreCase = true)
}

