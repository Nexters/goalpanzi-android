package com.luckyoct.core.model.request

import kotlinx.serialization.Serializable

enum class CharacterType {
    RABBIT, CAT, DOG, PANDA, BEAR, BIRD
}

@Serializable
data class SaveProfileRequest(
    val nickname: String,
    val characterType: String,
) {
    companion object {
        fun createRequest(nickname: String, index: Int) = SaveProfileRequest(
            nickname = nickname,
            characterType = CharacterType.entries[index].name.uppercase()
        )
    }
}
