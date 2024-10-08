package com.goalpanzi.mission_mate.feature.onboarding.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.common.model.user.UserProfile
import com.goalpanzi.mission_mate.core.domain.mission.usecase.GetMissionJoinedUseCase
import com.goalpanzi.mission_mate.core.domain.onboarding.usecase.GetJoinedMissionsUseCase
import com.goalpanzi.mission_mate.core.domain.user.usecase.ProfileUseCase
import com.goalpanzi.mission_mate.feature.onboarding.isAfterProfileCreateArg
import com.goalpanzi.mission_mate.feature.onboarding.model.OnboardingResultEvent
import com.goalpanzi.mission_mate.feature.onboarding.model.OnboardingUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val getJoinedMissionsUseCase: GetJoinedMissionsUseCase,
    private val getMissionJoinedUseCase: GetMissionJoinedUseCase,
    private val profileUseCase: ProfileUseCase,
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
            savedStateHandle.get<Boolean>(isAfterProfileCreateArg)?.let {
                if (it) {
                    profileCreateSuccessEvent.emit(profileUseCase.getProfile())
                }
            }
        }
    }

    fun getJoinedMissions() {
        viewModelScope.launch {
            _onboardingUiModel.emit(OnboardingUiModel.Loading)

            val isJoined = getMissionJoinedUseCase().first()

            getJoinedMissionsUseCase()
                .catch {
                    _onboardingResultEvent.emit(OnboardingResultEvent.Error)
                }.collect { result ->
                    when (result) {
                        is DomainResult.Success -> {
                            result.data.missions.let { missions ->
                                if (missions.isNotEmpty() && isJoined != false) {
                                    _onboardingResultEvent.emit(
                                        OnboardingResultEvent.SuccessWithJoinedMissions(missions.first())
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
