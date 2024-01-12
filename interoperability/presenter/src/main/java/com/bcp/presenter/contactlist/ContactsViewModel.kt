package com.bcp.presenter.contactlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcp.domain.FilterContactUseCase
import com.bcp.domain.model.ContactModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val filterContactUseCase: FilterContactUseCase
) : ViewModel() {

    private val intentFlow = MutableSharedFlow<ContactsIntent>()

    private val _uiState =
        MutableStateFlow<ContactsUiState>(ContactsUiState.RenderContacts(emptyList()))
    val uiState: StateFlow<ContactsUiState> = _uiState.asStateFlow()

    init {
        intentFlow
            .map { intent -> intent.mapToAction() }
            .flatMapMerge { action -> processAction(action) }
            .map { result -> contactsReduceState(result = result) }
            .onEach { _uiState.value = it }
            .launchIn(viewModelScope)
    }


    private fun processAction(action: ContactsAction): Flow<Result> {
        return when (action) {
            is ContactsAction.RequirePermissionContact -> flow {
                emit(Result.AllContacts(data = filterContactUseCase.invoke(data = action.data)))
            }

            is ContactsAction.FilterContacts -> flow {
                emit(Result.Filter(action.data.toMutableList()))
            }
        }
    }

    private fun contactsReduceState(result: Result): ContactsUiState {
        return when (result) {
            is Result.AllContacts -> ContactsUiState.RenderContacts(result.data)
            is Result.Filter -> ContactsUiState.RenderFilterContacts(result.data)
        }
    }

    fun loadContactsIntent(intent: ContactsIntent) {
        viewModelScope.launch {
            intentFlow.emit(intent)
        }
    }

}

sealed class Result {
    data class AllContacts(val data: MutableList<ContactModel>) : Result()
    data class Filter(val data: List<ContactModel>) : Result()
}