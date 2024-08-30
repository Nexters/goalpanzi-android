package com.goalpanzi.mission_mate.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val nickname : String,
    val characterType : CharacterTypeResponse
)
