package com.luckyoct.core.model.request

import kotlinx.serialization.Serializable

@Serializable
data class VerifyMissionRequest(
    val imageFile: String
)