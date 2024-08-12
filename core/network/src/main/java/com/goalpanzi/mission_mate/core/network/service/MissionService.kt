package com.goalpanzi.mission_mate.core.network.service

import com.luckyoct.core.model.response.MissionBoardsResponse
import com.luckyoct.core.model.response.MissionDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MissionService {
    @GET("/api/missions/{missionId}/board")
    suspend fun getMissionBoards(
        @Path("missionId") missionId: Long
    ): Response<MissionBoardsResponse>


    @GET("/api/missions/{missionId}")
    suspend fun getMission(
        @Path("missionId") missionId: Long
    ): Response<MissionDetailResponse>

}
