package com.goalpanzi.mission_mate.core.network.service

import com.goalpanzi.mission_mate.core.network.model.response.MissionBoardsResponse
import com.goalpanzi.mission_mate.core.network.model.response.MissionDetailResponse
import com.goalpanzi.mission_mate.core.network.model.response.MissionRankResponse
import com.goalpanzi.mission_mate.core.network.model.response.MissionVerificationResponse
import com.goalpanzi.mission_mate.core.network.model.response.MissionVerificationsResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface MissionService {
    @GET("/api/missions/{missionId}/board")
    suspend fun getMissionBoards(
        @Path("missionId") missionId: Long
    ): Response<MissionBoardsResponse>


    @GET("/api/missions/{missionId}")
    suspend fun getMission(
        @Path("missionId") missionId: Long
    ): Response<MissionDetailResponse>

    @GET("/api/missions/{missionId}/verifications")
    suspend fun getMissionVerifications(
        @Path("missionId") missionId: Long
    ) : Response<MissionVerificationsResponse>

    @DELETE("/api/missions/{missionId}")
    suspend fun deleteMission(
        @Path("missionId") missionId: Long
    ) : Response<MissionDetailResponse>

    @GET("/api/mission-members/rank")
    suspend fun getMissionRank(
        @Query("missionId") missionId : Long
    ) : Response<MissionRankResponse>

    @Multipart
    @POST("/api/missions/{missionId}/verifications/me")
    suspend fun verifyMission(
        @Path("missionId") missionId: Long,
        @Part imageFile: MultipartBody.Part
    ) : Response<Unit>

    @GET("/api/missions/{missionId}/verifications/me/{number}")
    suspend fun getMyMissionVerification(
        @Path("missionId") missionId: Long,
        @Path("number") number: Int
    ) : Response<MissionVerificationResponse>
}
