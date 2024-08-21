package com.goalpanzi.core.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val nickname : String,
    val characterType : String
)
