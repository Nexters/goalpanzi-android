package com.luckyoct.core.model.base

sealed interface NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>
    data class Error(val code: Int? = null, val message: String? = null) : NetworkResult<Nothing>
    data class Exception(val error: Throwable) : NetworkResult<Nothing>
}