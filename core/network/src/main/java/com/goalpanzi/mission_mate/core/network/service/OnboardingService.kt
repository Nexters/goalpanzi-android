package com.goalpanzi.mission_mate.core.network.service

import com.luckyoct.core.model.request.CreateMissionRequest
import com.luckyoct.core.model.response.MissionDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/*
* 온보딩 화면에서 사용되는 API
*  - 미션 생성
*  - 미션 조회 by 초대코드
*  - 미션 참여
*/
interface OnboardingService {
    @POST("/api/missions")
    suspend fun createMission(
        @Body request: CreateMissionRequest
    ): Response<MissionDetailResponse>
}