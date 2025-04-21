package com.goalpanzi.mission_mate.core.mission.mapper

import com.goalpanzi.mission_mate.core.data.common.mapper.toModel
import com.goalpanzi.mission_mate.core.domain.mission.model.VerificationInfoByBlockNumber
import com.goalpanzi.mission_mate.core.network.model.response.VerificationInfoByBlockNumberResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun VerificationInfoByBlockNumberResponse.toModel() = VerificationInfoByBlockNumber(
    nickname = nickname,
    characterType = characterType.toModel(),
    imageUrl = imageUrl,
    verifiedAt = LocalDateTime.parse(verifiedAt, DateTimeFormatter.ISO_DATE_TIME).toLocalDate()
)