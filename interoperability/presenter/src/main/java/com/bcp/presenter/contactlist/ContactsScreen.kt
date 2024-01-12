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
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine

@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel = hiltViewModel(),
    componentActivity: ComponentActivity,
    goToSelectBanks: () -> Unit
) {

    HandlerEffect(componentActivity) { initLoader ->
        viewModel.loadContactsIntent(initLoader)
    }

    val uiState: ContactsUiState by viewModel.uiState.collectAsStateWithLifecycle()

    var searchListValue by remember {
        mutableStateOf(listOf<ContactModel>())
    }

    var textSeachbox by rememberSaveable {
        mutableStateOf("")
    }

    var showView by rememberSaveable {
        mutableStateOf(false)
    }

    val contactsListAll by rememberSaveable {
        mutableStateOf(mutableListOf<ContactModel>())
    }
    LaunchedEffect(key1 = textSeachbox, block = {

        if (textSeachbox.isBlank() || textSeachbox.isEmpty()) {
            searchListValue = contactsListAll

        } else {
            val rr = contactsListAll.filter { contact ->
                contact.name matchTextInput textSeachbox || contact.number matchTextInput textSeachbox
            }
            Log.i("TAGS", "ContactsScreen: " + rr)
            searchListValue = rr

        }
    })

    LaunchedEffect(key1 = uiState) {
        when (uiState) {
            is ContactsUiState.RenderContacts -> {
                contactsListAll.clear()
                contactsListAll.addAll((uiState as ContactsUiState.RenderContacts).contacts)
                searchListValue = (uiState as ContactsUiState.RenderContacts).contacts
                showView = true
            }

            is ContactsUiState.Error -> Unit
        }
    }

    if (showView) {
        Column(modifier = Modifier) {
            SearchBox(onTexChange = {
                textSeachbox = it
            }, textValue = textSeachbox)

            ListContactInteroperability(
                listContacts = searchListValue,
                nextScreen = goToSelectBanks
            )
        }
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

