package com.bcp.presenter.banklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcp.domain.GetListBanksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class BankListViewModel @Inject constructor(
    private val getListBanksUseCase: GetListBanksUseCase
) : ViewModel() {

    private val intentFlow = MutableSharedFlow<BanksIntent>()

    private val _uiState = MutableStateFlow<BanksUiState>(BanksUiState.ShowShimmer)
    val uiState: StateFlow<BanksUiState> = _uiState.asStateFlow()

    init {
        intentFlow
            .map { intent -> intent.mapToAction() }
            .flatMapMerge { action -> processAction(action) }
            .map { result -> banksReduceSate(result) }
            .onEach { _uiState.value = it }
            .launchIn(viewModelScope)
    }

    private fun processAction(action: BanksAction): Flow<List<BankModel>> {
        return when (action) {
            is BanksAction.GetBanks -> getListBanksToModel()
        }
    }

    private fun getListBanksToModel(): Flow<List<BankModel>> {
        return getListBanksUseCase.invoke().map { it.toModel() }
    }

    private fun banksReduceSate(result: List<BankModel>): BanksUiState {
        return when {
            result.isNotEmpty() -> BanksUiState.RenderBanks(data = result)
            else -> BanksUiState.Error("Error")
        }
    }

    fun processIntent(intent: BanksIntent){
        viewModelScope.launch {
            intentFlow.emit(intent)
        }
    }
}