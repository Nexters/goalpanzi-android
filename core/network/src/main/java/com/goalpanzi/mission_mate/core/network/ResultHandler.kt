package com.goalpanzi.mission_mate.core.network

import com.goalpanzi.core.model.base.NetworkResult
import retrofit2.HttpException
import retrofit2.Response

interface ResultHandler {

    suspend fun <T : Any> handleResult(execute: suspend () -> Response<T>): NetworkResult<T> {
        return try {
            val response = execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    NetworkResult.Success(body)
                } else {
                    NetworkResult.Error(response.code(), "Response body is null")
                }
            } else {
                NetworkResult.Error(response.code(), response.errorBody()?.string())
            }
        } catch (e: HttpException) {
            NetworkResult.Error(e.code(), e.message())
        } catch (e: Throwable) {
            NetworkResult.Exception(e)
        }
    }
}
