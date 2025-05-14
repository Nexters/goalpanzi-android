package com.goalpanzi.mission_mate.core.navigation.model.image

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
@Parcelize
class MissionMateImages(
    private val images: List<MissionMateImage>
) : Parcelable, List<MissionMateImage> by images

@Serializable
@Parcelize
class MissionMateImage(
    val userCharacter : String,
    val nickname : String,
    val verifiedAt : String,
    val imageUrl : String
) : Parcelable {

    fun formattedVerifiedAt() : String {
        val dateTime = LocalDateTime.parse(verifiedAt)
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        return dateTime.format(formatter)
    }
}

val MissionMateImagesModelType = object : NavType<MissionMateImages>(
    isNullableAllowed = false
){
    override fun get(bundle: Bundle, key: String): MissionMateImages? {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            bundle.getParcelable(key, MissionMateImages::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): MissionMateImages {
        return Json.decodeFromString<MissionMateImages>(value)
    }

    override fun serializeAsValue(value: MissionMateImages): String {
        return Json.encodeToString(value)
    }

    override fun put(bundle: Bundle, key: String, value: MissionMateImages) {
        bundle.putParcelable(key, value)
    }
}
