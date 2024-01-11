package com.bcp.presenter

import android.app.Application
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.bcp.domain.FilterContactUsecase
import com.bcp.domain.model.ContactModel
import com.bcp.presenter.event.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListContactsViewModel @Inject constructor(
    private val filterUserCase: FilterContactUsecase,
    private val application: Application
) : ViewModel() {

    private val _allListContats = MutableStateFlow(emptyList<ContactModel>())
    private val allListContats = _allListContats.asStateFlow()
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()


    val result = searchText.combine(_allListContats) { searchText, listContact ->
        if (searchText.isBlank() || searchText.isEmpty()) {
            listContact
        } else {
            listContact.filter { contact ->
                contact.name matchTextInput searchText || contact.number matchTextInput searchText
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), allListContats.value)

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
                application.applicationContext,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projectionFields,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME.plus(" ASC")
            )
        }

        override fun onLoadFinished(loader: Loader<Cursor?>, data: Cursor?) {
            viewModelScope.launch(Dispatchers.IO) {
                this.runCatching {
                    _allListContats.value = filterUserCase(data)
                    loader.reset()
                }.onFailure {
                    loader.reset()
                }

            }


        }

        override fun onLoaderReset(loader: Loader<Cursor?>) {
        }
    }

    fun getTextInput(inputText: String) {
        _searchText.value = inputText
    }


    private infix fun String.matchTextInput(valueToMatch: String): Boolean {
        return this.contains(valueToMatch, ignoreCase = true)
    }
}