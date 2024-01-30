package com.bcp.presenter.banklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcp.interop.domain.ListBankUseCase
import com.bcp.interop.presenter.banklist.BanksEvent
import com.bcp.interop.presenter.banklist.modelui.mapToUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankListViewModel @Inject constructor(
    private val getListBanksUseCase: ListBankUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BanksUiState())
    val uiState: StateFlow<BanksUiState> = _uiState.asStateFlow()


    fun onEvent(event: BanksEvent) {
        when (event) {
            is BanksEvent.GetBanks -> {
                getListBanks()
            }

            else -> Unit
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