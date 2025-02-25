package com.goalpanzi.mission_mate.feature.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.history.usecase.GetMissionHistoriesUseCase
import com.goalpanzi.mission_mate.feature.history.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    getMissionHistoriesUseCase: GetMissionHistoriesUseCase
) : ViewModel() {

    val uiState: StateFlow<HistoryUiState> = getMissionHistoriesUseCase(0).map { result ->
        when (result) {
            is DomainResult.Success -> {
                HistoryUiState.Success(histories = result.data.toUiModel())
            }

            is DomainResult.Error -> {
                HistoryUiState.Loading
            }

            is DomainResult.Exception -> {
                HistoryUiState.Loading
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HistoryUiState.Loading
    )

}
