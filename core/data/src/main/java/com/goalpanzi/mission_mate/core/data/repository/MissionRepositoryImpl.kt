package com.goalpanzi.mission_mate.core.data.repository

import com.goalpanzi.mission_mate.core.domain.repository.MissionRepository
import com.goalpanzi.mission_mate.core.network.service.MissionService
import com.goalpanzi.core.model.base.NetworkResult
import com.goalpanzi.core.model.response.MissionBoardsResponse
import com.goalpanzi.core.model.response.MissionDetailResponse
import com.goalpanzi.core.model.response.MissionRankResponse
import com.goalpanzi.core.model.response.MissionVerificationResponse
import com.goalpanzi.core.model.response.MissionVerificationsResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor(
    private val missionService: MissionService,
) : MissionRepository {
    override suspend fun getMissionBoards(missionId: Long): NetworkResult<MissionBoardsResponse> =
        handleResult {
            missionService.getMissionBoards(missionId)
        }

    override suspend fun getMission(missionId: Long): NetworkResult<MissionDetailResponse> =
        handleResult {
            missionService.getMission(missionId)
        }

    override suspend fun getMissionVerifications(missionId: Long): NetworkResult<MissionVerificationsResponse> =
        handleResult {
            missionService.getMissionVerifications(missionId)
        }

    override suspend fun deleteMission(missionId: Long): NetworkResult<MissionDetailResponse> =
        handleResult {
            missionService.deleteMission(missionId)
        }

    override suspend fun getMissionRank(missionId: Long): NetworkResult<MissionRankResponse> =
        handleResult {
            missionService.getMissionRank(missionId)
        }

    override suspend fun verifyMission(missionId: Long, image: File): NetworkResult<Unit> =
        handleResult {
            val requestFile = MultipartBody.Part.createFormData(
                "imageFile",
                image.name,
                image.asRequestBody("image/*".toMediaTypeOrNull())
            )
            missionService.verifyMission(missionId, requestFile)
        }

    override suspend fun getMyMissionVerification(
        missionId: Long,
        number: Int
    ): NetworkResult<MissionVerificationResponse> = handleResult {
        missionService.getMyMissionVerification(missionId,number)
    }
}