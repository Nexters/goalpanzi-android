package com.goalpanzi.mission_mate.core.board.repository

import com.goalpanzi.mission_mate.core.board.mapper.toModel
import com.goalpanzi.mission_mate.core.data.common.handleResult
import com.goalpanzi.mission_mate.core.data.common.mapper.toModel
import com.goalpanzi.mission_mate.core.datastore.datasource.MissionDataSource
import com.goalpanzi.mission_mate.core.domain.model.base.DomainResult
import com.goalpanzi.mission_mate.core.domain.model.base.convert
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionBoards
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionDetail
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionRank
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionVerification
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionVerifications
import com.goalpanzi.mission_mate.core.domain.repository.MissionRepository
import com.goalpanzi.mission_mate.core.network.service.MissionService
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor(
    private val missionService: MissionService,
    private val missionDataSource: MissionDataSource
) : MissionRepository {
    override suspend fun getMissionBoards(missionId: Long): DomainResult<MissionBoards> =
        handleResult {
            missionService.getMissionBoards(missionId)
        }.convert {
            it.toModel()
        }

    override suspend fun getMission(missionId: Long): DomainResult<MissionDetail> =
        handleResult {
            missionService.getMission(missionId)
        }.convert {
            it.toModel()
        }

    override suspend fun getMissionVerifications(missionId: Long): DomainResult<MissionVerifications> =
        handleResult {
            missionService.getMissionVerifications(missionId)
        }.convert {
            it.toModel()
        }

    override suspend fun deleteMission(missionId: Long): DomainResult<MissionDetail> =
        handleResult {
            missionService.deleteMission(missionId)
        }.convert {
            it.toModel()
        }

    override suspend fun getMissionRank(missionId: Long): DomainResult<MissionRank> =
        handleResult {
            missionService.getMissionRank(missionId)
        }.convert {
            it.toModel()
        }

    override suspend fun verifyMission(missionId: Long, image: File): DomainResult<Unit> =
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
    ): DomainResult<MissionVerification> = handleResult {
        missionService.getMyMissionVerification(missionId, number)
    }.convert {
        it.toModel()
    }

    override fun clearMissionData(): Flow<Unit> = missionDataSource.clearMissionData()

    override fun setIsMissionJoined(data: Boolean): Flow<Unit> = missionDataSource.setIsMissionJoined(data)

    override fun getIsMissionJoined(): Flow<Boolean?> = missionDataSource.getIsMissionJoined()
}