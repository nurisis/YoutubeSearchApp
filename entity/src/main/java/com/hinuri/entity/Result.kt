package com.hinuri.entity

/**
 * 데이터 결과 반환 값 형식 지정
 * */

import java.lang.Exception

sealed class Result <out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}
