package com.goalpanzi.mission_mate.core.network.model.response

import kotlinx.serialization.SerialName

enum class CharacterTypeResponse {

    @SerialName("RABBIT")
    RABBIT,

    @SerialName("CAT")
    CAT,

    @SerialName("DOG")
    DOG,

    @SerialName("PANDA")
    PANDA,

    @SerialName("BEAR")
    BEAR,

    @SerialName("BIRD")
    BIRD
}