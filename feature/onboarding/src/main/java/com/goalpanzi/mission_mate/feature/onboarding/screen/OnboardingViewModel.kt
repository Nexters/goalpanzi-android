package com.goalpanzi.mission_mate.feature.onboarding.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.common.model.mission.MissionStatus
import com.goalpanzi.mission_mate.core.domain.common.model.user.UserProfile
import com.goalpanzi.mission_mate.core.domain.mission.usecase.GetMissionJoinedUseCase
import com.goalpanzi.mission_mate.core.domain.onboarding.usecase.GetJoinedMissionsUseCase
import com.goalpanzi.mission_mate.core.domain.user.usecase.GetFcmTokenUseCase
import com.goalpanzi.mission_mate.core.domain.user.usecase.ProfileUseCase
import com.goalpanzi.mission_mate.core.domain.user.usecase.UpdateFcmTokenUseCase
import com.goalpanzi.mission_mate.core.navigation.RouteModel.OnboardingRouteModel
import com.goalpanzi.mission_mate.feature.onboarding.model.OnboardingResultEvent
import com.goalpanzi.mission_mate.feature.onboarding.model.OnboardingUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val getJoinedMissionsUseCase: GetJoinedMissionsUseCase,
    private val getMissionJoinedUseCase: GetMissionJoinedUseCase,
    private val profileUseCase: ProfileUseCase,
    private val updateFcmTokenUseCase: UpdateFcmTokenUseCase,
    private val getFcmTokenUseCase: GetFcmTokenUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _onboardingUiModel = MutableStateFlow<OnboardingUiModel>(OnboardingUiModel.Loading)
    val onboardingUiModel: StateFlow<OnboardingUiModel> = _onboardingUiModel.asStateFlow()

    private val _onboardingResultEvent = MutableSharedFlow<OnboardingResultEvent>()
    val onboardingResultEvent: SharedFlow<OnboardingResultEvent> =
        _onboardingResultEvent.asSharedFlow()

    val profileCreateSuccessEvent = MutableSharedFlow<UserProfile?>()

    init {
        viewModelScope.launch {
            savedStateHandle.toRoute<OnboardingRouteModel.Onboarding>().isAfterProfileCreate.let {
                if (it) {
                    profileCreateSuccessEvent.emit(profileUseCase.getProfile())
                }
            }
        }
    }

    fun updateTokenAndGetJoinedMissions() {
        viewModelScope.launch {
            getFcmTokenUseCase()
                .catch {
                    getJoinedMissions()
                }.collect { token ->
                    updateFcmTokenUseCase(token)
                    getJoinedMissions()
                }
        }
    }

    private fun getJoinedMissions() {
        viewModelScope.launch {
            _onboardingUiModel.emit(OnboardingUiModel.Loading)
            getJoinedMissionsUseCase(
                filter = MissionStatus.statusString
            ).catch {
                _onboardingResultEvent.emit(OnboardingResultEvent.Error)
            }.collect { result ->
                when (result) {
                    is DomainResult.Success -> {
                        result.data.missions.let { missions ->
                            val missionInProgress = missions.lastOrNull()
                            if (missionInProgress != null) {
                                _onboardingResultEvent.emit(
                                    OnboardingResultEvent.SuccessWithJoinedMissions(
                                        missionInProgress
                                    )
                                )
                            } else {
                                _onboardingUiModel.emit(
                                    OnboardingUiModel.Success(result.data.profile)
                                )

                            }
                        }
                    }

                    else -> {
                        _onboardingResultEvent.emit(OnboardingResultEvent.Error)
                    }
                }
            }
        }
    }
}
