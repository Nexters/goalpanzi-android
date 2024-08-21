package com.goalpanzi.mission_mate.feature.onboarding.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.core.domain.usecase.GetJoinedMissionsUseCase
import com.goalpanzi.mission_mate.core.domain.usecase.GetMissionJoinedUseCase
import com.goalpanzi.mission_mate.feature.onboarding.model.OnboardingResultEvent
import com.goalpanzi.mission_mate.feature.onboarding.model.OnboardingUiModel
import com.goalpanzi.core.model.base.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
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

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val getJoinedMissionsUseCase : GetJoinedMissionsUseCase,
    private val getMissionJoinedUseCase: GetMissionJoinedUseCase
) : ViewModel() {

    private val _onboardingUiModel = MutableStateFlow<OnboardingUiModel>(OnboardingUiModel.Loading)
    val onboardingUiModel : StateFlow<OnboardingUiModel> = _onboardingUiModel.asStateFlow()

    private val _onboardingResultEvent = MutableSharedFlow<OnboardingResultEvent>()
    val onboardingResultEvent : SharedFlow<OnboardingResultEvent> = _onboardingResultEvent.asSharedFlow()

    fun getJoinedMissions() {
        viewModelScope.launch {
            _onboardingUiModel.emit(OnboardingUiModel.Loading)

            val isJoined = getMissionJoinedUseCase().first()

            getJoinedMissionsUseCase()
                .catch {
                    _onboardingResultEvent.emit(OnboardingResultEvent.Error)
                }.collect { result ->
                    when(result){
                        is NetworkResult.Success -> {
                            result.data.missions.let { missions ->
                                if(missions.isNotEmpty() && isJoined != false ){
                                    _onboardingResultEvent.emit(
                                        OnboardingResultEvent.SuccessWithJoinedMissions(missions.first())
                                    )
                                }else {
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