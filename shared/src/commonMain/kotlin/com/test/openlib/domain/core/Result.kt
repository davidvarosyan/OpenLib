package com.test.openlib.domain.core

sealed class Result<out T> {

    data object Loading : Result<Nothing>()

    class Error(val ex: Exception) : Result<Nothing>()

    class Success<out T>(val data: T) : Result<T>()
}

