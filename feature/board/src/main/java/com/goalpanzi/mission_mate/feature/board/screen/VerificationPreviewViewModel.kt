package com.goalpanzi.mission_mate.feature.board.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.core.domain.usecase.ProfileUseCase
import com.goalpanzi.mission_mate.core.domain.usecase.VerifyMissionUseCase
import com.goalpanzi.mission_mate.feature.board.imageUrlArg
import com.goalpanzi.mission_mate.feature.board.model.Character
import com.luckyoct.core.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class VerificationPreviewViewModel @Inject constructor(
    private val verifyMissionUseCase: VerifyMissionUseCase,
    private val profileUseCase: ProfileUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow = _errorFlow.asSharedFlow()

    val uiState: StateFlow<VerificationPreviewUiState> = flow {
        val profile = profileUseCase.getProfile() as UserProfile
        emit(profile)
    }.catch {
        _errorFlow.emit(it)
    }.map {
        VerificationPreviewUiState.Success(
            nickname = it.nickname,
            character = Character.valueOf(it.characterType.name),
            imageUrl = savedStateHandle.get<String>(imageUrlArg) ?: ""
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = VerificationPreviewUiState.Loading
    )

}

sealed interface VerificationPreviewUiState {
    data object Loading: VerificationPreviewUiState
    data class Success(
        val nickname: String,
        val character: Character,
        val imageUrl: String
    ): VerificationPreviewUiState
}