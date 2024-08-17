package com.luckyoct.core.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MissionRankResponse(
    val rank: Int
)