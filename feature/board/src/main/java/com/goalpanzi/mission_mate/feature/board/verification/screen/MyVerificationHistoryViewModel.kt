package com.goalpanzi.mission_mate.feature.board.verification.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.mission.usecase.GetMyMissionVerificationUseCase
import com.goalpanzi.mission_mate.core.navigation.model.RouteModel.MainTabRoute.MissionRouteModel
import com.goalpanzi.mission_mate.feature.board.verification.model.VerificationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MyVerificationHistoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMyMissionVerificationUseCase: GetMyMissionVerificationUseCase
) : ViewModel() {

    private val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

    private val missionId = savedStateHandle.toRoute<MissionRouteModel.MyVerificationHistory>().missionId
    private val number = savedStateHandle.toRoute<MissionRouteModel.MyVerificationHistory>().number
    private val totalNumber = savedStateHandle.toRoute<MissionRouteModel.MyVerificationHistory>().count

    private val _position = MutableStateFlow(number)

    val verification: StateFlow<VerificationUiState> = _position
        .mapNotNull { position ->
            onMoveToPosition(position)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = VerificationUiState(
                nickname = "",
                characterType = null,
                items = List<VerificationUiState.VerificationItemState>(totalNumber) { VerificationUiState.VerificationItemState.Loading },
                position = _position.value
            )
        )

    fun onMoveNextPosition() = _position.value++

    fun onMovePreviousPosition() = _position.value--

    private suspend fun onMoveToPosition(position: Int): VerificationUiState? {
        val items = verification.value.items.takeIf { it[position] is VerificationUiState.VerificationItemState.Loading }?.toMutableList() ?: return null
        val result = getMyMissionVerificationUseCase(missionId, position).first() as? DomainResult.Success
        return result?.data?.run {
            VerificationUiState(
                nickname = nickname,
                characterType = characterType,
                items = items.apply {
                    set(
                        position, VerificationUiState.VerificationItemState.Success(
                            verifiedAt = verifiedAt.format(formatter),
                            image = imageUrl
                        )
                    )
                },
                position = position
            )
        }
    }
}