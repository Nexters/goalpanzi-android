package com.luckyoct.core.model.request

import com.luckyoct.core.model.CharacterType
import kotlinx.serialization.Serializable

@Serializable
data class SaveProfileRequest(
    val nickname: String,
    val characterType: String,
) {
    companion object {
        fun createRequest(nickname: String, type: CharacterType) = SaveProfileRequest(
            nickname = nickname,
            characterType = type.name.uppercase()
        )
    }
}
