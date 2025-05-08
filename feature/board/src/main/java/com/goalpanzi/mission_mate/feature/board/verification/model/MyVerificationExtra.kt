package com.goalpanzi.mission_mate.feature.board.verification.model

import com.goalpanzi.mission_mate.core.navigation.model.RouteModel.MainTabRoute.MissionRouteModel

data class MyVerificationExtra(
    val missionId: Long,
    val number: Int,
    val count: Int
) {
    fun toRouteModel() = MissionRouteModel.MyVerificationHistory(
        missionId = missionId,
        number = number,
        count = count
    )
}
