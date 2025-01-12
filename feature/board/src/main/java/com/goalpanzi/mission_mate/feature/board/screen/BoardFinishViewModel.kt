package com.goalpanzi.mission_mate.feature.board.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.common.model.user.UserProfile
import com.goalpanzi.mission_mate.core.domain.mission.usecase.CompleteMissionUseCase
import com.goalpanzi.mission_mate.core.domain.mission.usecase.GetMissionRankUseCase
import com.goalpanzi.mission_mate.core.domain.mission.usecase.SetMissionJoinedUseCase
import com.goalpanzi.mission_mate.core.domain.user.usecase.ProfileUseCase
import com.goalpanzi.mission_mate.core.navigation.RouteModel.MainTabRoute.MissionRouteModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardFinishViewModel @Inject constructor(
    private val getMissionRankUseCase : GetMissionRankUseCase,
    private val profileUseCase : ProfileUseCase,
    private val setMissionJoinedUseCase: SetMissionJoinedUseCase,
    private val completeMissionUseCase : CompleteMissionUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val missionId: Long = savedStateHandle.toRoute<MissionRouteModel.Finish>().missionId

    private val _rank : MutableStateFlow<Int?> = MutableStateFlow(null)
    val rank : StateFlow<Int?> = _rank.asStateFlow()

    private val _profile : MutableStateFlow<UserProfile?> = MutableStateFlow(null)
    val profile : StateFlow<UserProfile?> = _profile.asStateFlow()

    private val _completeMissionResultEvent = MutableSharedFlow<CompleteMissionEvent>()
    val completeMissionResultEvent: SharedFlow<CompleteMissionEvent> = _completeMissionResultEvent.asSharedFlow()


    fun setMissionFinished() {
        viewModelScope.launch {
            setMissionJoinedUseCase(false).collect()
        }
    }

    fun getRankByMissionId() {
        viewModelScope.launch {
            getMissionRankUseCase(missionId)
                .catch {
                    _rank.emit(null)
                }.collect {
                    when(it){
                        is DomainResult.Success -> {
                            _rank.emit(it.data.rank)
                        }
                        else -> {
                            _rank.emit(null)
                        }
                    }
                }
        }
    }

    fun getUserProfile() {
        viewModelScope.launch {
            _profile.emit(profileUseCase.getProfile())
        }
    }

    fun completeMission() {
        viewModelScope.launch {
            _completeMissionResultEvent.emit(CompleteMissionEvent.Loading)
            completeMissionUseCase(missionId)
                .collectLatest {
                    when (it) {
                        is DomainResult.Success -> {
                            _completeMissionResultEvent.emit(CompleteMissionEvent.Success)
                        }

                        else -> {
                            _completeMissionResultEvent.emit(CompleteMissionEvent.Error)
                        }
                    }
                }
        }
    }
}

sealed interface CompleteMissionEvent {
    data object Loading : CompleteMissionEvent
    data object Success : CompleteMissionEvent
    data object Error : CompleteMissionEvent
}
