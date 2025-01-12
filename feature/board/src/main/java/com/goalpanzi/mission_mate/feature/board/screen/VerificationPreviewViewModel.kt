package com.goalpanzi.mission_mate.feature.board.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.common.model.user.UserProfile
import com.goalpanzi.mission_mate.core.domain.mission.usecase.VerifyMissionUseCase
import com.goalpanzi.mission_mate.core.domain.user.usecase.ProfileUseCase
import com.goalpanzi.mission_mate.core.navigation.RouteModel.Mission
import com.goalpanzi.mission_mate.feature.board.model.CharacterUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class VerificationPreviewViewModel @Inject constructor(
    private val verifyMissionUseCase: VerifyMissionUseCase,
    private val profileUseCase: ProfileUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val missionId = savedStateHandle.toRoute<Mission.VerificationPreview>().missionId
    private val imageUrl = savedStateHandle.toRoute<Mission.VerificationPreview>().imageUrl

    private val _eventFlow = MutableSharedFlow<UploadEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val uiState: StateFlow<VerificationPreviewUiState> = flow {
        val profile = profileUseCase.getProfile() as UserProfile
        emit(profile)
    }.catch {
        _eventFlow.emit(UploadEvent.Error)
    }.map {
        VerificationPreviewUiState.Success(
            nickname = it.nickname,
            characterUiModel = CharacterUiModel.valueOf(it.characterType.name),
            imageUrl = decodedImageUrl()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = VerificationPreviewUiState.Loading
    )

    fun uploadImage(image: File) {
        viewModelScope.launch {
            _eventFlow.emit(UploadEvent.Loading)
            verifyMissionUseCase(missionId, image).collectLatest {
                when (it) {
                    is DomainResult.Success -> {
                        _eventFlow.emit(UploadEvent.Success)
                    }

                    else -> {
                        _eventFlow.emit(UploadEvent.Error)
                    }
                }
            }
        }
    }

    private fun decodedImageUrl() : String {
        return URLDecoder.decode(
            imageUrl,
            StandardCharsets.UTF_8.toString()
        )
    }
}

sealed interface VerificationPreviewUiState {
    data object Loading : VerificationPreviewUiState
    data class Success(
        val nickname: String,
        val characterUiModel: CharacterUiModel,
        val imageUrl: String
    ) : VerificationPreviewUiState
}

sealed interface UploadEvent {
    data object Loading : UploadEvent
    data object Success : UploadEvent
    data object Error : UploadEvent
}

