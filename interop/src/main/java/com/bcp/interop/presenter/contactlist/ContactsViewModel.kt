package com.bcp.presenter.contactlist

import android.app.Application
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.bcp.interop.domain.FilterContactUseCase
import com.bcp.interop.domain.model.ContactModel
import com.bcp.interop.presenter.contactlist.ContactsUIEvent
import com.bcp.interop.presenter.contactlist.ContactsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val appplication: Application,
    private val filterUserCase: FilterContactUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ContactsUiState())
    val uiState = _uiState.asStateFlow()

    private val _allListContats = MutableStateFlow(emptyList<ContactModel>())
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    init {
        searchText.combine(_allListContats) { searchText, listContact ->
            if (searchText.isBlank() || searchText.isEmpty()) {
                listContact
            } else {
                listContact.filter { contact ->
                    contact.name matchTextInput searchText || contact.number matchTextInput searchText
                }

            }
        }
            .onEach { listContacts ->
                _uiState.update {
                    it.copy(
                        contacts = listContacts
                    )
                }
            }.launchIn(viewModelScope)

    }

    companion object {
        val projectionFields = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.TYPE,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
        )
    }

    val loaderManager = object : LoaderManager.LoaderCallbacks<Cursor?> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor?> {
            return CursorLoader(
                appplication.applicationContext,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projectionFields,  // projection fields
                null,  // the selection criteria
                null,  // the selection args
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME.plus(" ASC")
            )
        }

        override fun onLoadFinished(loader: Loader<Cursor?>, data: Cursor?) {
            viewModelScope.launch(Dispatchers.IO) {
                this.runCatching {
                    filterUserCase(data).apply {
                        _allListContats.value = this
                        _uiState.update {
                            it.copy(
                                contacts = this
                            )
                        }
                    }
                    loader.reset()
                }.onFailure {
                    loader.reset()
                }

            }


        }

        override fun onLoaderReset(loader: Loader<Cursor?>) {
        }
    }

    fun onEvent(event: ContactsUIEvent) {
        when (event) {

            is ContactsUIEvent.SearchContact -> {
                _searchText.value = event.input

            }

            else -> Unit
        }
    }

    private infix fun String.matchTextInput(valueToMatch: String): Boolean {
        return this.contains(valueToMatch, ignoreCase = true)
    }

}