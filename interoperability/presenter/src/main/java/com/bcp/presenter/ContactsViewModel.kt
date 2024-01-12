package com.bcp.presenter

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcp.domain.FilterContactUseCase
import com.bcp.domain.model.ContactModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val filterContactUseCase: FilterContactUseCase,
    private val application: Application
) : ViewModel() {

    private val intentFlow = MutableSharedFlow<ContactsIntent>()

    private val _uiState = MutableStateFlow<ContactsUiState>(ContactsUiState.RenderContacts(emptyList()))
    val uiState: StateFlow<ContactsUiState> = _uiState.asStateFlow()

    init {
        intentFlow
            .map { intent -> intent.mapToAction() }
            .flatMapMerge { action -> processAction(action) }
            .map { result -> contactsReduceState(contacts = result) }
            .onEach {
                _uiState.value = it
            }
            .launchIn(viewModelScope)
    }


    private fun processAction(action: ContactsAction): Flow<MutableList<ContactModel>> {
        return when (action) {
            is ContactsAction.RequirePermissionContact -> flow {
                emit(
                    filterContactUseCase.invoke(
                        data = action.data
                    )
                )
            }
        }
    }

    private fun contactsReduceState(contacts: MutableList<ContactModel>): ContactsUiState {
        return when {
            contacts.isNotEmpty() -> ContactsUiState.RenderContacts(contacts)
            else -> ContactsUiState.Error("")
        }
    }

    fun loadContactsIntent(intent: ContactsIntent) {
        viewModelScope.launch {
            intentFlow.emit(intent)
        }
    }



    /*
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

     */
}