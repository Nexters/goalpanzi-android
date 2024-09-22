package com.goalpanzi.mission_mate.core.network.model.request

import com.goalpanzi.mission_mate.core.network.model.response.CharacterTypeResponse
import kotlinx.serialization.Serializable

@Serializable
data class SaveProfileRequest(
    val nickname: String?,
    val characterType: String,
) {
    companion object {
        fun createRequest(nickname: String?, type: CharacterTypeResponse) = SaveProfileRequest(
            nickname = nickname,
            characterType = type.name.uppercase()
        )
    }
}
