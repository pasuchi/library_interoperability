package com.bcp.presenter.banklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcp.domain.GetListBanksUseCase
import com.bcp.presenter.banklist.modelui.mapToUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankListViewModel @Inject constructor(
    private val getListBanksUseCase: GetListBanksUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(BanksUiState())
    val uiState: StateFlow<BanksUiState> = _uiState.asStateFlow()


    fun onEvent(event: BanksEvent) {
        when (event) {
            is BanksEvent.GetBanks -> {
                getListBanks()
            }
        }
    }


    fun getListBanks() {
        _uiState.update {
            it.copy(
                isLoaderShimmer = true
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            getListBanksUseCase.invoke().onEach { listBanks ->
                _uiState.update {
                    it.copy(
                        isLoaderShimmer = false,
                        listBank = listBanks.mapToUiModel()

                    )
                }
            }.catch {
                _uiState.update {
                    it.copy(
                        isLoaderShimmer = false,
                        listBank = emptyList()
                    )
                }
            }.launchIn(this)
        }
    }

}