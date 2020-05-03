package com.hinuri.data.base

import retrofit2.Response
import com.hinuri.entity.Result

abstract class BaseRepository {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.Success(body)
            }

            return error(response.errorBody()?.string() ?: "Unknown API Error.")


        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        return Result.Error(Exception("$message"))
    }

}