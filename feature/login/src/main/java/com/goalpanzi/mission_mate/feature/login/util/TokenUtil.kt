package com.goalpanzi.mission_mate.feature.login.util

import java.security.MessageDigest

object TokenUtil {
    fun compressToken(data: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(data.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}