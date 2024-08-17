package com.goalpanzi.mission_mate.feature.board.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.core.domain.usecase.GetMissionRankUseCase
import com.goalpanzi.mission_mate.core.domain.usecase.ProfileUseCase
import com.luckyoct.core.model.UserProfile
import com.luckyoct.core.model.base.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class BoardFinishViewModel @Inject constructor(
    private val getMissionRankUseCase : GetMissionRankUseCase,
    private val profileUseCase : ProfileUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val missionId: Long = savedStateHandle.get<Long>("missionId")!!

    private val _rank : MutableStateFlow<Int?> = MutableStateFlow(null)
    val rank : StateFlow<Int?> = _rank.asStateFlow()

    private val _profile : MutableStateFlow<UserProfile?> = MutableStateFlow(null)
    val profile : StateFlow<UserProfile?> = _profile.asStateFlow()

    fun getRankByMissionId() {
        viewModelScope.launch {
            getMissionRankUseCase(missionId)
                .catch {
                    _rank.emit(null)
                }.collect {
                    when(it){
                        is NetworkResult.Success -> {
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

}